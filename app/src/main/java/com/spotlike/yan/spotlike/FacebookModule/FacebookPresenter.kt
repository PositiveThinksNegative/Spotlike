package com.spotlike.yan.spotlike.FacebookModule

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import javax.inject.Inject
import com.spotlike.yan.spotlike.FacebookModule.FacebookContract.*
import com.spotlike.yan.spotlike.MainApplication
import com.spotlike.yan.spotlike.Managers.RoutingManager
import com.spotlike.yan.spotlike.Managers.ToastManager
import com.spotlike.yan.spotlike.R
import com.spotlike.yan.spotlike.YoutubeModule.YoutubeView

/**
 * Created by yan on 2017-08-15.
 */
class FacebookPresenter : FacebookPresenterContract {
     @Inject lateinit var toastManager: ToastManager
    @Inject lateinit var routingManager: RoutingManager
    private var facebookView: FacebookViewContract? = null
    private var loginButton: LoginButton? = null
    private var callbackManager: CallbackManager? = null

    init {
        MainApplication.Companion.graph.inject(this)
    }

    fun bind(facebookView: FacebookViewContract, loginButton: LoginButton) {
        this.facebookView = facebookView
        this.loginButton = loginButton
        initFacebookLogin()
    }

    private fun initFacebookLogin() {
        loginButton?.setReadPermissions("email")
        loginButton?.fragment = facebookView as Fragment

        callbackManager = CallbackManager.Factory.create()
        loginButton?.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {

            override fun onSuccess(result: LoginResult?) {
                toastManager.displayShortToast(result?.accessToken.toString())
                if (facebookView != null) {
                    routingManager.replaceFragment(R.id.fragment_frame_layout, Bundle(), YoutubeView(), facebookView!!.getParentActivity())
                }
            }

            override fun onError(error: FacebookException?) {
                toastManager.displayShortToast(error?.message.toString())
            }

            override fun onCancel() {

            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }
    override fun onViewCreated() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun unbind() {

    }

}