package com.spotlike.yan.spotlike

import android.app.Application
import android.content.Context
import com.spotlike.yan.spotlike.FacebookModule.FacebookPresenter
import com.spotlike.yan.spotlike.Managers.RoutingManager
import com.spotlike.yan.spotlike.Managers.ToastManager
import com.spotlike.yan.spotlike.YoutubeModule.YoutubePresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by yan on 2017-08-15.
 */

@Module class AndroidModule(private val application: Application) {

    @Provides @Singleton
    fun provideContext(): Context {
        return application.applicationContext
    }

    @Provides @Singleton
    fun provideRoutingManager(): RoutingManager {
        return RoutingManager.instance
    }

    @Provides @Singleton
    fun provideFacebookPresenter(): FacebookPresenter {
        return FacebookPresenter()
    }

    @Provides @Singleton
    fun provideYoutubePresenter(): YoutubePresenter {
        return YoutubePresenter()
    }

    @Provides @Singleton
    fun provideToastManager(): ToastManager {
        return ToastManager.instance
    }
}