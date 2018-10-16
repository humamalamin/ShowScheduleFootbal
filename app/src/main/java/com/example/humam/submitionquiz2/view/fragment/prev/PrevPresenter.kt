package com.example.humam.submitionquiz2.view.fragment.prev

import com.example.humam.submitionquiz2.api.ApiRequest
import com.example.humam.submitionquiz2.api.TheSportApi
import com.example.humam.submitionquiz2.model.EventResponse
import com.example.humam.submitionquiz2.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PrevPresenter(private val view: PrevView,
                    private val apiRequest: ApiRequest,
                    private val gson: Gson,
                    private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getEventList(match: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRequest.doRequest(TheSportApi.getSchedule(match)),
                        EventResponse::class.java
                )
            }
            view.showEventList(data.await().match)
            view.hideLoading()
        }
    }
}