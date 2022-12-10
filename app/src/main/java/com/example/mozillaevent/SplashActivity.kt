package com.example.mozillaevent

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mozillaevent.databinding.SplashScreenBinding


class SplashActivity : AppCompatActivity() {
    private lateinit var binding: SplashScreenBinding
    private val password: String = "MozillaManager"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        binding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.DeVision.setOnClickListener{

           val intent =  Intent(applicationContext,MainActivity::class.java)
                startActivity(intent)
        }

        val image  = findViewById<TextView>(R.id.tvSection3)
        image.setOnClickListener {


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


    }
}