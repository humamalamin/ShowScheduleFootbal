package com.example.humam.submitionquiz2.view.activities.detail

import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.example.humam.submitionquiz2.R.color.colorPrimary
import com.example.humam.submitionquiz2.R.color.colorAccent
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.example.humam.submitionquiz2.R
import com.example.humam.submitionquiz2.R.string.*
import com.example.humam.submitionquiz2.api.ApiRequest
import com.example.humam.submitionquiz2.model.Event
import com.example.humam.submitionquiz2.model.Team
import com.example.humam.submitionquiz2.model.db.FavoriteParamsDB
import com.example.humam.submitionquiz2.utils.db
import com.example.humam.submitionquiz2.utils.gone
import com.example.humam.submitionquiz2.utils.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.experimental.selects.select
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity(), DetailView {

    private lateinit var eventDetail: Event
    private lateinit var badgeHome: Team
    private lateinit var badgeAway: Team

    private lateinit var tvDateEvent: TextView
    private lateinit var tvHomeScore: TextView
    private lateinit var tvAwayScore: TextView
    private lateinit var tvHomeTeam: TextView
    private lateinit var tvAwayTeam: TextView
    private lateinit var tvHomeFormation: TextView
    private lateinit var tvAwayFormation: TextView
    private lateinit var tvHomeShot: TextView
    private lateinit var tvAwayShot: TextView
    private lateinit var tvHomeGoalkeeper: TextView
    private lateinit var tvAwayGoalkeeper: TextView
    private lateinit var tvHomeDefense: TextView
    private lateinit var tvAwayDefense: TextView
    private lateinit var tvHomeMidfield: TextView
    private lateinit var tvAwayMidfield: TextView
    private lateinit var tvHomeForward: TextView
    private lateinit var tvAwayForward: TextView

    private lateinit var ivHomeBadge: ImageView
    private lateinit var ivAwayBadge: ImageView

    private lateinit var lyEventDetail: LinearLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private var itemHomeId: String? = null
    private var itemAwayId: String? = null

    private lateinit var presenter: DetailPresenter
    private lateinit var idEventDetail: String

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.extras != null) {
            idEventDetail = intent.getStringExtra(getString(item_eventdetail_id))
            itemHomeId = intent.getStringExtra(getString(item_home_id))
            itemAwayId = intent.getStringExtra(getString(item_away_id))
        }

        initView()
        getEventDetail()
    }

    private fun initView() {
        swipeRefresh = swipeRefreshLayout {
            setColorSchemeResources(colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)
            scrollView {
                lparams(width = matchParent, height = matchParent)
                relativeLayout {
                    lparams(width = matchParent, height = matchParent)
                    lyEventDetail = linearLayout {
                        id = R.id.lyEventDetail
                        lparams(width = matchParent, height = matchParent)
                        orientation = LinearLayout.VERTICAL
                        gravity = Gravity.CENTER
                        backgroundColor = Color.WHITE
                        padding = dip(16)

                        tvDateEvent = textView {
                            gravity = Gravity.CENTER
                            textColor = colorPrimary
                            textSize = 18f
                            setTypeface(null, Typeface.BOLD)
                        }.lparams(width = wrapContent, height = wrapContent) {
                            topMargin = dip(8)
                            bottomMargin = dip(16)
                        }

                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER

                            ivHomeBadge = imageView().lparams(width = dip(100), height = dip(100)) {
                                gravity = Gravity.CENTER
                                topPadding = dip(16)
                                rightMargin = dip(16)
                            }

                            tvHomeScore = textView {
                                gravity = Gravity.CENTER
                                textSize = 48f
                                setTypeface(null, Typeface.BOLD)
                            }.lparams(width = wrapContent, height = wrapContent)

                            textView {
                                text = context.getString(R.string.resource_vs)
                                textSize = 24f
                            }.lparams(width = wrapContent, height = wrapContent) {
                                leftMargin = dip(8)
                                rightMargin = dip(8)
                            }

                            tvAwayScore = textView {
                                gravity = Gravity.CENTER
                                textSize = 48f
                                setTypeface(null, Typeface.BOLD)
                            }.lparams(width = wrapContent, height = wrapContent)

                            ivAwayBadge = imageView().lparams(width = dip(100), height = dip(100)) {
                                gravity = Gravity.CENTER
                                topPadding = dip(16)
                                leftMargin = dip(16)

                            }

                        }.lparams(height = wrapContent, width = wrapContent)

                        //team and formation
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 1f
                            gravity = Gravity.CENTER

                            linearLayout {
                                orientation = LinearLayout.VERTICAL

                                tvHomeTeam = textView {
                                    textColor = colorPrimary
                                    textSize = 20f
                                    setTypeface(null, Typeface.BOLD)
                                }.lparams(width = wrapContent, height = wrapContent) {
                                    gravity = Gravity.CENTER_HORIZONTAL
                                }

                                tvHomeFormation = textView {
                                    textSize = 14f
                                }.lparams(width = wrapContent, height = wrapContent) {
                                    gravity = Gravity.CENTER_HORIZONTAL
                                }

                            }.lparams(width = 0, height = wrapContent, weight = 0.45f) {
                                margin = dip(16)
                            }

                            linearLayout {
                                orientation = LinearLayout.VERTICAL
                            }.lparams(width = 0, height = wrapContent, weight = 0.1f) {
                                margin = dip(16)
                            }

                            linearLayout {
                                orientation = LinearLayout.VERTICAL

                                tvAwayTeam = textView {
                                    textColor = colorPrimary
                                    textSize = 20f
                                    setTypeface(null, Typeface.BOLD)
                                }.lparams(width = wrapContent, height = wrapContent) {
                                    gravity = Gravity.CENTER_HORIZONTAL
                                }

                                tvAwayFormation = textView {
                                    gravity = Gravity.CENTER_HORIZONTAL
                                    textSize = 14f
                                }

                            }.lparams(width = 0, height = wrapContent, weight = 0.45f) {
                                margin = dip(16)
                            }
                        }

                        view {
                            backgroundColorResource = android.R.color.darker_gray
                        }.lparams(width = matchParent, height = dip(2)) {
                            bottomMargin = dip(48)
                        }

                        //shot
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 1f

                            relativeLayout {
                                tvHomeShot = textView {
                                    textSize = 20f
                                    setTypeface(null, Typeface.BOLD)
                                }.lparams(width = wrapContent, height = wrapContent) {
                                    gravity = Gravity.START
                                }
                            }.lparams(width = dip(0), height = wrapContent, weight = 0.4f) {
                                topMargin = dip(16)
                            }

                            relativeLayout {
                                textView {
                                    textSize = 18f
                                    text = context.getString(R.string.resource_shots)
                                    textColor = colorPrimary
                                }.lparams(width = wrapContent, height = wrapContent) {
                                    gravity = Gravity.CENTER_HORIZONTAL
                                }
                            }.lparams(width = dip(0), height = wrapContent, weight = 0.2f) {
                                topMargin = dip(16)
                            }

                            relativeLayout {
                                tvAwayShot = textView {
                                    textSize = 20f
                                    setTypeface(null, Typeface.BOLD)
                                }.lparams(width = wrapContent, height = wrapContent) {
                                    gravity = Gravity.END
                                }
                            }.lparams(width = dip(0), height = wrapContent, weight = 0.4f) {
                                topMargin = dip(16)
                            }
                        }

                        view {
                            backgroundColorResource = android.R.color.darker_gray
                        }.lparams(width = matchParent, height = dip(2)) {
                            bottomMargin = dip(16)
                        }

                        textView {
                            textSize = 18f
                            text = context.getString(R.string.resource_lineups)
                        }.lparams(width = wrapContent, height = wrapContent)

                        //goalkeeper
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 1f

                            relativeLayout {
                                tvHomeGoalkeeper = textView {
                                    textSize = 16f

                                }.lparams(width = wrapContent, height = wrapContent) {
                                    gravity = Gravity.START
                                }
                            }.lparams(width = dip(0), height = wrapContent, weight = 0.4f) {
                                topMargin = dip(16)
                            }

                            relativeLayout {
                                textView {
                                    textSize = 14f
                                    text = context.getString(R.string.resource_goalkeeper)
                                    textColor = colorPrimary
                                    setTypeface(null, Typeface.BOLD)
                                }.lparams(width = wrapContent, height = wrapContent) {
                                    gravity = Gravity.CENTER_HORIZONTAL
                                }
                            }.lparams(width = dip(0), height = wrapContent, weight = 0.2f) {
                                topMargin = dip(16)
                            }

                            relativeLayout {
                                tvAwayGoalkeeper = textView {
                                    textSize = 16f
                                }.lparams(width = wrapContent, height = wrapContent) {
                                    gravity = Gravity.END
                                }
                            }.lparams(width = dip(0), height = wrapContent, weight = 0.4f) {
                                topMargin = dip(16)
                            }
                        }

                        //Defense
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 1f

                            relativeLayout {
                                tvHomeDefense = textView {
                                    textSize = 16f

                                }.lparams(width = wrapContent, height = wrapContent) {
                                    gravity = Gravity.START
                                }
                            }.lparams(width = dip(0), height = wrapContent, weight = 0.4f) {
                                topMargin = dip(8)
                            }

                            relativeLayout {
                                textView {
                                    textSize = 14f
                                    text = context.getString(R.string.resource_defense)
                                    textColor = colorPrimary
                                    setTypeface(null, Typeface.BOLD)
                                }.lparams(width = wrapContent, height = wrapContent) {
                                    gravity = Gravity.CENTER_HORIZONTAL
                                }
                            }.lparams(width = dip(0), height = wrapContent, weight = 0.2f) {
                                topMargin = dip(8)
                            }

                            relativeLayout {
                                tvAwayDefense = textView {
                                    textSize = 16f
                                }.lparams(width = wrapContent, height = wrapContent) {
                                    gravity = Gravity.END
                                }
                            }.lparams(width = dip(0), height = wrapContent, weight = 0.4f) {
                                topMargin = dip(8)
                            }
                        }

                        //Midfield
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 1f

                            relativeLayout {
                                tvHomeMidfield = textView {
                                    textSize = 16f

                                }.lparams(width = wrapContent, height = wrapContent) {
                                    gravity = Gravity.START
                                }
                            }.lparams(width = dip(0), height = wrapContent, weight = 0.4f) {
                                topMargin = dip(8)
                            }

                            relativeLayout {
                                textView {
                                    textSize = 14f
                                    text = context.getString(R.string.resource_midfield)
                                    textColor = colorPrimary
                                    setTypeface(null, Typeface.BOLD)
                                }.lparams(width = wrapContent, height = wrapContent) {
                                    gravity = Gravity.CENTER_HORIZONTAL
                                }
                            }.lparams(width = dip(0), height = wrapContent, weight = 0.2f) {
                                topMargin = dip(8)
                            }

                            relativeLayout {
                                tvAwayMidfield = textView {
                                    textSize = 16f
                                }.lparams(width = wrapContent, height = wrapContent) {
                                    gravity = Gravity.END
                                }
                            }.lparams(width = dip(0), height = wrapContent, weight = 0.4f) {
                                topMargin = dip(8)
                            }
                        }

                        //Forward
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 1f

                            relativeLayout {
                                tvHomeForward = textView {
                                    textSize = 16f

                                }.lparams(width = wrapContent, height = wrapContent) {
                                    gravity = Gravity.START
                                }
                            }.lparams(width = dip(0), height = wrapContent, weight = 0.4f) {
                                topMargin = dip(8)
                            }

                            relativeLayout {
                                textView {
                                    textSize = 14f
                                    text = context.getString(R.string.resource_forward)
                                    textColor = colorPrimary
                                    setTypeface(null, Typeface.BOLD)
                                }.lparams(width = wrapContent, height = wrapContent) {
                                    gravity = Gravity.CENTER_HORIZONTAL
                                }
                            }.lparams(width = dip(0), height = wrapContent, weight = 0.2f) {
                                topMargin = dip(8)
                            }

                            relativeLayout {
                                tvAwayForward = textView {
                                    textSize = 16f
                                }.lparams(width = wrapContent, height = wrapContent) {
                                    gravity = Gravity.END
                                }
                            }.lparams(width = dip(0), height = wrapContent, weight = 0.4f) {
                                topMargin = dip(8)
                            }
                        }
                    }
                    progressBar = progressBar {
                        id = R.id.pbDetailEvent
                    }.lparams {
                        centerInParent()
                    }
                }
            }
        }
    }

    private fun bindView() {

        swipeRefresh.isRefreshing = false

        val timeEvent = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .parse(eventDetail.dateEvent)
        val dateEvent = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
                .format(timeEvent)

        tvDateEvent.text = dateEvent
        tvHomeScore.text = eventDetail.intHomeScore
        tvAwayScore.text = eventDetail.intAwayScore
        tvHomeTeam.text = eventDetail.strHomeTeam
        tvHomeFormation.text = eventDetail.strHomeFormation
        tvAwayFormation.text = eventDetail.strAwayFormation
        tvHomeShot.text = eventDetail.intHomeShots
        tvAwayShot.text = eventDetail.intAwayShots
        tvAwayTeam.text = eventDetail.strAwayTeam
        tvHomeGoalkeeper.text = setPlayerTeam(eventDetail.strHomeLineupGoalKeeper)
        tvAwayGoalkeeper.text = setPlayerTeam(eventDetail.strAwayLineupGoalkeeper)
        tvHomeDefense.text = setPlayerTeam(eventDetail.strHomeLineupDefense)
        tvAwayDefense.text = setPlayerTeam(eventDetail.strAwayLineupDefense)
        tvHomeMidfield.text = setPlayerTeam(eventDetail.strHomeLineupMidfield)
        tvAwayMidfield.text = setPlayerTeam(eventDetail.strAwayLineupMidfield)
        tvHomeForward.text = setPlayerTeam(eventDetail.strHomeLineupForward)
        tvAwayForward.text = setPlayerTeam(eventDetail.strAwayLineupForward)

        Picasso.get().load(badgeHome.teamBadge).into(ivHomeBadge)
        Picasso.get().load(badgeAway.teamBadge).into(ivAwayBadge)

    }

    private fun getEventDetail() {
        favoriteState()
        presenter = DetailPresenter(this, ApiRequest(), Gson())
        presenter.getEventDetail(idEventDetail, itemHomeId, itemAwayId)

        swipeRefresh.onRefresh {
            presenter.getEventDetail(idEventDetail, itemHomeId, itemAwayId)
        }
    }

    private fun setPlayerTeam(playerName: String?): String? {
        val bulkPlayer = playerName?.split(";".toRegex())?.dropLastWhile {
            it.isEmpty()
        }?.map { it.trim() }?.toTypedArray()?.joinToString("\n")

        return bulkPlayer
    }

    override fun hideLoading() {
        progressBar.gone()
        lyEventDetail.visible()
    }

    override fun showLoading() {
        progressBar.visible()
        lyEventDetail.gone()
    }

    override fun showEventList(data: List<Event>, home: List<Team>, away: List<Team>) {
        eventDetail = data[0]
        badgeAway = away[0]
        badgeHome = home[0]

        bindView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun favoriteState(){
        db.use {
            val result = select(FavoriteParamsDB.TABLE_FAVORITE)
                    .whereArgs("(EVENT_ID = {id})",
                            "id" to idEventDetail)
            val favorite = result.parseList(classParser<FavoriteParamsDB>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun addToFavorite() {
        try {
            db.use {
                insert(FavoriteParamsDB.TABLE_FAVORITE,
                        FavoriteParamsDB.EVENT_ID to eventDetail.idEvent,
                        FavoriteParamsDB.EVENT_TIME to eventDetail.dateEvent,
                        FavoriteParamsDB.HOME_TEAM to eventDetail.strHomeTeam,
                        FavoriteParamsDB.HOME_SCORE to eventDetail.intHomeScore,
                        FavoriteParamsDB.AWAY_TEAM to eventDetail.strAwayTeam,
                        FavoriteParamsDB.AWAY_SCORE to eventDetail.intAwayScore,
                        FavoriteParamsDB.HOME_TEAM_ID to itemHomeId,
                        FavoriteParamsDB.AWAY_TEAM_ID to itemAwayId)
            }
            snackbar(swipeRefresh, "Added to Favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            db.use {
                delete(FavoriteParamsDB.TABLE_FAVORITE, "(EVENT_ID = {id})",
                        "id" to idEventDetail)
            }
            snackbar(swipeRefresh, "Removed to Favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this,
                    R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this,
                    R.drawable.ic_add_to_favorites)
    }

}
