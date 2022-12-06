package com.example.mozillaevent

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class Adapter(val items: List<Items>, val context: Context) : RecyclerView.Adapter<Adapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.day1, parent, false)
        return ViewHolder(item)
    }

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
        val attend_button : Button
        val intent : Intent

        init {
            image = view.findViewById(R.id.imageBox)
            description = view.findViewById(R.id.tvDes)
            day = view.findViewById(R.id.tvDay)
            cardName = view.findViewById(R.id.tvName)
            attend_button = view.findViewById(R.id.attend_btn)
            intent=Intent(context,RegestrationActivitty::class.java)

        }

        fun bind(element: Items) {
//            image.setImageResource(element.image!!)
            Glide.with(context!!)
                .load(element.image)
                .into(image)
            description.text = element.description
            cardName.text = element.cardName
            day.text = element.day

           attend_button.setOnClickListener {
               intent.putExtra("workshop",element.cardName)
              context.startActivity(intent)
            }



        }
    }
}


