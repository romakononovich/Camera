package xyz.romakononovich.camera.di.activity

import dagger.Module
import dagger.android.ContributesAndroidInjector
import xyz.romakononovich.camera.di.scopes.ActivityScope
import xyz.romakononovich.camera.presentation.facedetect.FaceDetectActivity
import xyz.romakononovich.camera.presentation.gallery.GalleryActivity
import xyz.romakononovich.camera.presentation.main.CameraActivity


/**
 * Created by RomanK on 11.05.18.
 */
@Module
abstract class ActivityBuilder {
    @ActivityScope
    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    abstract fun providesMainActivityInjector(): CameraActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [(GalleryActivityModule::class)])
    abstract fun providesGalleryActivityInjector(): GalleryActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [(FaceDetectorActivityModule::class)])
    abstract fun providesFaceDetectorActivityInjector(): FaceDetectActivity
}