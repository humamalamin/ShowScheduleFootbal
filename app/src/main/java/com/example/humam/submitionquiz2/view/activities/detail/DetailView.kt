package com.example.humam.submitionquiz2.view.activities.detail

import com.example.humam.submitionquiz2.model.Event
import com.example.humam.submitionquiz2.model.Team

interface DetailView {
    fun hideLoading()
    fun showLoading()
    fun showEventList(data: List<Event>, home: List<Team>, away: List<Team>)
}