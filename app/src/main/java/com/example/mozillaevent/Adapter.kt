package com.example.mozillaevent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class Adapter(val items: List<Items>) : RecyclerView.Adapter<Adapter.ViewHolder>() {


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
        val  description: TextView

        init {
            image = view.findViewById(R.id.imageBox)
            description = view.findViewById(R.id.tvDes)
            day = view.findViewById(R.id.tvDay)
            cardName = view.findViewById(R.id.tvName)
        }

        fun bind(element: Items) {
            image.setImageResource(element.image)
            description.text = element.description
            cardName.text = element.cardName
            day.text = element.day

        }


    }
}