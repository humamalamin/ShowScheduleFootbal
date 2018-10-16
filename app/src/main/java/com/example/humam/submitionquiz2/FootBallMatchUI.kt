package com.example.humam.submitionquiz2

import android.graphics.Typeface
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class FootBallMatchUI: AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        cardView {
            lparams(width = matchParent, height = wrapContent) {
                topMargin = dip(8)
                leftMargin = dip(16)
                rightMargin = dip(16)
                radius = dip(4).toFloat()
            }

            linearLayout {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                padding = dip(8)

                textView {
                    id = R.id.txt_schedule
                    textColor = R.color.colorPrimary
                    gravity = Gravity.CENTER
                    setTypeface(null, Typeface.BOLD)
                    textSize = 16f
                }.lparams{
                    bottomMargin = dip(8)
                }

                linearLayout {
                    lparams(width = wrapContent, height = matchParent)
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER_HORIZONTAL

                    textView {
                        id = R.id.txt_hometeam
                        textSize = 20f
                        textColor = R.color.colorBlack
                    }.lparams(width = wrapContent, height = wrapContent) {

                    }

                    textView {
                        id = R.id.txt_homescore
                        textSize = 24f
                        setTypeface(null, Typeface.BOLD)
                    }.lparams(width = wrapContent, height = wrapContent) {
                        leftMargin = dip(8)
                    }

                    textView {
                        id = R.id.txt_versus
                        text = context.getString(R.string.resource_vs)
                        gravity = Gravity.CENTER
                        textSize = 18f
                    }.lparams(height = wrapContent, width = wrapContent) {
                        leftMargin = dip(4)
                    }

                    textView {
                        id = R.id.txtawayscore
                        setTypeface(null, Typeface.BOLD)
                        textSize = 24f
                    }.lparams(width = wrapContent, height = wrapContent) {
                        leftMargin = dip(4)
                    }

                    textView {
                        id = R.id.txt_awayteam
                        textSize = 20f
                        textColor = R.color.colorBlack
                    }.lparams(width = wrapContent, height = wrapContent) {
                        leftMargin = dip(8)
                    }
                }
            }.lparams(width = matchParent, height = wrapContent) {
                margin = dip(8)
            }
        }
    }
}