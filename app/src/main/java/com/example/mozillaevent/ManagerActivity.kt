package com.example.mozillaevent

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mozillaevent.databinding.AccessManagerBinding


class ManagerActivity : AppCompatActivity() {
   private lateinit var binding: AccessManagerBinding
   private final val password : String ="MozillaManager"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AccessManagerBinding.inflate(layoutInflater)

        }





    }
