package xyz.romakononovich.camera.presentation.gallery

import android.graphics.Bitmap
import xyz.romakononovich.camera.presentation.base.BasePresenter
import xyz.romakononovich.camera.presentation.base.BaseView

/**
 * Created by RomanK on 09.05.18.
 */
interface GalleryContract {
    interface View : BaseView {
        var onBarcodeDetect: (source: String) -> Unit

        fun showDetectFace()

        fun initViewPager(list: MutableList<String>)

        fun showCannotOpenGalleryToast()
    }

    interface Presenter<V : GalleryContract.View> : BasePresenter<V> {
        fun startBarcodeDetector(id: Int)

        fun openFacedetectActivity(id: Int)

        fun getPhoto()

        fun sharePhoto(id: Int)

        fun deletePhoto(id: Int)

        fun start()

        fun stop()
    }
}