package com.example.humam.submitionquiz2.view.fragment.next

import com.example.humam.submitionquiz2.model.Event

interface NextView {
    fun showLoading()
    fun hideLoading()
    fun showEventList(data: List<Event>?)
    fun errorMessage(message: String?)

}