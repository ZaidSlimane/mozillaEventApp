package com.example.mozillaevent

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import java.time.Instant
import java.util.*
import kotlin.collections.ArrayList

class SessionsSliderAdapter(
    private var sessionList: ArrayList<Session>,
    private val viewPager2: ViewPager2,
    private val context: Context,
    private val mRecView: RecyclerView,


    private val daytransm: TextView,
    private val adapter: MentorsAdapter


) : RecyclerView.Adapter<SessionsSliderAdapter.SessionViewHolder>() {


    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.sess_cardview, parent, false)
        return SessionViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        holder.sessionTitle.text = sessionList[position].cardName
        holder.sessionType.text = sessionList[position].type
        holder.day.text = "" + sessionList[position].startDate!!.hours + ":" + sessionList[position].startDate!!.minutes + " - " + sessionList[position].endDate!!.hours + ":" + sessionList[position].endDate!!.minutes
        holder.sessionSalle.text = sessionList[position].Salle
            Glide.with(context!!)
            .load(sessionList[position].image)
            .into(holder.sessionImg)

        val date = Date.from(Instant.now())
        if (date.time.toInt()>sessionList[position].startDate!!.time.toInt() && date.time.toInt()<sessionList[position].endDate!!.time.toInt()) {
            Toast.makeText(context,date.time.toString(),Toast.LENGTH_LONG)
            holder.cardbg.setBackgroundResource(R.color.green_trnsp)
        }

        holder.sessCard.setOnClickListener {
            val intent = Intent(context, Workshop::class.java)


            // Define the shared element transition
            val dayPair = Pair.create(daytransm as View, "day")
            val cardTransitionPair = Pair.create(holder.itemView, "card_transition")
            var mentorPair: Pair<View, String> = Pair.create(holder.itemView, "card_transition")

            // Set the mentorPair to the corresponding RecyclerView ViewHolder, if present
            var i: Int = adapter.getViewidbyName(sessionList[position].mentorName!!)
            //    Toast.makeText(context, "" + i + " j", Toast.LENGTH_LONG)
            val viewHolder = mRecView.findViewHolderForAdapterPosition(i)
            viewHolder?.let {
                mentorPair = Pair.create(it.itemView, "Mentor")
            }

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context as Activity, cardTransitionPair, mentorPair, dayPair
            )
            sessionList[position].startDate?.let { it1 -> intent.putExtra("d", it1.day+1) }
            intent.putExtra("Session",sessionList[position])
            startActivity(context, intent, options.toBundle())
        }
    }

    override fun getItemCount(): Int {
        return sessionList.size
    }


    fun getViewidbyName(name: String): Int {
        for (sess in sessionList) {
            if (sess.mentorName == name) {
                return sessionList.indexOf(sess)
            }
        }
        return -1
    }

    fun setSessionList(sessionList: ArrayList<Session>) {
        this.sessionList = sessionList
        notifyDataSetChanged()
    }

    inner class SessionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sessionTitle: TextView = itemView.findViewById(R.id.SessionTitlem)
        val sessionType: TextView = itemView.findViewById(R.id.SessionTypem)
        val sessionSalle: TextView = itemView.findViewById(R.id.salle)
        val sessionImg: ImageView = itemView.findViewById(R.id.imageView)
        val sessCard: CardView = itemView.findViewById(R.id.sessCard)
        val day: TextView = itemView.findViewById(R.id.time)
        val cardbg: CardView = itemView.findViewById(R.id.crdm)


    }
}
