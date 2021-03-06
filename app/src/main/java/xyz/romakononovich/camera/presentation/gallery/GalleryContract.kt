package xyz.romakononovich.camera.presentation.gallery

import xyz.romakononovich.camera.presentation.base.BasePresenter
import xyz.romakononovich.camera.presentation.base.BaseView

/**
 * Created by RomanK on 09.05.18.
 */
interface GalleryContract {
    interface View : BaseView {
        var onBarcodeDetect: (source: String) -> Unit

        var onGetInfo: (source: String) -> Unit

        fun printPhoto(path: String)

        fun initViewPager(list: MutableList<String>)

        fun refreshListPager(list: MutableList<String>)

        fun showCannotOpenGalleryToastEmpty()

        fun showCannotOpenGalleryToastPermission()
    }

    interface Presenter<V : GalleryContract.View> : BasePresenter<V> {
        fun startBarcodeDetector(id: Int)

        fun openFaceDetectActivity(id: Int)

        fun showInfoPhoto(id: Int)

        fun sharePhoto(id: Int)

        fun printPhoto(id: Int)

        fun deletePhoto(id: Int)

        fun refreshList()

        fun start()

        fun stop()
    }
}