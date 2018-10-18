package com.example.humam.submitionquiz2.utils

import android.content.Context
import android.view.View
import com.example.humam.submitionquiz2.model.db.FavoriteDBHelper

fun View.invisible(){
    visibility = View.INVISIBLE
}

fun View.visible(){
    visibility  = View.VISIBLE
}

fun View.gone() {
    visibility  = View.GONE
}

val Context.db: FavoriteDBHelper
    get() = FavoriteDBHelper.getInstance(applicationContext)
