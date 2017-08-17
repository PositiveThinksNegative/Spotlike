package com.spotlike.yan.spotlike.YoutubeModule

import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

/**
 * Created by yan on 2017-08-16.
 */
class RequestCallback : Callback {
    private var requestListener: JsonRequestListener? = null

    interface JsonRequestListener {
        fun getJsonResults(success: Boolean, response: String)
    }

    override fun onFailure(call: Call?, e: IOException?) {
        requestListener?.getJsonResults(false, "")
    }

    override fun onResponse(call: Call?, response: Response) {
        requestListener?.getJsonResults(response.isSuccessful, response.body()?.string().toString())
    }

    fun setCallbackObserver(requestListener: JsonRequestListener) {
        this.requestListener = requestListener
    }

    fun removeCallbackObserver() {
        this.requestListener = null
    }
}