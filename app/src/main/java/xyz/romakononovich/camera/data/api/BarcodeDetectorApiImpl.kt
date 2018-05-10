package xyz.romakononovich.camera.data.api

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import xyz.romakononovich.camera.R
import xyz.romakononovich.camera.domain.api.BarcodeDetectorApi

/**
 * Created by RomanK on 10.05.18.
 */
class BarcodeDetectorApiImpl(private val context: Context) : BarcodeDetectorApi {
    private val barcodeDetector: BarcodeDetector
        get() = initializeBarcodeDetector()

    override var onBarcodeDetect: (source: String) -> Unit = {}

    override fun start(path: String) {
        Glide.with(context)
                .asBitmap()
                .load(path)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        detectBarcode(resource)
                    }

                })
    }

    private fun detectBarcode(bitmap: Bitmap) {
        if (!barcodeDetector.isOperational) {
            AlertDialog.Builder(context)
                    .setMessage(context.getString(R.string.device_no_support_barcode_detector))
                    .show()
        } else {
            val frame = Frame.Builder().setBitmap(bitmap).build()
            val sparseArray = barcodeDetector.detect(frame)
            if (sparseArray.size() == 0) {
                onBarcodeDetect.invoke(context.getString(R.string.qrcode_no_found))
            } else {
                onBarcodeDetect.invoke(sparseArray.valueAt(0).rawValue)
            }
            stop()
        }
    }


    override fun stop() {
        barcodeDetector.release()
    }

    private fun initializeBarcodeDetector(): BarcodeDetector {
        return BarcodeDetector.Builder(context)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build()
    }

}