package com.example.mozillaevent
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide

import kotlin.collections.ArrayList

class MentorsAdapter(private val context: Context, private val mentors: ArrayList<Mentor>,
                     private val SessViewPager: ViewPager2, private val day: TextView,private var SessList:ArrayList<Session> ) :
    RecyclerView.Adapter<MentorsAdapter.MentorViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MentorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.mentor_cardview, parent, false)
        return MentorViewHolder(view)
    }
    lateinit var SessAdapter: SessionsSliderAdapter
    lateinit var childView:View
    lateinit var session:Session


    override fun onBindViewHolder(holder: MentorViewHolder, position: Int) {
        holder.name.text = mentors[position].name
        holder.fName.text = mentors[position].fName
        Glide.with(context!!)
            .load(mentors[position].image)
            .transform(RoundedCornersTransformation(50, 0))
            .into(holder.mentorImg)

        holder.mentCard.setOnClickListener {

            var i = SessAdapter.getViewidbyName(mentors[position].name!!)
            val intent = Intent(context, MentorActivity::class.java)


            if (i==SessViewPager.currentItem){
                childView = SessViewPager.getChildAt(0)
            }else
                childView = holder.itemView

            // Get the ViewGroup for the ViewPager
            val viewPagerParent = SessViewPager.parent as ViewGroup

            // Get the child view at position 0 in the ViewPager


            val dayPair = Pair.create(day as View, "day")
            val pair = Pair.create(holder.itemView, "Mentorm")
            val pair1: Pair<View, String> =
                if (childView != null)
                    Pair.create(childView, "card_transitionm")
                else
                    Pair.create(holder.itemView, "Mentorm")

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context as Activity,
                pair,
                pair1,
                dayPair
            )

            intent.putExtra("act", "main")
           for (s : Session in SessList){
               if (s.mentorName.equals(mentors[position].name)){
                   session= s
               }
           }

            session.startDate?.let { it1 -> intent.putExtra("d", it1.day+1) }
            intent.putExtra("m",session)
            context.startActivity(intent, options.toBundle())
        }

       /* val uri = "@drawable/ouassim"
        val imageResource = context.resources.getIdentifier(uri, null, context.packageName)
        val res: Drawable = context.resources.getDrawable(imageResource)

        holder.mentorImg.setImageDrawable(res)*/
    }

    override fun getItemCount(): Int {
        return mentors.size
    }
   /* fun getPositionByMentorName(mentorName : String): Int {
       var m : Mentor
        for (i in 1..mentors.size-1){
            m=mentors[i]
            if (m.name!!.trim().equals(mentorName)){
                return  i
            }
        }
        return -1
    }*/

    fun getViewidbyName(name: String): Int {
        for (m in mentors) {
            if (m.name == name) {
                return mentors.indexOf(m)
            }
        }
        return -1
    }

    inner class MentorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.MentorName)
        var fName: TextView = itemView.findViewById(R.id.MentorFName)
        var mentorImg: ImageView = itemView.findViewById(R.id.MentorImg)
        var mentCard: CardView = itemView.findViewById(R.id.mentCard)
    }
}
