package com.spotlike.yan.spotlike.YoutubeModule

import android.app.Activity
import android.view.MenuItem
import com.spotlike.yan.spotlike.BaseContract

/**
 * Created by yan on 2017-08-16.
 */
interface YoutubeContract {
    interface YoutubeViewContract : BaseContract.BaseViewContract {

    }

    interface YoutubePresenterContract : BaseContract.BasePresenterContract {
        fun searchYoutube(searchKeyword: String)
        fun onOptionsItemSelected(item: MenuItem?, activity: Activity) : Boolean
    }
}