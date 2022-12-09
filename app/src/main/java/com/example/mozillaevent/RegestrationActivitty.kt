package com.example.mozillaevent

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Date

class RegestrationActivitty : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var workshopName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.regestration_layout)
        workshopName = intent.getStringExtra("workshop")!!
        showRegestrationSheet()




    }

    fun showRegestrationSheet() {
        val continueBtn = findViewById<Button>(R.id.btn_continue)

        continueBtn.setOnClickListener {
            if (findViewById<EditText>(R.id.et_name).text.isNotBlank() &&
                findViewById<EditText>(R.id.et_last_name).text.isNotBlank()&&
                findViewById<EditText>(R.id.etEmail).text.isNotBlank()) {
                val firstName = findViewById<EditText>(R.id.et_name).text
                val lastName = findViewById<EditText>(R.id.et_last_name).text
                val email = findViewById<EditText>(R.id.etEmail).text

                database = FirebaseDatabase.getInstance().getReference("1stRegestration")
                val user = Attendee(firstName.toString(), lastName.toString(), email.toString())
                database.child(workshopName).child(firstName.toString()).setValue(user).addOnSuccessListener {
                    firstName.clear()
                    lastName.toString()
                    email.clear()
                    Toast.makeText(this, "Congrats you have been registered", Toast.LENGTH_LONG)
                        .show()
                    this.finish()
                }
            }else{
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}