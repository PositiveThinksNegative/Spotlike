package com.spotlike.yan.spotlike.YoutubeModule

import com.google.gson.Gson
import com.spotlike.yan.spotlike.MainApplication
import com.spotlike.yan.spotlike.Managers.RequestsManager
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.Response
import javax.inject.Inject

/**
 * Created by yan on 2017-08-16.
 */
class YoutubeRequestManager private constructor(): RequestCallback.JsonRequestListener {
    @Inject lateinit var requestsManager: RequestsManager
    @Inject lateinit var gson: Gson

    companion object {
        val INSTANCE: YoutubeRequestManager by lazy {
            YoutubeRequestManager()
        }
        val baseBuilder : HttpUrl.Builder? = HttpUrl.parse("https://www.googleapis.com/youtube/v3/search?part=snippet")?.newBuilder()
        val searchParam = "q"
        val maxResultsParam = "maxResults"
        val keyParam = "key"
        val keyValue = "AIzaSyAxUlD5YCxrbIG6RPAD2Jydg2LOUGdavRE"
    }

    init {
        MainApplication.Companion.graph.inject(this)
    }

    fun constructYTSearchRequest(maxResults: Int, searchKeyword: String): String {
        val youtubeBuilder = baseBuilder
        youtubeBuilder?.addQueryParameter(maxResultsParam, maxResults.toString())
        youtubeBuilder?.addQueryParameter(searchParam, searchKeyword)
        return buildYTApiUrl(youtubeBuilder)
    }

    private fun buildYTApiUrl(builder: HttpUrl.Builder?) : String {
        builder?.addQueryParameter(keyParam, keyValue)
        return builder?.build().toString()
    }

    fun launchYTRequestFromUrl(url: String) {
        val call : Call? = requestsManager.createCallbackFromUrl(url)
        var callback = RequestCallback()
        callback.setCallbackObserver(this)
        call?.enqueue(callback)
    }

    override fun getJsonResults(success: Boolean, response: String) {
        if(success) {
            var test = gson.fromJson(response, YoutubePojo :: class.java)
        }
    }
}