package com.spotlike.yan.spotlike.YoutubeModule.YoutubeDetail

import com.spotlike.yan.spotlike.BaseContract

/**
 * Created by yan on 2017-08-22.
 */
interface YoutubeDetailContract {
    interface YoutubeDetailView: BaseContract.BaseViewContract {
        fun showOption(id: Int)
        fun hideOption(id: Int)
        fun setToolbarTitle(title: String)
        fun setToolbarImage(imageSource: String)
        fun setDescriptionText(text: String)
    }

    interface YoutubeDetailPresentation: BaseContract.BasePresenterContract {

    }
}