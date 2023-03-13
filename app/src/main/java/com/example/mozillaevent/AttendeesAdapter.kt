package com.example.mozillaevent

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AttendeesAdapter (val attendeeList : ArrayList<Attendee>) :
RecyclerView.Adapter<AttendeesAdapter.MyViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.attendee_presented, parent, false)
        return MyViewHolder(layout)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val att_list = attendeeList[position]

        holder.name.text=att_list.name
        holder.lastname.text=att_list.lastName
        holder.email.text=att_list.email
        holder.workshopname.text= att_list.workshopName
        holder.grade.text = att_list.grade
    }

    override fun getItemCount(): Int {
        return (attendeeList.size)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder (view) {
       var  name : TextView = view.findViewById(R.id.textView3)
       val lastname: TextView = view.findViewById(R.id.textView6)
       val email : TextView = view.findViewById(R.id.textView4)
       val workshopname : TextView = view.findViewById(R.id.textView5)
        val grade : TextView = view.findViewById(R.id.gradem)
    }

}