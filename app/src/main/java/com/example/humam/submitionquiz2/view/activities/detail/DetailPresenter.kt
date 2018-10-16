package com.example.humam.submitionquiz2.view.activities.detail

import com.example.humam.submitionquiz2.api.ApiRequest
import com.example.humam.submitionquiz2.api.TheSportApi
import com.example.humam.submitionquiz2.model.EventResponse
import com.example.humam.submitionquiz2.model.TeamResponse
import com.example.humam.submitionquiz2.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailPresenter(private val view: DetailView,
                      private val apiRequest: ApiRequest,
                      private val gson: Gson,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    fun getEventDetail(idEvent: String?, idHomeTeam: String?, idAwayTeam: String?) {
        view.showLoading()

        async(context.main) {
            val eventDetail = bg {
                gson.fromJson(apiRequest.doRequest(TheSportApi.getDetailEvent(idEvent)),
                        EventResponse::class.java)
            }
            val badgeHome = bg {
                gson.fromJson(apiRequest.doRequest(TheSportApi.getHomeBadge(idHomeTeam)),
                        TeamResponse::class.java)
            }
            val badgeAway = bg {
                gson.fromJson(apiRequest.doRequest(TheSportApi.getAwayBadge(idAwayTeam)),
                        TeamResponse::class.java)
            }
            view.showEventList(eventDetail.await().match, badgeHome.await().teams,
                    badgeAway.await().teams)
            view.hideLoading()
        }
    }
}