package com.example.mozillaevent

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class BottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var database: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view =  inflater.inflate(R.layout.regestration_layout, container,false)
        var continue_btn = view.findViewById<Button>(R.id.btn_continue)

        continue_btn.setOnClickListener {

            val workshopName=view.findViewById<TextView>(R.id.tvName).text.toString()
            val firstName = view.findViewById<EditText>(R.id.et_name).text.toString()
            val lastName = view.findViewById<EditText>(R.id.et_last_name).text.toString()
            val email = view.findViewById<EditText>(R.id.etEmail).text.toString()

            database = FirebaseDatabase.getInstance().getReference(workshopName)
            val user = Attendee(firstName, lastName, email, null)
            database.child(email).setValue(user)
            }
        return view
        }

    }
