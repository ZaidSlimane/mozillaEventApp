package com.example.mozillaevent

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegestrationActivitty : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.regestration_layout)

        showRegestrationSheet()

    }

     fun showRegestrationSheet() {
        val continueBtn = findViewById<Button>(R.id.btn_continue)

        continueBtn.setOnClickListener {
            val workshopName=findViewById<TextView>(R.id.tvName).text.toString()
            val firstName = findViewById<EditText>(R.id.et_name).text
            val lastName = findViewById<EditText>(R.id.et_last_name).text
            val email = findViewById<EditText>(R.id.etEmail).text

            database = FirebaseDatabase.getInstance().getReference(workshopName)
            val user = Attendee(firstName.toString(), lastName.toString(), email.toString())
            database.child(email.toString()).setValue(user).addOnSuccessListener {
                firstName.clear()
                lastName.toString()
                email.clear()
                Toast.makeText(this,"Congrats you have been registered" , Toast.LENGTH_LONG).show()
            }
        }
    }
}