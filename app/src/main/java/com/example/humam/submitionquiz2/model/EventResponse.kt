package com.example.humam.submitionquiz2.model

import com.google.gson.annotations.SerializedName

data class EventResponse(@SerializedName("events")
                         val match: List<Event>)