package com.example.mozillaevent

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.transition.Transition
import android.transition.TransitionListenerAdapter
import android.transition.Visibility
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text
import java.time.Instant
import java.util.*

class Workshop : AppCompatActivity() {
    private lateinit var bluringBG: ImageView
    private lateinit var cardView: CardView
    private lateinit var cardView2: CardView
    private lateinit var attendBtn: Button
    private lateinit var imageView: ImageView
    private lateinit var backBtn: ImageView
    private lateinit var day: TextView
    private lateinit var workshop: Session
    private lateinit var workshopName: TextView
    private lateinit var workshopType: TextView
    private lateinit var mentorName: TextView
    private lateinit var mentorFName: TextView
    private lateinit var mentorImage: ImageView
    private lateinit var mentorDesc: TextView
    private lateinit var workshopDesc: TextView
    private lateinit var workshopTime: TextView
    private lateinit var workshopImageView: ImageView
    private lateinit var mentLayout: RelativeLayout
    private lateinit var database: DatabaseReference
    private lateinit var cancelButton: Button
    private lateinit var prBar :ProgressBar
    private val LOGIN_REQUEST_CODE = 1
    private lateinit var Salle: TextView
    private lateinit var bgsess: CardView
    private lateinit var dayT: TextView
private var d:Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workshop)
        supportActionBar?.hide()

        intent = getIntent()
        d= intent.getIntExtra("d",1)
        workshop = intent.getParcelableExtra<Session>("Session")!!
        initVars()
        filltheFields()
        btnsOnClickListners()

        if (!workshop.hasMentor!!) {
            mentLayout.isVisible = false
        }



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            bluringBG.setRenderEffect(
                RenderEffect.createBlurEffect(
                    40f,
                    40f,
                    Shader.TileMode.MIRROR
                )
            )
        }




        ViewCompat.setTransitionName(cardView, "card_transition")
        ViewCompat.setTransitionName(cardView2, "Mentor")
        ViewCompat.setTransitionName(day, "day")


        window.sharedElementEnterTransition.addListener(@RequiresApi(Build.VERSION_CODES.O)
        object : TransitionListenerAdapter() {
            override fun onTransitionStart(transition: Transition?) {
                // Hide the original CardView during the transition
               // cardView.visibility = View.INVISIBLE
                //cardView2.visibility = View.INVISIBLE


            }

            override fun onTransitionEnd(transition: Transition?) {
                // Show the zoomed-in CardView after the transition is complete
              //  cardView.visibility = View.VISIBLE
              //  cardView2.visibility = View.VISIBLE
            }
        })

        backBtn.setOnClickListener {
            onBackPressed()
        }

       /* val uri = "@drawable/t"
        val imageResource = resources.getIdentifier(uri, null, packageName)
        val res = resources.getDrawable(imageResource, null) as BitmapDrawable
        imageView.setImageBitmap(res.bitmap)*/

        cardView2.setOnClickListener {
            val intent = Intent(this@Workshop, MentorActivity::class.java)
            val pair: Pair<View, String> = Pair.create(cardView, "Mentor2")
            val pair1: Pair<View, String> = Pair.create(cardView2, "card_transition2")
            val pair2: Pair<View, String> = Pair.create(attendBtn, "btnTrans")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this@Workshop,
                pair,
                pair1,
                pair2
            )
            workshop.startDate?.let { it1 -> intent.putExtra("d", it1.day+1) }
            intent.putExtra("m", workshop)
            intent.putExtra("act", "wrks")
            startActivity(intent, options.toBundle())
        }
    }


    private fun initVars() {
        dayT=findViewById(R.id.daywrks)
        prBar=findViewById(R.id.prBar)
        cancelButton = findViewById(R.id.cancel_button)
        backBtn = findViewById(R.id.imageView3)
        day = findViewById(R.id.daywrks)
        bluringBG = findViewById(R.id.imageView4)
        imageView = findViewById(R.id.imageView5)
        attendBtn = findViewById(R.id.attend_btn)
        cardView = findViewById(R.id.card_view)
        cardView2 = findViewById(R.id.card_view3)
        workshopName = findViewById(R.id.SessionTitlem)
        workshopType = findViewById(R.id.SessionTypem)
        workshopDesc = findViewById(R.id.SessDesc)
        workshopTime = findViewById(R.id.time)
        mentorName = findViewById(R.id.MentorName)
        mentorFName = findViewById(R.id.MentorFName2)
        mentorImage = findViewById(R.id.MentorImg4)
        mentorDesc = findViewById(R.id.MentorDesc)
        workshopImageView = findViewById(R.id.imageView5)
        mentLayout = findViewById(R.id.rela)
        Salle = findViewById(R.id.salle1)
        bgsess = findViewById(R.id.bgsess)
        mentorDesc.setMovementMethod(ScrollingMovementMethod())


        val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)


        val att = sharedPref.getString(workshop.cardName, "")
        if (att.equals("attend")) {
            attendBtn.visibility = View.GONE
        }else{
            cancelButton.visibility = View.GONE
        }


    }


    private fun btnsOnClickListners() {

        attendBtn.setOnClickListener {
            val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)


            val logged = sharedPref.getBoolean("logged", false)
            val name = sharedPref.getString("name", "")
            val surname = sharedPref.getString("surname", "")
            val email = sharedPref.getString("email", "")
            val gender = sharedPref.getString("gender", "")
            val grade = sharedPref.getString("grade", "")
            if (logged) {
                prBar.visibility =View.VISIBLE
                database = FirebaseDatabase.getInstance().getReference("Attendees")
                val user = Attendee(name, surname, email, workshop.cardName, grade)
                database.child(workshop.cardName!!).push().setValue(user).addOnSuccessListener {
                    attendBtn.visibility = View.GONE
                    cancelButton.visibility = View.VISIBLE
                    prBar.visibility =View.GONE
                    val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

                    // Edit shared preferences
                    with(sharedPref.edit()) {

                        putString(workshop.cardName, "attend")
                        apply()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this@Workshop,"Please Check your internet connection",Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this@Workshop,"you need to login first",Toast.LENGTH_LONG).show()
                val intentbtmsheet = Intent(this, Login::class.java)
                startActivityForResult(intentbtmsheet, LOGIN_REQUEST_CODE)

            }

        }

        cancelButton.setOnClickListener {
            attendBtn.visibility = View.VISIBLE
            cancelButton.visibility = View.GONE
            val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

            // Edit shared preferences
            with(sharedPref.edit()) {

                putString(workshop.cardName, "")
                apply()
            }
        }
    }
        fun filltheFields() {
            dayT.text = "DAY "+ d.toString()
            workshopName.text = workshop.cardName
            workshopType.text = workshop.type
            workshopDesc.text = workshop.description!!.replace("\\n", "\n")
            val stime = String.format("%02d:%02d", workshop.startDate!!.hours ,workshop.startDate!!.minutes)
            val etime = String.format("%02d:%02d", workshop.endDate!!.hours , workshop.endDate!!.minutes)
            workshopTime.text = "" + stime + " - " + etime
           Salle.text= workshop.Salle
            Glide.with(this)
                .load(workshop.image)
                .transform(RoundedCornersTransformation(50, 0))

                .into(workshopImageView)
            mentorName.text = workshop.mentorName
            mentorFName.text = workshop.mentorFName
            mentorDesc.text = workshop.mentorDesc!!.replace("\\n", "\n")
            Glide.with(this)
                .load(workshop.mentorImg)
                .transform(RoundedCornersTransformation(50, 0))

                .into(mentorImage)

             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                 val date =Date.from(Instant.now())
                if (date.time.toInt()>workshop.startDate!!.time.toInt() && date.time.toInt()<workshop.endDate!!.time.toInt()) {
                    bgsess.setBackgroundResource(R.color.green_trnsp)
                }
            }


    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LOGIN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // Create intent to restart the activity
                // Create intent to restart the activity
                val intent = Intent(this, FirstPage::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

// Start the activity
                this.startActivity(intent)

// Finish the current activity
                this.finish()
            }}}
}
