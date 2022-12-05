package com.example.mozillaevent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager

class ScreenOne : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dayone)

        getEventWorkshops()


        val recyclerview =  findViewById<RecyclerView>(R.id.recyclerView)

        val elements = listOf(
            Items(R.drawable.img, "Android workshop", "tuesday", "Make your first app"
            ),
            Items(R.drawable.img, "Android workshop", "tuesday", "Make your first app"
            ),
            Items(R.drawable.img, "Android workshop", "tuesday", "Make your first app"
            ),
            Items(R.drawable.img, "Android workshop", "tuesday", "Make your first app"
            ),
            Items(R.drawable.img, "Android workshop", "tuesday", "Make your first app"
            ),

            )

        recyclerview.apply {
            layoutManager = LinearLayoutManager(this@ScreenOne)
        }
        recyclerview.adapter= Adapter(elements)




    }

    private fun getEventWorkshops() {

    }
}