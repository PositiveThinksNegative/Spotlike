package com.spotlike.yan.spotlike

import com.spotlike.yan.spotlike.FacebookModule.FacebookPresenter
import com.spotlike.yan.spotlike.FacebookModule.FacebookView
import com.spotlike.yan.spotlike.Managers.RoutingManager
import com.spotlike.yan.spotlike.Managers.ToastManager
import com.spotlike.yan.spotlike.YoutubeModule.YoutubeView
import dagger.Component
import javax.inject.Singleton

/**
 * Created by yan on 2017-08-15.
 */

@Singleton
@Component(modules = arrayOf(AndroidModule::class))
interface ApplicationComponent {
    fun inject(application: MainApplication)
    fun inject(facebookPresenter: FacebookPresenter)
    fun inject(mainActivity: MainActivity)
    fun inject(facebookView: FacebookView)
    fun inject(facebookView: ToastManager)
    fun inject(routingManager: RoutingManager)
    fun inject(youtubeView: YoutubeView)
}