package com.spotlike.yan.spotlike.Managers

import android.content.Context
import android.os.Looper
import android.widget.Toast
import com.spotlike.yan.spotlike.MainApplication
import javax.inject.Inject

/**
 * Created by yan on 2017-08-15.
 */
class ToastManager private constructor() {
    @Inject lateinit var context: Context

    companion object {
        val INSTANCE: ToastManager by lazy {
            ToastManager()
        }
    }

    init {
        MainApplication.Companion.graph.inject(this)
    }

    fun displayShortToast(textToDisplay: String){
        showToast(textToDisplay, Toast.LENGTH_SHORT)
    }

    fun displayLongToast(textToDisplay: String){
        showToast(textToDisplay, Toast.LENGTH_LONG)
    }

    private fun showToast(textToDisplay: String, lenght: Int) {
        Looper.getMainLooper().run {
            Toast.makeText(context, textToDisplay, lenght).show()
        }
    }
}