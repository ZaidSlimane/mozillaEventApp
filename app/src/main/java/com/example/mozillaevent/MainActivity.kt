package com.example.mozillaevent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val t=Toast.makeText(this,"updated",Toast.LENGTH_LONG)
        t.show()



    }

    fun onClick(view:View){

        val intent = Intent(this, ScreenOne::class.java)
            startActivity(intent)


    }
}