package com.example.mozillaevent

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.transition.Transition
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide
import java.time.Instant
import java.util.*


class MentorActivity : AppCompatActivity() {
    private lateinit var mentCrd: CardView
    private lateinit var wrksCrd: CardView
    private lateinit var fbBtn: Button
    private lateinit var instBtn: Button
    private lateinit var LinkedInBtn: Button
    private lateinit var dayment:TextView
    private lateinit var backBtn: ImageView
    private lateinit var s: String
    private lateinit var day: TextView
    private lateinit var mentorSess: Session
    private lateinit var mentorDesc: TextView
    private lateinit var mentorImage: ImageView
    private lateinit var workshopName: TextView
    private lateinit var workshopType: TextView
    private lateinit var workshopTime: TextView
    private lateinit var workshopImage: ImageView
    private lateinit var Salle: TextView
    private lateinit var cardbg: CardView
    private var d:Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mentor)
        supportActionBar?.hide()

        intent = getIntent()
        d= intent.getIntExtra("d",1)

        initVars()
        fillTheFields()


        day = findViewById(R.id.dayment)
        mentCrd = findViewById(R.id.mentcrd)
        wrksCrd = findViewById(R.id.wrkshcrd)
        fbBtn = findViewById(R.id.Facebook)

        val intent = getIntent()
        backBtn = findViewById(R.id.imageView6)
        s = intent.getStringExtra("act") ?: "e"
val da = intent.getIntExtra("d",1)
        if (s.trim() == "wrks") {
            ViewCompat.setTransitionName(mentCrd, "card_transition2")
            ViewCompat.setTransitionName(wrksCrd, "Mentor2")
            ViewCompat.setTransitionName(fbBtn, "btnTrans")
            window.sharedElementEnterTransition.addListener(object : Transition.TransitionListener {
                override fun onTransitionStart(transition: Transition) {
                    // Hide the original CardView during the transition
                    mentCrd.visibility = View.INVISIBLE
                    wrksCrd.visibility = View.INVISIBLE
                    fbBtn.text = "Attend"
                }

                override fun onTransitionEnd(transition: Transition) {
                    // Show the zoomed-in CardView after the transition is complete
                    mentCrd.visibility = View.VISIBLE
                    wrksCrd.visibility = View.VISIBLE
                    fbBtn.text = "Facebook"
                }

                override fun onTransitionCancel(transition: Transition) {}

                override fun onTransitionPause(transition: Transition) {}

                override fun onTransitionResume(transition: Transition) {}

                // Implement the other TransitionListener methods as needed
            })
        } else if (s.trim() == "main") {
            ViewCompat.setTransitionName(mentCrd, "Mentorm")
            ViewCompat.setTransitionName(wrksCrd, "card_transitionm")
            ViewCompat.setTransitionName(day, "day")
            window.sharedElementEnterTransition.addListener(object : Transition.TransitionListener {
                override fun onTransitionStart(transition: Transition) {
                    // Hide the original CardView during the transition
                    mentCrd.visibility = View.INVISIBLE
                    wrksCrd.visibility = View.INVISIBLE
                }

                override fun onTransitionEnd(transition: Transition) {
                    // Show the zoomed-in CardView after the transition is complete
                    mentCrd.visibility = View.VISIBLE
                    wrksCrd.visibility = View.VISIBLE
                }

                override fun onTransitionCancel(transition: Transition) {}

                override fun onTransitionPause(transition: Transition) {}

                override fun onTransitionResume(transition: Transition) {}
            })
        }
        wrksCrd.setOnClickListener {
            if (intent.getStringExtra("act")?.trim().equals("wrks"))
                onBackPressed()
            else if (s.trim() == "main") {
                val intent = Intent(this@MentorActivity, Workshop::class.java)
                val pair: Pair<View, String> = Pair.create(mentCrd, "Mentor")
                val pair1: Pair<View, String> = Pair.create(wrksCrd, "card_transition")
                val options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(this@MentorActivity, pair, pair1)
                mentorSess.startDate?.let { it1 -> intent.putExtra("d", it1.day+1) }
                intent.putExtra("Session",mentorSess)
                intent.putExtra("act", "ment")
                startActivity(intent, options.toBundle())
            }
        }

        backBtn.setOnClickListener {
            onBackPressed()
        }
        fbBtn.setOnClickListener {
            if (mentorSess.mentorFB!!.isNotBlank())
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(mentorSess.mentorFB)
                    )
                )
            else
                Toast.makeText(
                    this,
                    "Sorry the mentor does not provide his Facebook Account",
                    Toast.LENGTH_LONG
                ).show()


        }
        instBtn.setOnClickListener {
            if (mentorSess.mentorInst!!.isNotBlank())
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(mentorSess.mentorInst)
                    )
                )
            else
                Toast.makeText(
                    this,
                    "Sorry the mentor does not provide his Instagram Account",
                    Toast.LENGTH_LONG
                ).show()


        }
        LinkedInBtn.setOnClickListener {
            if (mentorSess.mentorLinkedIn!!.isNotBlank())
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(mentorSess.mentorLinkedIn)
                    )
                )
            else
                Toast.makeText(
                    this,
                    "Sorry the mentor does not provide his Linked in Account",
                    Toast.LENGTH_LONG
                ).show()


        }


    }

    private fun initVars() {
        dayment=findViewById(R.id.dayment)
        mentorSess = intent.getParcelableExtra<Session>("m")!!
        mentorImage = findViewById(R.id.MentorImg4)
        mentorDesc = findViewById(R.id.ttm)
        instBtn = findViewById(R.id.Instagram)
        LinkedInBtn = findViewById(R.id.LinkedIn)
        workshopImage = findViewById(R.id.imageView55)
        workshopName = findViewById(R.id.SessionTitlem)
        workshopType = findViewById(R.id.SessionTypem)
        workshopTime =findViewById(R.id.timem)
        Salle = findViewById(R.id.salle2)
        cardbg = findViewById(R.id.sessbg)
        mentorDesc.setMovementMethod(ScrollingMovementMethod())
    }


    private fun fillTheFields() {
        dayment.text = "DAY "+ d.toString()
        mentorDesc.text = mentorSess.mentorDesc
        workshopName.text = mentorSess.cardName
        workshopType.text = mentorSess.type
        workshopTime.text = "" + mentorSess.startDate!!.hours + ":" + mentorSess.startDate!!.minutes + " - " + mentorSess.endDate!!.hours + ":" + mentorSess.endDate!!.minutes
        Salle.text = mentorSess.Salle
        Glide.with(this)
            .load(mentorSess.mentorImg)
            .transform(RoundedCornersTransformation(50, 0))

            .into(mentorImage)
        Glide.with(this)
            .load(mentorSess.image)

            .into(workshopImage)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date = Date.from(Instant.now())
        if (date.time.toInt()>mentorSess.startDate!!.time.toInt() && date.time.toInt()<mentorSess.endDate!!.time.toInt()) {

            cardbg.setBackgroundResource(R.color.green_trnsp)
        }

        }

    }
}
