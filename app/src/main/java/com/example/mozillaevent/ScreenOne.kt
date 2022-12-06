
package com.example.mozillaevent

import android.content.ClipData.Item
import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*


class ScreenOne : AppCompatActivity() {
    private lateinit var dbref: DatabaseReference
    private lateinit var workshopslist: ArrayList<Items>
    private lateinit var recyclerview: RecyclerView
    private lateinit var Recviewprogbar: ProgressBar

    private var day: Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dayone)


        supportActionBar!!.hide()
        day= intent.getIntExtra("day",0)

        recyclerview = findViewById<RecyclerView>(R.id.recyclerView)
        Recviewprogbar = findViewById(R.id.RecviewProgBar)

        workshopslist = arrayListOf<Items>()

        recyclerview.apply {
            layoutManager = LinearLayoutManager(this@ScreenOne)
        }
        getEventWorkshops()
    }

    private fun getEventWorkshops() {
        dbref = FirebaseDatabase.getInstance().getReference("EventWorkshops")
        dbref.child(day.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                workshopslist.clear()
                if (snapshot.exists()) {
                    for (wssnap in snapshot.children) {

                        val wsData = wssnap.getValue(Items::class.java)
                        workshopslist.add(wsData!!)

                    }
                    Recviewprogbar.visibility = View.GONE
                    val adap = Adapter(workshopslist,this@ScreenOne)
                    recyclerview.adapter = adap
                }

            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Something went wrong please try again", Toast.LENGTH_LONG).
                show()
                this@ScreenOne.finish()
            }

        })

    }
}
