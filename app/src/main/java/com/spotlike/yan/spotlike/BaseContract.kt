package com.spotlike.yan.spotlike

/**
 * Created by yan on 2017-08-15.
 */
interface BaseContract {

    interface BaseViewContract {

    }

    interface BasePresenterContract {

        fun onResume()

        fun onPause()

        fun unbind()

    }
}