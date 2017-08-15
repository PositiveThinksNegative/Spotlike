package com.spotlike.yan.spotlike.Managers

import android.app.Activity
import android.app.Fragment
import android.app.FragmentTransaction
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.spotlike.yan.spotlike.MainApplication
import com.spotlike.yan.spotlike.R
import javax.inject.Inject

/**
 * Created by yan on 2017-08-15.
 */
class RoutingManager private constructor() {
    @Inject lateinit var context: Context

    companion object {
        val instance: RoutingManager by lazy {
            RoutingManager()
        }
    }

    init {
        MainApplication.Companion.graph.inject(this)
    }

    fun replaceFragment(layoutId: Int, bundle: Bundle, fragment: Fragment, activity: Activity) {
        var transaction: FragmentTransaction = activity.fragmentManager.beginTransaction()
        fragment.arguments = bundle
        transaction.replace(layoutId, fragment)
        transaction.commitAllowingStateLoss()
    }

    fun replaceFragment(layoutId: Int, bundle: Bundle, fragment: android.support.v4.app.Fragment, activity: AppCompatActivity) {
        var transaction = activity.supportFragmentManager.beginTransaction()
        fragment.arguments = bundle
        transaction.replace(layoutId, fragment)
        transaction.commitAllowingStateLoss()
    }

    fun startActivity(activity: Activity?, targetActivity: Class<in Activity>) {
        var intent: Intent = Intent(activity, targetActivity)
        activity?.startActivity(intent)
        activity?.overridePendingTransition(R.anim.fadein, R.anim.fadeout)
    }
}