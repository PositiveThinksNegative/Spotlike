package com.spotlike.yan.spotlike.YoutubeModule.YoutubeDetail

import android.app.Activity
import android.view.MenuItem
import com.spotlike.yan.spotlike.BaseContract

/**
 * Created by yan on 2017-08-22.
 */
interface YoutubeDetailContract {
    interface YoutubeDetailView: BaseContract.BaseViewContract {
        fun showOption(id: Int)
        fun hideOption(id: Int)
        fun showToolbarTitle()
        fun hideToolbarTitle()
        fun showTitleDescription()
        fun hideTitleDescription()
        fun setDescriptionTitle(title: String)
        fun setToolbarTitle(title: String)
        fun setToolbarImage(imageSource: String)
        fun setDescriptionText(text: String)
    }

    interface YoutubeDetailPresentation: BaseContract.BasePresenterContract {
        fun onActionPlaySelected(activity: Activity)
        fun onOptionsItemSelected(item: MenuItem?, activity: Activity) : Boolean
    }
}