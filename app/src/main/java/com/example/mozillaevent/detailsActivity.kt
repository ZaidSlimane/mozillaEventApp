package com.example.mozillaevent

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import org.w3c.dom.Text

private lateinit var wimg: ImageView
private lateinit var wName: TextView
private lateinit var wdescription: TextView
private lateinit var wmentorName: TextView
private lateinit var wdate_salle: TextView
private lateinit var wmentorDesc: TextView
private lateinit var wmetorimg: ImageView
private lateinit var attendBtn: Button
private lateinit var goBackBtn: Button
private lateinit var workshop:Items
class detailsActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_layout)
        supportActionBar!!.hide()
        workshop = intent.getParcelableExtra<Items>("workahop1")!!
        initVars()
        filltheFields()
        btnsOnClickListners()


    }

    private fun initVars() {
        wimg=findViewById(R.id.imageView)
        wdescription=findViewById(R.id.tvdesWorkshop)
        wName=findViewById(R.id.titleText)
        wdate_salle=findViewById(R.id.titleText2)
        wmentorDesc=findViewById(R.id.tvDes)
        wmetorimg=findViewById(R.id.imageView2)
        wmentorName=findViewById(R.id.tvMentor)
        attendBtn=findViewById(R.id.register_btn)
        goBackBtn=findViewById(R.id.back_btn)

    }

    private fun filltheFields() {

        Glide.with(this)
            .load(workshop!!.image)
            .into(wimg!!)

        Glide.with(this).asDrawable()
            .load(workshop!!.mentorImg)
            .into(findViewById(R.id.imageView2))

        wName.text= workshop!!.cardName
        wdate_salle.text= "Salle: "+workshop!!.Salle+ " On "+workshop!!.day+" form "+workshop!!.startDate!!.hours+":"+workshop!!.startDate!!.minutes+" to "+workshop!!.endDate!!.hours+":"+workshop!!.endDate!!.minutes
        wmentorName.text = workshop!!.mentorName
        wmentorDesc.text= workshop!!.mentorDesc
        wdescription.text= workshop!!.description
    }

    private fun btnsOnClickListners() {

     attendBtn.setOnClickListener(View.OnClickListener {
         val attendIntent= Intent(this@detailsActivity, RegestrationActivitty::class.java)
         attendIntent.putExtra("workshop", workshop!!.cardName)
         this.startActivity(attendIntent)
     })

        goBackBtn.setOnClickListener(View.OnClickListener {
            this.finish()
        })
    }
}