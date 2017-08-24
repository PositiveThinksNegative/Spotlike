package com.spotlike.yan.spotlike.Managers

import android.app.Activity
import android.app.Fragment
import android.app.FragmentTransaction
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.Surface
import android.view.View
import android.view.Window
import com.spotlike.yan.spotlike.MainApplication
import com.spotlike.yan.spotlike.R
import javax.inject.Inject
import android.view.WindowManager


/**
 * Created by yan on 2017-08-15.
 */
class RoutingManager private constructor() {
    @Inject lateinit var context: Context

    companion object {
        val INSTANCE: RoutingManager by lazy {
            RoutingManager()
        }
        val EXTRA_STRING = "extra_string"
        val TRANSITION_STRING = "transition_string"
    }

    init {
        MainApplication.Companion.graph.inject(this)
    }

    fun replaceFragment(layoutId: Int, bundle: Bundle, fragment: android.app.Fragment, activity: Activity) {
        val transaction: FragmentTransaction = activity.fragmentManager.beginTransaction()
        val currentFragment = activity.fragmentManager.findFragmentById(layoutId)
        if(fragmentDoesNotOverlap(currentFragment, fragment)){
            fragment.arguments = bundle
            transaction.replace(layoutId, fragment)
            transaction.commitAllowingStateLoss()
        }
    }

    fun addFragment(layoutId: Int, bundle: Bundle, fragment: android.support.v4.app.Fragment, activity: AppCompatActivity) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        val currentFragment = activity.supportFragmentManager.findFragmentById(layoutId)
        if(fragmentDoesNotOverlap(currentFragment, fragment)){
            fragment.arguments = bundle
            transaction.add(layoutId, fragment)
            transaction.commitAllowingStateLoss()
        }
    }

    fun replaceFragment(layoutId: Int, bundle: Bundle, fragment: android.support.v4.app.Fragment, activity: AppCompatActivity) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        val currentFragment = activity.supportFragmentManager.findFragmentById(layoutId)
        if(fragmentDoesNotOverlap(currentFragment, fragment)){
            fragment.arguments = bundle
            transaction.replace(layoutId, fragment)
            transaction.commitAllowingStateLoss()
        }
    }

    private fun fragmentDoesNotOverlap(currentFragment: Fragment?, newFragment: Fragment): Boolean {
        return currentFragment == null || canOperateOnFragment(currentFragment.javaClass, newFragment.javaClass)
    }

    private fun fragmentDoesNotOverlap(currentFragment: android.support.v4.app.Fragment?, newFragment: android.support.v4.app.Fragment): Boolean {
        return currentFragment == null || canOperateOnFragment(currentFragment, newFragment)
    }

    private fun canOperateOnFragment(currentFragment: Any, newFragment: Any): Boolean {
        var canReplace = false
        if (currentFragment.javaClass != newFragment.javaClass) {
            canReplace = true
        }
        return canReplace
    }


    @JvmOverloads
    fun startActivity(activity: Activity?, targetActivity: Class<in Activity>, extra: String = "", view: View? = null) {
        activity?.let {
            val intent = Intent(activity, targetActivity)
            intent.putExtra(EXTRA_STRING, extra)

            if (view != null) {
                var pair1 = Pair<View, String>(activity.findViewById(android.R.id.navigationBarBackground), Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME)
                var pair2 = Pair<View, String>(activity.findViewById(android.R.id.statusBarBackground), Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME)
                var pair3 = Pair<View, String>(view, ViewCompat.getTransitionName(view))
                val activityOptions : ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair1, pair2, pair3)

                intent.putExtra(TRANSITION_STRING, ViewCompat.getTransitionName(view))
                activity.startActivity(intent, activityOptions.toBundle())
            } else {
                activity.startActivity(intent)
                activity.overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            }
        }
    }

    fun getGridRotationLayout(): Int {
        val rotation = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.rotation
        when (rotation) {
            Surface.ROTATION_0 -> return GridLayoutManager.VERTICAL
            Surface.ROTATION_90 -> return GridLayoutManager.HORIZONTAL
            Surface.ROTATION_180 -> return GridLayoutManager.VERTICAL
            else -> return GridLayoutManager.HORIZONTAL
        }
    }
}