package com.example.mozillaevent

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ShareCenter : AppCompatActivity() {

    private lateinit var commentDataBase : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.share_center)

        val commentbtn =  findViewById<Button>(R.id.button)
        val commentSection = findViewById<EditText>(R.id.editText).text.toString()

       commentbtn.setOnClickListener{
           if (commentSection.isNotBlank() && commentSection.length>20){
               commentDataBase = FirebaseDatabase.getInstance().getReference("Comments")
               commentDataBase.child("Anonymous").push().setValue(commentSection)

               Toast.makeText(this,"Thanks for your comment", Toast.LENGTH_LONG).show()
           }
           else {
               Toast.makeText(this,"comments must be > 20 characters", Toast.LENGTH_LONG).show()
           }
        }
    }

    }