package com.example.mozillaevent

import android.graphics.Color
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.widget.*
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import org.w3c.dom.Text
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
            .load(R.drawable.profile)

            //.transform(RoundedCornersTransformation(1000, 0))

            .into(photo)
        val color = ContextCompat.getColor(this, R.color.cameraBackground2)
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.OVAL
        shape.setColor(color)
        photo.background = shape


    }

    private fun setOnClick() {
        submit.setOnClickListener {
            if (name.text.isNotBlank() &&
                surname.text.isNotBlank() &&
                email.text.isNotBlank()
            ) {
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
}