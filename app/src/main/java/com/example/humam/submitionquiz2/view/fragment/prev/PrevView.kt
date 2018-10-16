package com.example.humam.submitionquiz2.view.fragment.prev

import com.example.humam.submitionquiz2.model.Event

interface PrevView {
    fun showLoading()
    fun hideLoading()
    fun showEventList(data: List<Event>)
}