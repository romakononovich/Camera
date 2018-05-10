package xyz.romakononovich.camera.presentation.gallery

import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.android.synthetic.main.switches_bottom_gallery.*
import kotlinx.android.synthetic.main.toolbar.*
import xyz.romakononovich.camera.R
import xyz.romakononovich.camera.data.api.BarcodeDetectorApiImpl
import xyz.romakononovich.camera.data.api.PhotoRepositoryImpl
import xyz.romakononovich.camera.presentation.base.BaseActivity
import xyz.romakononovich.camera.presentation.router.RouterImpl
import xyz.romakononovich.camera.presentation.view.DeleteDialog
import xyz.romakononovich.camera.presentation.view.QrCodeDialog
import xyz.romakononovich.camera.utils.DELETE_DIALOG
import xyz.romakononovich.camera.utils.DepthPageTransformer
import xyz.romakononovich.camera.utils.QRCODE_DIALOG


/**
 * Created by RomanK on 06.05.18.
 */

class GalleryActivity : BaseActivity(),
        GalleryContract.View,
        GalleryAdapter.ClickListener,
        View.OnClickListener,
        DeleteDialog.DeleteDialogListener {


    override var presenter: GalleryContract.Presenter? = null
    private var galleryAdapter: GalleryAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        GalleryPresenter(this, BarcodeDetectorApiImpl(this), PhotoRepositoryImpl(), RouterImpl(this))
        presenter?.start()

        includeToolbar.visibility = View.VISIBLE
        includeLayoutBottom.visibility = View.VISIBLE

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun initViewPager(list: MutableList<String>) {
        galleryAdapter = GalleryAdapter(this, list, this)
        viewPager.adapter = galleryAdapter
        viewPager.setPageTransformer(true, DepthPageTransformer())
        viewPager.setOnClickListener(this)

    }

    override var onBarcodeDetect: (source: String) -> Unit = {
        QrCodeDialog.newInstance(it).show(supportFragmentManager, QRCODE_DIALOG)
    }


    override var onFaceDetect: (bitmap: Bitmap) -> Unit = {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showDetectFace() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDeleteDialogPositiveClick(id: Int) {
        presenter?.deletePhoto(id)
        galleryAdapter?.delete(id)
    }

    override fun onClick(view: View) {
        when (view) {
            btnQrCode -> {
                presenter?.startBarcodeDetector(viewPager.currentItem)
            }
            btnDelete -> {
                DeleteDialog.newInstance(viewPager.currentItem).show(supportFragmentManager, DELETE_DIALOG)
            }
            btnShare -> {
                presenter?.sharePhoto(viewPager.currentItem)
            }
            btnFace -> {


            }
        }
    }

    override fun getResLayout(): Int {
        return R.layout.activity_gallery
    }

    private fun showView(isShow: Boolean) {
        if (isShow) {
            ObjectAnimator.ofFloat(includeToolbar, "translationY", 0f)
                    .setDuration(300)
                    .start()
            includeToolbar.visibility = View.VISIBLE
            includeLayoutBottom.visibility = View.VISIBLE
        } else {
            ObjectAnimator.ofFloat(includeToolbar, "translationY", -includeToolbar.height.toFloat())
                    .setDuration(300)
                    .start()
            includeToolbar.visibility = View.INVISIBLE
            includeLayoutBottom.visibility = View.INVISIBLE
        }
    }

    override fun clickViewPager() {
        if (includeToolbar.visibility == View.VISIBLE) {
            showView(false)
        } else {
            showView(true)
        }
    }

    override fun onBackPressed() {
        if (includeToolbar.visibility == View.VISIBLE) {
            showView(false)
        }
        super.onBackPressed()
    }
}

