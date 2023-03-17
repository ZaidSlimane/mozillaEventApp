package com.example.mozillaevent

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import java.io.IOException
import java.util.*

class Login : AppCompatActivity() {

    private lateinit var photo: ImageView
    private lateinit var name: TextView
    private lateinit var surname: TextView
    private lateinit var email: TextView
    private lateinit var checkBox: CheckBox
    private lateinit var submit: Button
    private lateinit var cancel: Button
    private lateinit var text: String
    private lateinit var grd: String
    private var imageUri: Uri? = null
    private var B: Boolean = false
    private val PICK_IMAGE_REQUEST = 22
    private var IMAGE_PICKED = 0
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar!!.hide()
        photo = findViewById(R.id.imageView11)
        name = findViewById(R.id.Name)
        surname = findViewById(R.id.surname)
        email = findViewById(R.id.Email)
        checkBox = findViewById(R.id.checkBoxImp)
        submit = findViewById(R.id.Submit)
        cancel = findViewById(R.id.cancel)
        setOnClick()
        val spinner: Spinner = findViewById(R.id.spinner1)
        val Gspinner: Spinner = findViewById(R.id.spinner2)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.numbers,
            R.layout.spinner
        )
        val adapterG = ArrayAdapter.createFromResource(
            this,
            R.array.grade,
            R.layout.spinner
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                text = parent?.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        photo.setOnClickListener {
            SelectImage()
        }
        adapterG.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        Gspinner.adapter = adapterG
        Gspinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                grd = parent?.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        Glide.with(this)
            .load(R.drawable.camera)

            .transform(RoundedCornersTransformation(50, 0))

            .into(photo)
        val color = ContextCompat.getColor(this, R.color.cameraBackground)
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.OVAL
        shape.setColor(color)
        photo.background = shape
        intent = getIntent()


        B = intent.getBooleanExtra("change", false)
        if (B == true) {
            val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            name.text = sharedPref.getString("name", "")
            surname.text = sharedPref.getString("surname", "")
            email.text = sharedPref.getString("email", "")
            if (sharedPref.getString("gender", "").equals("Male")) {

            } else {
                spinner.setSelection(1)
            }
            if (sharedPref.getString("grade", "").equals("L2")) {
                Gspinner.setSelection(1)
            } else if (sharedPref.getString("grade", "").equals("L3")) {
                Gspinner.setSelection(2)
            } else if (sharedPref.getString("grade", "").equals("M1")) {
                Gspinner.setSelection(3)
            } else if (sharedPref.getString("grade", "").equals("M2")) {
                Gspinner.setSelection(4)
            } else if (sharedPref.getString("grade", "").equals("Other")) {
                Gspinner.setSelection(6)
            } else if (sharedPref.getString("grade", "").equals("ENG1")) {
                Gspinner.setSelection(5)
            }

            if (sharedPref.getString("i", "").equals("y")){
                imageUri = sharedPref.getString("image", "")!!.toUri()
                try {



                    Glide.with(this@Login)
                        .load(imageUri)
                        .transform(RoundedCornersTransformation(50, 0))
                        .into(photo)



                } catch (e: IOException) {
                    // Log the exception
                    e.printStackTrace()
                }
            }


        }


    }

    private fun setOnClick() {
        submit.setOnClickListener {
            if (name.text.isNotBlank() &&
                surname.text.isNotBlank() &&
                email.text.isNotBlank()
            ) {
                if (email.text.contains('@')) {
                    if (checkBox.isChecked) {

                        // Get shared preferences instance
                        val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

                        // Edit shared preferences
                        with(sharedPref.edit()) {
                            putBoolean("logged", true)
                            putString("name", name.text.toString())
                            putString("surname", surname.text.toString())
                            putString("email", email.text.toString())
                            putString("gender", text)
                            putString("grade", grd)
                            if (imageUri.toString().isNotBlank() && imageUri !=null){
                                Toast.makeText(this@Login,imageUri.toString(),Toast.LENGTH_LONG).show()
                                 putString("img",imageUri.toString())
                                putString("i","y")
                            }else{
                                putString("i","n")
                                Toast.makeText(this@Login,"hi2",Toast.LENGTH_LONG).show()}
                            apply() // apply changes to shared preferences
                            val intent = Intent()
                            intent.putExtra("result", "loged")
                            setResult(RESULT_OK, intent)
                            this@Login.finish()


                        }
                    } else {
                        Toast.makeText(
                            this,
                            "make sure you read and agree our conditions",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        "make sure to use a valid email",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(this, "please fill all the fields", Toast.LENGTH_LONG).show()
            }
        }

        cancel.setOnClickListener {
            val intent = Intent()
            intent.putExtra("result", "not loged")
            setResult(RESULT_OK, intent)
            this@Login.finish()

        }
    }

    private fun getImageExtension(uri: Uri): String? {
        val cr = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cr.getType(uri))
    }

    // Select Image method
    private fun SelectImage() {

        // Defining Implicit Intent to mobile gallery
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(
                intent,
                "Select Image from here..."
            ),
            PICK_IMAGE_REQUEST
        )
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
          //  Toast.makeText(this@Login,"hi",Toast.LENGTH_LONG).show()
            // Get the Uri of data
            imageUri = data.data
            try {


                photo.background = null
                Glide.with(this@Login)
                    .load(imageUri)
                    .transform(RoundedCornersTransformation(50, 0))
                    .centerCrop()
                    .into(photo)



            } catch (e: IOException) {
                // Log the exception
                e.printStackTrace()
            }
        }
    }
}