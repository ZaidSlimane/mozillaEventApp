package com.example.mozillaevent


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.RenderEffect
import android.graphics.Shader
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.*
import java.io.IOException
import java.time.Instant
import java.util.*

class FirstPage : AppCompatActivity() {
    private lateinit var viewPager2: ViewPager2
    private lateinit var SessList: ArrayList<Session>
    private lateinit var adapter: SessionsSliderAdapter
    private lateinit var bluringBG: ImageView
    private lateinit var mentorsList: ArrayList<Mentor>
    private lateinit var mAdapter: MentorsAdapter
    private lateinit var day: TextView
    private lateinit var settings: ImageView
    private lateinit var mRecView: RecyclerView
    private lateinit var dbref: DatabaseReference
    private lateinit var intentbtmsheet: Intent
    private lateinit var previousDay: ImageView
    private lateinit var nextDay: ImageView
    private lateinit var textDay: TextView
    private lateinit var photo: ImageView
    private lateinit var LoginLayout: ConstraintLayout
    private lateinit var nameTextView: TextView
    private val LOGIN_REQUEST_CODE = 1
    private lateinit var name: String
    private var imageUri: Uri? = null
    var DAY = 1
    private val password: String = "MozillaManager"


    // private lateinit var workshopslist: ArrayList<Session>
    private lateinit var recyclerview: RecyclerView
    private lateinit var Recviewprogbar: ProgressBar
    private lateinit var mentProgbar: ProgressBar

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)



        intent = getIntent()

        val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)


        val logged = sharedPref.getBoolean("logged", false)
        name = sharedPref.getString("name", "")!!
        val surname = sharedPref.getString("surname", "")
        val email = sharedPref.getString("email", "")
        val gender = sharedPref.getString("gender", "")
        val grade = sharedPref.getString("grade", "")
        nameTextView = findViewById(R.id.textView16)


        DAY = intent.getIntExtra("day", 1)

//        Log.d("tag","dsfdf")
        supportActionBar?.hide()
        bluringBG = findViewById(R.id.FirstPageBackgroundImage)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            bluringBG.setRenderEffect(
                RenderEffect.createBlurEffect(
                    40F,
                    40F,
                    Shader.TileMode.MIRROR
                )
            )


        }
        LoginLayout = findViewById(R.id.Login)
        photo = findViewById(R.id.loginPhoto)
        Recviewprogbar = findViewById(R.id.wrksProgBar)
        mentProgbar = findViewById(R.id.mentProgBar)
        day = findViewById(R.id.daymain)
        mRecView = findViewById(R.id.MentorsRecView)
        settings = findViewById(R.id.settings)
        viewPager2 = findViewById(R.id.ViewPager)
        previousDay = findViewById(R.id.imageView10)
        nextDay = findViewById(R.id.imageView7)
        SessList = ArrayList()
        textDay = findViewById(R.id.daymain)

        if (logged) {
            nameTextView.text = name
            if (sharedPref.getString("i", "").equals("y")){
                loadImage()


          }else{

                Glide.with(this)
                    .load(R.drawable.profile)

                    .transform(RoundedCornersTransformation(50, 0))

                    .into(photo)
            }

        } else {
            Glide.with(this)
                .load(R.drawable.camera)

                //.transform(RoundedCornersTransformation(50, 0))

                .into(photo)

            LoginLayout.setOnClickListener({
                intentbtmsheet = Intent(this, Login::class.java)
                intentbtmsheet.putExtra("change", true)
                startActivityForResult(intentbtmsheet, LOGIN_REQUEST_CODE)
            })
        }


        getEventWorkshops()
        textDay.text = "DAY " + DAY
        if (DAY == 1) {
            previousDay.visibility = View.INVISIBLE
        }
        if (DAY == 3) {
            nextDay.visibility = View.INVISIBLE
        }

        nextDay.setOnClickListener({
            intent = Intent(this, FirstPage::class.java)
            intent.putExtra("day", DAY + 1)
            startActivity(intent)
        })
        previousDay.setOnClickListener({
            onBackPressed()
        })

        /*val date: Date = Date(2022, 11, 22, 11, 11)
        SessList.add(Session("", "Development", "", "", "", "", "", "workshop", date, date))
        SessList.add(Session("", "Development", "", "", "", "", "", "workshop", date, date))
        SessList.add(Session("", "Development", "", "", "", "", "", "workshop", date, date))
*/



        val color = ContextCompat.getColor(this, R.color.cameraBackground)
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.OVAL
        shape.setColor(color)

// Set the CircleShape drawable as the background of the ImageView
        photo.background = shape


        viewPager2.offscreenPageLimit = 3
        viewPager2.clipChildren = false
        viewPager2.clipToPadding = false

        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(30))
        transformer.addTransformer(ViewPager2.PageTransformer { page, position ->
            val r = 1 - Math.abs(position)
            page.scaleY = 0.85f + r * 0.14f
        })
        viewPager2.setPageTransformer(transformer)

        mentorsList = ArrayList()

        /* mentorsList.add(Mentor("ouassim", "azib", ""))
         mentorsList.add(Mentor("ouassim", "azib", ""))
         mentorsList.add(Mentor("ouassim", "azib", ""))
         mentorsList.add(Mentor("ouassim", "azib", ""))*/

        mAdapter = MentorsAdapter(this, mentorsList, viewPager2, day, SessList)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(
            1, //The number of Columns in the grid
            LinearLayoutManager.HORIZONTAL
        )
        mRecView.layoutManager = staggeredGridLayoutManager
        adapter = SessionsSliderAdapter(SessList, viewPager2, this, mRecView, day, mAdapter)
        mAdapter.SessAdapter = adapter

        val date = Date.from(Instant.now())


    }


    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val window = bottomSheetDialog.window
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        val bottomSheetView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout, null)
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getEventWorkshops() {
        dbref = FirebaseDatabase.getInstance().getReference("test")
        // val date= Date.from(Instant.now())
        // val date2= Date.from(Instant.now())
        // val i=Items("dd","dd","d","asdasf0","sadf","dsgaf","ASDf",date,date2)
        //dbref.child(day.toString()).child("test").child("SDFg").setValue(i)
        dbref.child(DAY.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                SessList.clear()
                if (snapshot.exists()) {
                    for (wssnap in snapshot.children) {

                        val wsData = wssnap.getValue(Session::class.java)
                        SessList.add(wsData!!)
                        //Toast.makeText(this@FirstPage, wsData.toString(), Toast.LENGTH_SHORT).show()

                    }
                    //TODO make a progressBar for both mentors and workshops
                    Recviewprogbar.visibility = View.GONE
                    SessList.sort()
                    viewPager2.adapter = adapter
                    buildMentorsList()
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    applicationContext,
                    "Something went wrong please try again",
                    Toast.LENGTH_LONG
                ).show()
                // this@FirstPage.finish()
            }

        })

    }

    public fun shareImg(view: View) {
        intentbtmsheet = Intent(this, ShareCenter::class.java)
        startActivity(intentbtmsheet)
    }

    public fun shareTxt(view: View) {
        intentbtmsheet = Intent(this, ShareCenter::class.java)
        startActivity(intentbtmsheet)
    }

    public fun ManageTxt(view: View) {
        statrtManagersDialog()
    }

    public fun ManageImg(view: View) {
        statrtManagersDialog()
    }

    fun changePrftxt(view: View) {
        changePr()
    }

    fun changePrfImg(view: View) {
        changePr()
    }

    fun changePr() {
        val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)


        // Create intent to restart the activity
        // Create intent to restart the activity
        val intent = Intent(this, FirstPage::class.java)


        intentbtmsheet = Intent(this, Login::class.java)
        intentbtmsheet.putExtra("change", true)
        startActivityForResult(intentbtmsheet, LOGIN_REQUEST_CODE)

    }


    fun FacisSpaceImg(view: View) {

    }

    fun FacisSpaceTxt(view: View) {

    }

    fun TurnOff(view: View) {
        // Finish the current activity and all activities in the task
        this.finishAffinity()

    }

    fun LogOut(view: View) {
        // Get shared preferences instance
        val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

// Clear all data in shared preferences
        with(sharedPref.edit()) {
            clear()
            apply() // apply changes to shared preferences
        }
        // Create intent to restart the activity
        // Create intent to restart the activity
        val intent = Intent(this, FirstPage::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

// Start the activity
        this.startActivity(intent)

// Finish the current activity
        this.finish()


    }

    private fun statrtManagersDialog() {

        val builder = AlertDialog.Builder(this)
        val inflater: LayoutInflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.access_manager, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.manager_pass)

        with(builder) {
            setTitle("Manager Room")
            setPositiveButton("Ok") { dialog, which ->
                if (editText.text.toString().trim() == password) {
                    Intent(context, ManagerActivity::class.java).also {
                        startActivity(it)
                    }
                }
                setNegativeButton("Cancel") { dialog, ehich ->
                    Toast.makeText(context, "Oops, try again", Toast.LENGTH_LONG).show()
                    Log.d("dialog", "negative button")
                }

            }
            setView(dialogLayout)
            show()

        }
    }


    private fun buildMentorsList() {

        for (sess: Session in SessList) {
            if (sess.hasMentor == true) {

                mentorsList.add(
                    Mentor(
                        sess.mentorName!!,
                        sess.mentorFName!!,
                        sess.mentorImg,
                        sess.mentorDesc,
                        sess.mentorFB,
                        sess.mentorInst,
                        sess.mentorLinkedIn
                    )
                )
            }
        }
        mentProgbar.visibility = View.GONE
        mRecView.adapter = mAdapter
        settings.setOnClickListener {
            showBottomSheetDialog()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LOGIN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Handle the result
                val result = data?.getStringExtra("result")
                if (result.equals("loged")) {
                    Toast.makeText(this, "Congratulations you are now logged in", Toast.LENGTH_LONG)
                        .show()

                    val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                    name = sharedPref.getString("name", "")!!
                    nameTextView.text = name
                    if (sharedPref.getString("img", "")!!.isNotBlank())
                        loadImage()
                    else
                        Glide.with(this)
                            .load(R.drawable.profile)

                            .transform(RoundedCornersTransformation(50, 0))

                            .into(photo)

                    LoginLayout.setOnClickListener {

                    }

                }
                // ...
            } else if (resultCode == RESULT_CANCELED) {
                // Handle the cancellation
                // ...
            }
        }
    }

    fun loadImage() {
        val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        // Get the Uri of data
        imageUri = sharedPref.getString("img","")?.toUri()
        try {

            // Setting image on image view using Bitmap
            /*val bitmap = MediaStore.Images.Media
                .getBitmap(
                    contentResolver,
                    imageUri
                )*/
            Glide.with(this@FirstPage)
                .load(imageUri)
                .centerCrop()
                .transform(RoundedCornersTransformation(50, 0))

                .into(photo)

        } catch (e: IOException) {
            // Log the exception
            e.printStackTrace()
        }
    }

}
