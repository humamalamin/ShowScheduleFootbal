package com.example.humam.submitionquiz2.view.fragment.next

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.example.humam.submitionquiz2.R
import com.example.humam.submitionquiz2.adapter.NextAdapter
import com.example.humam.submitionquiz2.api.ApiRequest
import com.example.humam.submitionquiz2.model.Event
import com.example.humam.submitionquiz2.utils.SpaceItemDecoration
import com.example.humam.submitionquiz2.utils.invisible
import com.example.humam.submitionquiz2.utils.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh

class NextFragment : Fragment(), NextView {
    private var schedules: MutableList<Event> = mutableListOf()
    private lateinit var listSchedules: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var adapter: NextAdapter
    private lateinit var presenter: NextPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return UI {
            frameLayout {
                lparams(width = matchParent, height = matchParent)
                swipeRefresh = swipeRefreshLayout {
                    id = R.id.swipeRefresh
                    setColorSchemeResources(R.color.colorAccent,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light)

                    relativeLayout {
                        lparams(width = matchParent, height = matchParent)

                        listSchedules = recyclerView {
                            id = R.id.rvNextEvent
                            layoutManager = LinearLayoutManager(ctx)
                            addItemDecoration(SpaceItemDecoration(8))
                        }.lparams(width = matchParent, height = matchParent) {
                            centerInParent()

                        }

                        progressBar = progressBar {
                            id = R.id.pbNextEvent
                        }.lparams {
                            centerHorizontally()
                        }
                    }
                }
            }
        }.view
    }

    companion object {
        fun nextInstance(): NextFragment = NextFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()

        swipeRefresh.onRefresh {
            presenter.getEventList(getString(R.string.resource_eventsnextleague))
        }
    }

    private fun initAdapter() {
        adapter = NextAdapter(schedules)
        listSchedules.adapter = adapter

        val request = ApiRequest()
        val gson = Gson()
        presenter = NextPresenter(this, request, gson)
        presenter.getEventList(getString(R.string.resource_eventsnextleague))
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showEventList(data: List<Event>?) {
        swipeRefresh.isRefreshing = false
        schedules.clear()
        if(data != null) schedules.addAll(data)
        else errorMessage(getString(R.string.next_event_not_found))
        if (schedules.size == 0) errorMessage(getString(R.string.next_event_not_found))
        adapter.notifyDataSetChanged()
    }


    override fun errorMessage(message: String?) {
        view?.let { longSnackbar(it, message!!) }
    }
}