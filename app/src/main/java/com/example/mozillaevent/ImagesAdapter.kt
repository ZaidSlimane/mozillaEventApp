package com.example.mozillaevent

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class ImagesAdapter( val context: Context) :
    RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    var imagess : ArrayList<Uri> = arrayListOf<Uri>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.img_item, parent, false)
        return ViewHolder(item)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       // Toast.makeText(context,imagess[position].toString(),Toast.LENGTH_LONG).show()
        val image = imagess[position]
        holder.imageT.setImageURI(image)
        //holder.imageT.text= image
    }

    override fun getItemCount(): Int {

        return (imagess.size)


    }
    public fun setImagesArrayList(ImagesArrayList: java.util.ArrayList<Uri>) {
        imagess = ImagesArrayList
        notifyDataSetChanged()
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageT: ImageView = view.findViewById<ImageView>(R.id.Simage)





        fun bind(element: String) {
//            image.setImageResource(element.image!!)




        }
    }
}


