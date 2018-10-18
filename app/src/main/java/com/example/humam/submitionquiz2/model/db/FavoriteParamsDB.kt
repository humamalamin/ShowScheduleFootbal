package com.example.humam.submitionquiz2.model.db

class FavoriteParamsDB(val id: Long?, val eventId: String?, val eventTime: String?,
                       val homeTeam: String?, val homeScore: String?, val awayTeam: String?,
                       val awayScore: String?, val homeTeamId: String?, val awayTeamId: String?) {
    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val EVENT_TIME: String = "EVENT_TIME"
        const val HOME_TEAM: String = "HOME_TEAM"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_TEAM: String = "AWAY_TEAM"
        const val AWAY_SCORE: String = "AWAY_SCORE"
        const val HOME_TEAM_ID: String = "HOME_TEAM_ID"
        const val AWAY_TEAM_ID: String = "AWAY_TEAM_ID"
    }
}