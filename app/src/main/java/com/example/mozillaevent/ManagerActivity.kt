package com.example.mozillaevent

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


class ManagerActivity : AppCompatActivity() {
   private lateinit var binding: ManagerRoomBinding
   private  lateinit var database: DatabaseReference
   private lateinit var recyclerView: RecyclerView
   private lateinit var attendee_list: ArrayList<Attendee>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ManagerRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerview
        attendee_list = arrayListOf<Attendee>()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ManagerActivity)
        }

        getAttendeeData()

        }

    fun getAttendeeData(){
       database = FirebaseDatabase.getInstance().getReference("Attendees")
        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                attendee_list.clear()
                if (snapshot.exists()) {
                    for (attendeeSnap in snapshot.children) {
                      val attendee_s = attendeeSnap!!.getValue(Attendee::class.java)
                        attendee_list.add(attendee_s!!)
                        Toast.makeText(applicationContext, "Try again,please!"+attendee_s.toString(), Toast.LENGTH_LONG).
                        show()
                    }
                    //recyclerView.visibility = View.GONE
                    recyclerView.adapter= AttendeesAdapter(attendee_list)


                }


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Try again,please!", Toast.LENGTH_LONG).
                show()
                this@ManagerActivity.finish()
            }
        })

    }






    }
