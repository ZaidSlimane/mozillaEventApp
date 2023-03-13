package com.example.mozillaevent

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.LocalDateTime



class MainActivity : AppCompatActivity() {
    private lateinit var day1:TextView
    private lateinit var day2:TextView
    private lateinit var day3:TextView
    private lateinit var day4:TextView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        day1=findViewById(R.id.tvDayOne)
        day2=findViewById(R.id.tvDayTwo)
        day3=findViewById(R.id.tvDayThere)
        day4=findViewById(R.id.tvDayFour)

        ChangeBtnsColor()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun ChangeBtnsColor() {
        if (LocalDate.now().dayOfMonth==27){
            day1.setBackgroundColor(Color.GREEN)
        }
        if (LocalDate.now().dayOfMonth==28){
            day1.setBackgroundColor(Color.GRAY)
            day2.setBackgroundColor(Color.GREEN)
        }
        if (LocalDate.now().dayOfMonth==29){
            day1.setBackgroundColor(Color.GRAY)
            day2.setBackgroundColor(Color.GRAY)
            day3.setBackgroundColor(Color.GREEN)
        }
        if (LocalDate.now().dayOfMonth==30){
            day1.setBackgroundColor(Color.GRAY)
            day2.setBackgroundColor(Color.GRAY)
            day3.setBackgroundColor(Color.GRAY)
            day4.setBackgroundColor(Color.GREEN)
        }
        if (LocalDate.now().year>2022){
            day1.setBackgroundColor(Color.GRAY)
            day2.setBackgroundColor(Color.GRAY)
            day3.setBackgroundColor(Color.GRAY)
            day4.setBackgroundColor(Color.GRAY)
        }


    }

    fun onClickday1(view:View){

        val intent = Intent(this, ScreenOne::class.java)
        intent.putExtra("day",1)
        startActivity(intent)


    }
    fun onClickday2(view:View){
        val intent = Intent(this, ScreenOne::class.java)
        intent.putExtra("day",2)
        startActivity(intent)
    }

    fun onClickday3(view:View){

        val intent = Intent(this, ScreenOne::class.java)
        intent.putExtra("day",3)
        startActivity(intent)


    }

    fun onClickday4(view:View){

        val intent = Intent(this, ScreenOne::class.java)
        intent.putExtra("day",4)
        startActivity(intent)


    }
}