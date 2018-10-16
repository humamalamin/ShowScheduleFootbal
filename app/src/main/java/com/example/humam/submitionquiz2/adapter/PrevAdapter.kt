package com.example.humam.submitionquiz2.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.humam.submitionquiz2.R
import com.example.humam.submitionquiz2.model.Event
import com.example.humam.submitionquiz2.FootBallMatchUI
import com.example.humam.submitionquiz2.view.activities.detail.DetailActivity
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*

class PrevAdapter (private val matches: List<Event>) :
        RecyclerView.Adapter<PrevViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrevViewHolder {
        return PrevViewHolder(FootBallMatchUI().createView(AnkoContext.create(parent.context,
                parent)))
    }

    override fun getItemCount(): Int = matches.size

    override fun onBindViewHolder(holder: PrevViewHolder, position: Int) {
        holder.bindItem(matches[position])
    }

}

class PrevViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val timeSchedule: TextView = view.find(R.id.txt_schedule)
    private val homeTeam: TextView = view.find(R.id.txt_hometeam)
    private val homeScore: TextView = view.find(R.id.txt_homescore)
    private val awayScore: TextView = view.find(R.id.txtawayscore)
    private val awayTeam: TextView = view.find(R.id.txt_awayteam)


    fun bindItem(matches: Event) {

        val timeEvent = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .parse(matches.dateEvent)
        val dateEvent = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
                .format(timeEvent)

        timeSchedule.text = dateEvent.toString()
        homeTeam.text = matches.strHomeTeam
        homeScore.text = matches.intHomeScore
        awayScore.text = matches.intAwayScore
        awayTeam.text = matches.strAwayTeam

        val ctx = itemView.context

        itemView.setOnClickListener {
            ctx.startActivity<DetailActivity>(
                    ctx.getString(R.string.item_eventdetail_id) to matches.idEvent,
                    ctx.getString(R.string.item_home_id) to matches.idHomeTeam,
                    ctx.getString(R.string.item_away_id) to matches.idAwayTeam)
        }
    }
}