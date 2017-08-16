package com.spotlike.yan.spotlike.FacebookModule

import android.app.Activity
import android.content.Intent
import com.spotlike.yan.spotlike.BaseContract.*

/**
 * Created by yan on 2017-08-15.
 */
interface FacebookContract {

    interface FacebookViewContract : BaseViewContract {

    }

    interface FacebookPresenterContract : BasePresenterContract {
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    }
}