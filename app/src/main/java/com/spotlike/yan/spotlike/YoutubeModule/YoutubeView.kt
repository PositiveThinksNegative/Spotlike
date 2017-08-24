package com.spotlike.yan.spotlike.YoutubeModule

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.spotlike.yan.spotlike.MainApplication
import com.spotlike.yan.spotlike.R
import javax.inject.Inject
import kotlinx.android.synthetic.main.youtube_list_view.*


/**
 * Created by yan on 2017-08-15.
 */
class YoutubeView : Fragment(), YoutubeContract.YoutubeViewContract {
    @Inject lateinit var presenter: YoutubePresenter

    init {
        MainApplication.Companion.graph.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.youtube_list_view, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bind(this, recyclerView)
        presenter.onViewCreated()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val optionHandled = presenter.onOptionsItemSelected(item, getParentActivity())
        return if (optionHandled) true else super.onOptionsItemSelected(item)
    }

    override fun getParentActivity(): AppCompatActivity {
        return activity as AppCompatActivity
    }
}