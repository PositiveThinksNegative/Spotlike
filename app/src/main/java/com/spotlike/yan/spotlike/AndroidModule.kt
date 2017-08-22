package com.spotlike.yan.spotlike

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.spotlike.yan.spotlike.FacebookModule.FacebookPresenter
import com.spotlike.yan.spotlike.Managers.ImageManager
import com.spotlike.yan.spotlike.Managers.RequestsManager
import com.spotlike.yan.spotlike.Managers.RoutingManager
import com.spotlike.yan.spotlike.Managers.ToastManager
import com.spotlike.yan.spotlike.YoutubeModule.YoutubeDetail.YoutubeDetailContract
import com.spotlike.yan.spotlike.YoutubeModule.YoutubeDetail.YoutubeDetailPresenter
import com.spotlike.yan.spotlike.YoutubeModule.YoutubePresenter
import com.spotlike.yan.spotlike.YoutubeModule.YoutubeRequestManager
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * Created by yan on 2017-08-15.
 */

@Module class AndroidModule(private val application: Application) {

    @Provides @Singleton
    fun provideContext(): Context {
        return application.applicationContext
    }

    /**
     * Presenters
     */
    @Provides @Singleton
    fun provideFacebookPresenter(): FacebookPresenter {
        return FacebookPresenter()
    }

    @Provides @Singleton
    fun provideYoutubePresenter(): YoutubePresenter {
        return YoutubePresenter()
    }

    @Provides @Singleton
    fun provideYoutubeDetailPresenter(): YoutubeDetailPresenter {
        return YoutubeDetailPresenter()
    }

    /**
     * Utilities
     */
    @Provides @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Provides @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    /**
     * MANAGERS
     */
    @Provides @Singleton
    fun provideToastManager(): ToastManager {
        return ToastManager.INSTANCE
    }

    @Provides @Singleton
    fun provideRoutingManager(): RoutingManager {
        return RoutingManager.INSTANCE
    }

    @Provides @Singleton
    fun provideRequestsManager(): RequestsManager {
        return RequestsManager.INSTANCE
    }

    @Provides @Singleton
    fun provideYoutubeRequestManager(): YoutubeRequestManager {
        return YoutubeRequestManager.INSTANCE
    }

    @Provides @Singleton
    fun provideImageManager(): ImageManager {
        return ImageManager.INSTANCE
    }

}