package com.spotlike.yan.spotlike.YoutubeModule

import com.google.gson.Gson
import com.spotlike.yan.spotlike.MainApplication
import com.spotlike.yan.spotlike.Managers.RequestsManager
import okhttp3.Call
import okhttp3.HttpUrl
import javax.inject.Inject

/**
 * Created by yan on 2017-08-16.
 */
class YoutubeRequestManager private constructor(): RequestCallback.JsonRequestListener {
    @Inject lateinit var requestsManager: RequestsManager
    @Inject lateinit var gson: Gson
    private var youtubeRequestListener: YoutubeRequestListener? = null
    private var currentYoutubeData : YoutubeObject? = null

    interface YoutubeRequestListener {
        fun youtubeResponse(youtubeObject: YoutubeObject)
    }

    companion object {
        val INSTANCE: YoutubeRequestManager by lazy {
            YoutubeRequestManager()
        }
        val baseVideoUrl = "https://www.youtube.com/watch?v="
        val baseSearchUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet"
        val keyValue = "AIzaSyAxUlD5YCxrbIG6RPAD2Jydg2LOUGdavRE"
        val searchParam = "q"
        val maxResultsParam = "maxResults"
        val keyParam = "key"
    }

    init {
        MainApplication.Companion.graph.inject(this)
    }

    fun constructYTSearchRequest(maxResults: Int, searchKeyword: String): String {
        val youtubeBuilder : HttpUrl.Builder? = HttpUrl.parse(baseSearchUrl)?.newBuilder()
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
            val youtubeObject : YoutubeObject = gson.fromJson(response, YoutubeObject:: class.java)
            youtubeRequestListener?.youtubeResponse(youtubeObject)
            currentYoutubeData = youtubeObject
        }
    }

    fun getYoutubeItem(id: String) : YoutubeItem? {
        return currentYoutubeData?.items?.find { it.id.videoId == id }
    }

    fun setListener(youtubeRequestListener: YoutubeRequestListener){
        this.youtubeRequestListener = youtubeRequestListener
    }

    fun removeCallbackObserver() {
        this.youtubeRequestListener = null
    }
}