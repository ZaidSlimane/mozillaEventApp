package com.example.mozillaevent

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.time.Instant
import java.util.Date



class Adapter(val items: List<Items>, val context: Context) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.day1, parent, false)
        return ViewHolder(item)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val items = items[position]
        holder.bind(items)
    }

    override fun getItemCount(): Int {
        return (items.size)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val image: ImageView
        val cardName: TextView
        val day: TextView
        val description: TextView
        val attend_button: Button
        val intent: Intent
        val intent1: Intent
        val descCardView: CardView
        val workshopRL: RelativeLayout

        init {
            image = view.findViewById(R.id.imageBox)
            description = view.findViewById(R.id.tvDes)
            day = view.findViewById(R.id.tvDay)
            cardName = view.findViewById(R.id.tvName)
            attend_button = view.findViewById(R.id.attend_btn)
            intent = Intent(context, RegestrationActivitty::class.java)
            intent1 = Intent(context, detailsActivity::class.java)
            descCardView = view.findViewById(R.id.descriptionBox)
            workshopRL= view.findViewById(R.id.workshopLayout)

        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(element: Items) {
//            image.setImageResource(element.image!!)
            Glide.with(context!!)
                .load(element.image)
                .into(image)
            description.text = element.description
            cardName.text = element.cardName
            day.text = element.day+"\n"+" form "+element.startDate!!.hours+":"+element.startDate.minutes+" to "+element.endDate!!.hours+":"+element.endDate.minutes
            val date = Date.from(Instant.now())


            if (date.time.toInt()>element.startDate!!.time.toInt()) {
                descCardView.setBackgroundColor(Color.GREEN)
            }



            if (date.time.toInt()>=element.endDate!!.time.toInt()) {
                descCardView.setBackgroundColor(Color.GRAY)
                //attend_button.isEnabled=false
                attend_button.visibility=View.GONE
            }

            workshopRL.setOnClickListener{
                intent1.putExtra("workahop1",element)
                context.startActivity(intent1)
            }

            attend_button.setOnClickListener {
                intent.putExtra("workshop", element.cardName)
                context.startActivity(intent)
            }


        }
    }
}


