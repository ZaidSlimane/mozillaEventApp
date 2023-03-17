package com.example.mozillaevent

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.util.*


class ShareCenter : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView
    private lateinit var addImagesbtn: TextView
    private lateinit var addImagesImg: ImageView
    private lateinit var uploadbtn: TextView
    private lateinit var CommentCounter: TextView
    private lateinit var uploadImg: ImageView
    private lateinit var ImagesList: ArrayList<Uri>
    private lateinit var mProgressDialog: ProgressDialog
    private lateinit var uri: Uri
    private var PICK_IMAGE_MULTIPLE = 1
    private lateinit var adap: ImagesAdapter
    private var co: Int = 0

    companion object {
        val IMAGE_REQUEST_CODE = 1_000;
    }

    private lateinit var commentDataBase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.share_center)
        supportActionBar!!.hide()

        window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )


        mProgressDialog = ProgressDialog(this)
        mProgressDialog.setTitle("Please Wait...")
        mProgressDialog.setCancelable(false)

        CommentCounter = findViewById(R.id.counter)

        val commentbtn = findViewById<Button>(R.id.button)



        commentbtn.setOnClickListener {
            val commentSection = findViewById<EditText>(R.id.editText).text.toString()
            if (commentSection.isNotBlank() && commentSection.length > 20) {
                commentDataBase = FirebaseDatabase.getInstance().getReference("Comments")
                commentDataBase.child("Anonymous").push().setValue(commentSection)

                Toast.makeText(this, "Thanks for your comment", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "comments must be > 20 characters", Toast.LENGTH_LONG).show()

                val commentSection = findViewById<EditText>(R.id.editText)

                val mTitleTextWatcher = object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (commentSection.text.toString().trim().length <= 100) {
                            CommentCounter.text =
                                (100 - commentSection.text.toString().trim().length).toString()
                            CommentCounter.setTextColor(Color.GREEN)
                        } else {
                            CommentCounter.text = "0"
                            Toast.makeText(
                                applicationContext,
                                "100 characters are allowed",
                                Toast.LENGTH_LONG
                            ).show()
                            CommentCounter.setTextColor(Color.RED)
                        }

                    }

                    override fun afterTextChanged(s: Editable) {}
                }

                commentSection.addTextChangedListener(mTitleTextWatcher)

                commentbtn.setOnClickListener {
                    if (commentSection.text.isBlank()) {
                        Toast.makeText(this, "Blank Field not allowed", Toast.LENGTH_LONG).show()
                    } else {
                        val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                        val logged = sharedPref.getBoolean("logged", false)
                        val c = sharedPref.getString("name", "Anonymous")

                        commentDataBase = FirebaseDatabase.getInstance().getReference("Comments")
                        commentDataBase.child(c.toString()).push().setValue("${commentSection.text}")
                            .addOnSuccessListener {
                                commentSection.setText(" ")
                            }
                        commentSection.text.clear()
                        Toast.makeText(this, "Thank you for helping us", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }



        recyclerview = findViewById<RecyclerView>(R.id.ImagesRecView!!)
        ImagesList = arrayListOf<Uri>()
        ImagesList.clear()
        recyclerview.layoutManager = LinearLayoutManager(this)
        // recyclerview.setHasFixedSize(true)
        adap = ImagesAdapter(this@ShareCenter)
        recyclerview.adapter = adap
        adap.setImagesArrayList(ImagesList)

        addImagesbtn = findViewById<TextView>(R.id.Add)
        addImagesImg = findViewById(R.id.imageView6)
        addImagesbtn.setOnClickListener {
            pickImageFromGallery()
            Toast.makeText(this, "TIP: Long press to choose multiple images", Toast.LENGTH_LONG)
                .show()
        }
        addImagesImg.setOnClickListener {
            pickImageFromGallery()
            Toast.makeText(this, "TIP: Long press to choose multiple images", Toast.LENGTH_LONG)
                .show()

        }
        uploadbtn = findViewById(R.id.tvUpload!!)
        uploadImg = findViewById(R.id.imageView7)
        uploadbtn.setOnClickListener {
            if (!ImagesList.isEmpty()) {
                mProgressDialog.setMessage("Uploading Images " + co + "/" + ImagesList.size)
                mProgressDialog.show()
                for (i in 0..ImagesList.size - 1)
                    uploadImageToFirebase(ImagesList[i])
            } else Toast.makeText(this, "No Image Selected", Toast.LENGTH_LONG).show()


        }
        uploadImg.setOnClickListener {
            if (!ImagesList.isEmpty()) {
                mProgressDialog.setMessage("Uploading Images " + co + "/" + ImagesList.size)
                mProgressDialog.show()
                for (i in 0..ImagesList.size - 1)
                    uploadImageToFirebase(ImagesList[i])
            } else Toast.makeText(this, "No Image Selected", Toast.LENGTH_LONG).show()


        }


    }

    private fun pickImageFromGallery() {
        ImagesList.clear()
        var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_MULTIPLE);
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // When an Image is picked
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
            && null != data
        ) {
            if (data.getClipData() != null) {
                var count = data.clipData!!.itemCount
                for (i in 0..count - 1) {
                    var imageUri: Uri = data.clipData!!.getItemAt(i).uri
                    ImagesList.add(imageUri)
                    adap.setImagesArrayList(ImagesList)

                }
            } else if (data.getData() != null) {
                var imageUri: Uri = data.data!!
                ImagesList.add(imageUri)
                adap.setImagesArrayList(ImagesList)
            }


        }
    }

    private fun uploadImageToFirebase(fileUri: Uri) {
        if (fileUri != null) {
            val fileName = UUID.randomUUID().toString() + ".jpg"

            val database = FirebaseDatabase.getInstance()
            val refStorage = FirebaseStorage.getInstance().reference.child("images/$fileName")

            refStorage.putFile(fileUri)
                .addOnSuccessListener(
                    OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                        co += 1
                        mProgressDialog.setMessage("Uploading Images " + co + "/" + ImagesList.size)
                        if (co == recyclerview.size) {
                            mProgressDialog.dismiss()
                            Toast.makeText(this, "Images Uploaded Successfully", Toast.LENGTH_LONG)
                                .show()
                            ImagesList.clear()
                            adap.setImagesArrayList(ImagesList)
                            co = 0
                        }
                        taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                            val imageUrl = it.toString()
                        }
                    })

                ?.addOnFailureListener(OnFailureListener { e ->
                    print(e.message)
                })
        }
    }
}
