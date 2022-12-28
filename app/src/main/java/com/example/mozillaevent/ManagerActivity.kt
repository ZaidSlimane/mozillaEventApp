package com.example.mozillaevent

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mozillaevent.databinding.AccessManagerBinding
import com.example.mozillaevent.databinding.ManagerRoomBinding
import com.google.firebase.database.*
import java.util.Collections


class ManagerActivity : AppCompatActivity() {
    private lateinit var binding: ManagerRoomBinding
    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var attendee_list: ArrayList<Attendee>
    private lateinit var dbref: DatabaseReference
    private lateinit var workshopslist: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ManagerRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerview
        attendee_list = arrayListOf<Attendee>()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ManagerActivity)
        }
        workshopslist = arrayListOf<String>()
        getWorkshopsNames()


    }

    fun getAttendeeData() {
        attendee_list.clear()

        for (i in 0..workshopslist!!.size - 1) {

            database = FirebaseDatabase.getInstance().getReference("Attendees")
            database.child(workshopslist!!.get(i))
                .addValueEventListener(object : ValueEventListener {
                    @SuppressLint("SuspiciousIndentation")
                    override fun onDataChange(snapshot: DataSnapshot) {


                        if (snapshot.exists()) {

                            for (attendeeSnap in snapshot.children) {
                                val attendee_s = attendeeSnap!!.getValue(Attendee::class.java)
                                if (!attendee_list.contains(attendee_s!!))
                                attendee_list.add(attendee_s!!)

                            }
                            //recyclerView.visibility = View.GONE
                            Collections.sort(attendee_list!!)
                            recyclerView.adapter = AttendeesAdapter(attendee_list)


                        }


                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(applicationContext, "Try again,please!", Toast.LENGTH_LONG)
                            .show()
                        this@ManagerActivity.finish()
                    }
                })
        }
    }


    fun getWorkshopsNames(): Boolean {


        dbref = FirebaseDatabase.getInstance().getReference("EventWorkshops")
        workshopslist.clear()

        for (i in 1..4) {
            dbref.child(i.toString()).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {

                        for (wssnap in snapshot.children) {

                            val wsData = wssnap.getValue(Items::class.java)
                            workshopslist.add(wsData!!.cardName!!)

                            getAttendeeData()



                        }

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })



        }


        return true


    }
}
