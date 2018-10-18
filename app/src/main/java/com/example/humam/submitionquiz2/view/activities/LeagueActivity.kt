package com.example.humam.submitionquiz2.view.activities

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.humam.submitionquiz2.R.color.colorPrimary
import com.example.humam.submitionquiz2.R.color.colorWhite
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.widget.LinearLayout
import com.example.humam.submitionquiz2.R
import com.example.humam.submitionquiz2.view.fragment.favorite.FavoriteFragment
import com.example.humam.submitionquiz2.view.fragment.next.NextFragment
import com.example.humam.submitionquiz2.view.fragment.prev.PrevFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.design.bottomNavigationView

class LeagueActivity : AppCompatActivity() {
    lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar = supportActionBar as ActionBar

        LeagueActivityUI().setContentView(this)

        val bottomNavigation: BottomNavigationView = find(R.id.navigation)

        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if(savedInstanceState == null) {
            addFragment(PrevFragment.prevInstance())
        }
    }

    class LeagueActivityUI : AnkoComponent<LeagueActivity> {
        @SuppressLint("NewApi")
        override fun createView(ui: AnkoContext<LeagueActivity>) = with(ui) {
            relativeLayout {
                lparams(width = matchParent, height = matchParent)

                frameLayout {
                    id = R.id.container
                }.lparams(width = matchParent, height = matchParent) {
                    above(R.id.bottom_layout)
                }

                linearLayout {
                    id = R.id.bottom_layout
                    orientation = LinearLayout.VERTICAL

                    view {
                        background = resources.getDrawable(R.drawable.shadow)
                    }.lparams(height = dip(4), width = matchParent)

                    bottomNavigationView {
                        id = R.id.navigation
                        inflateMenu(R.menu.navigation)
                        itemBackgroundResource = colorPrimary
                        backgroundColor = android.R.attr.windowBackground
                        itemTextColor = resources.getColorStateList(colorWhite)
                        itemIconTintList = resources.getColorStateList(colorWhite)
                    }.lparams(width = matchParent, height = wrapContent) {
                        marginEnd = dip(0)
                        marginStart = dip(0)
                    }
                }.lparams(width = matchParent, height = wrapContent) {
                    alignParentBottom()
                }
            }
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_prev_league -> {
                val prevFragment = PrevFragment.prevInstance()
                addFragment(prevFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_next_league -> {
                val nextFragment = NextFragment.nextInstance()
                addFragment(nextFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorites -> {
                val favoFragment = FavoriteFragment.favoInstance()
                addFragment(favoFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun addFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}

