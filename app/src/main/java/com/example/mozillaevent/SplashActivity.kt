package com.example.mozillaevent

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.mozillaevent.databinding.AccessManagerBinding
import com.example.mozillaevent.databinding.SplashScreenBinding


class SplashActivity : AppCompatActivity() {
    private lateinit var binding: SplashScreenBinding
    private  val password : String ="MozillaManager"
    private lateinit var mainCardView: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainCardView=findViewById(R.id.cardView)
        mainCardView.setOnClickListener(View.OnClickListener {
            this.startActivity(Intent(this,MainActivity::class.java))
        })
        showPasswordDialog()

        }

    fun showPasswordDialog(){
        val builder = AlertDialog.Builder(this)
        val inflater : LayoutInflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.access_manager,null)
        val editText = dialogLayout.findViewById<EditText>(R.id.manager_pass)

        with (builder){
            setTitle("Manager Room")
            setPositiveButton("Ok"){
                    dialog,which->
                if (editText.text.toString().trim()==password){
                     Intent(context,ManagerActivity::class.java).also {
                        startActivity(it)
                    }
                   Toast.makeText(context,"Manager Login Successfully",Toast.LENGTH_LONG).show()
                }


            }
            setNegativeButton("Cancel"){dialog,ehich->
                Log.d("dialog", "negative button")

            }
            setView(dialogLayout)
            show()
        }

    }




}