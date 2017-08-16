package com.spotlike.yan.spotlike

import android.support.v7.app.AppCompatActivity

/**
 * Created by yan on 2017-08-15.
 */
interface BaseContract {

    interface BaseViewContract {
        fun getParentActivity() : AppCompatActivity
    }

    interface BasePresenterContract {
        fun onViewCreated()

        fun onResume()

        fun onPause()

        fun unbind()

    }
}