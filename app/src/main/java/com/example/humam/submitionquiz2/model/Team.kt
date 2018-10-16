package com.example.humam.submitionquiz2.model

import com.google.gson.annotations.SerializedName

data class Team (@SerializedName("idTeam") var teamId: String? = null,
                 @SerializedName("strTeam") var teamName: String? = null,
                 @SerializedName("strTeamBadge") var teamBadge: String? = null)