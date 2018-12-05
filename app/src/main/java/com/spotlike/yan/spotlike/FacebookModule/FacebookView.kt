package com.spotlike.yan.spotlike.FacebookModule

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spotlike.yan.spotlike.MainApplication
import com.spotlike.yan.spotlike.R
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_facebook_login.*
/**
 * Created by yan on 2017-08-15.
 */

class FacebookView : Fragment(), FacebookContract.FacebookViewContract {
    @Inject lateinit var facebookPresenter: FacebookPresenter

    init {
        MainApplication.Companion.graph.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.activity_facebook_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        facebookPresenter.bind(this, login_button)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        facebookPresenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun getParentActivity() : AppCompatActivity {
        return activity as AppCompatActivity
    }
}