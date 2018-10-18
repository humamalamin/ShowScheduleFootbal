package com.example.humam.submitionquiz2.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.humam.submitionquiz2.FootBallMatchUI
import com.example.humam.submitionquiz2.R
import com.example.humam.submitionquiz2.model.db.FavoriteParamsDB
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.SimpleDateFormat
import java.util.*

class FavoriteAdapter(private val favorite: List<FavoriteParamsDB>,
                      private val listener: (FavoriteParamsDB) -> Unit):
        RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(FootBallMatchUI().createView(AnkoContext.
                create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }

    override fun getItemCount(): Int = favorite.size
}



class FavoriteViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val timeSchedule: TextView = view.find(R.id.txt_schedule)
    private val homeTeam: TextView = view.find(R.id.txt_hometeam)
    private val homeScore: TextView = view.find(R.id.txt_homescore)
    private val awayScore: TextView = view.find(R.id.txtawayscore)
    private val awayTeam: TextView = view.find(R.id.txt_awayteam)

    fun bindItem(favorite: FavoriteParamsDB, listener: (FavoriteParamsDB) -> Unit) {

        val timeEvent = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .parse(favorite.eventTime)
        val dateEvent = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
                .format(timeEvent)

        timeSchedule.text = dateEvent.toString()
        homeTeam.text = favorite.homeTeam
        homeScore.text = favorite.homeScore
        awayScore.text = favorite.awayScore
        awayTeam.text = favorite.awayTeam

        itemView.onClick {
            listener(favorite)
        }
    }
}