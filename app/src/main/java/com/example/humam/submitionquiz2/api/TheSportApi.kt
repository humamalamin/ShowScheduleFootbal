package com.example.humam.submitionquiz2.api

import com.example.humam.submitionquiz2.BuildConfig

object TheSportApi {
    private const val idLeague = ".php?id=4328"
    private const val strLookUpTeam = "lookupteam.php?id="
    private const val strLookUpEvent = "lookupevent.php?id="

    fun getSchedule(eventLeague: String?): String {
        return BuildConfig.BASE_URL + eventLeague + idLeague
    }

    fun getDetailEvent(idLeague: String?) : String {
        return BuildConfig.BASE_URL + strLookUpEvent + idLeague
    }

    fun getHomeBadge(idHome: String?) : String {
        return BuildConfig.BASE_URL + strLookUpTeam + idHome
    }

    fun getAwayBadge(idAway: String?) : String {
        return BuildConfig.BASE_URL + strLookUpTeam + idAway
    }

    fun getPrevEvent(idEvent: String?): String {
        return BuildConfig.BASE_URL + "eventspastleague.php?id=" + idEvent
    }

    fun getNextEvent(idEvent: String?): String {
        return BuildConfig.BASE_URL + "eventsnextleague.php?id=" + idEvent
    }

}