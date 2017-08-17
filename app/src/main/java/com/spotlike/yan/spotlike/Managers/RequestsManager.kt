package com.spotlike.yan.spotlike.Managers

import com.spotlike.yan.spotlike.MainApplication
import okhttp3.*
import javax.inject.Inject

/**
 * Created by yan on 2017-08-16.
 */
class RequestsManager private constructor() {
    @Inject lateinit var okHttpClient: OkHttpClient

    companion object {
        val INSTANCE: RequestsManager by lazy {
            RequestsManager()
        }
    }

    init {
        MainApplication.Companion.graph.inject(this)
    }

    fun createCallbackFromUrl(url: String) : Call? {
        val request: Request = Request.Builder().url(url).build()
        return okHttpClient.newCall(request)
    }
}