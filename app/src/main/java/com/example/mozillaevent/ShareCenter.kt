package com.example.mozillaevent

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
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
import kotlin.collections.ArrayList

class ShareCenter : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView
    private lateinit var addImagesbtn: TextView
    private lateinit var addImagesImg: ImageView
    private lateinit var uploadbtn: TextView
    private lateinit var uploadImg: ImageView
    private lateinit var ImagesList: ArrayList<Uri>
    private lateinit var mProgressDialog: ProgressDialog
    private lateinit var uri: Uri
    private var PICK_IMAGE_MULTIPLE = 1
    private lateinit var adap: ImagesAdapter
    private var co:Int = 0

    companion object {
        val IMAGE_REQUEST_CODE = 1_000;
    }

    private lateinit var commentDataBase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.share_center)
        supportActionBar!!.hide()

        mProgressDialog = ProgressDialog(this)
        mProgressDialog.setTitle("Please Wait...")
        mProgressDialog.setCancelable(false)



        val commentbtn = findViewById<Button>(R.id.button)


        commentbtn.setOnClickListener {
            val commentSection = findViewById<EditText>(R.id.editText).text.toString()
            if (commentSection.isNotBlank() && commentSection.length > 20) {
                commentDataBase = FirebaseDatabase.getInstance().getReference("Comments")
                commentDataBase.child("Anonymous").push().setValue(commentSection)

                Toast.makeText(this, "Thanks for your comment", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "comments must be > 20 characters", Toast.LENGTH_LONG).show()
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
            Toast.makeText(this,"TIP: Long press to choose multiple images",Toast.LENGTH_LONG).show()
        }
        addImagesImg.setOnClickListener {
            pickImageFromGallery()
            Toast.makeText(this,"TIP: Long press to choose multiple images",Toast.LENGTH_LONG).show()

        }
        uploadbtn = findViewById(R.id.tvUpload!!)
        uploadImg = findViewById(R.id.imageView7)
        uploadbtn.setOnClickListener {
            if (!ImagesList.isEmpty()) {
                mProgressDialog.setMessage("Uploading Images "+ co + "/"+ ImagesList.size)
                mProgressDialog.show()
                for (i in 0..ImagesList.size - 1)
                    uploadImageToFirebase(ImagesList[i])
            } else Toast.makeText(this, "No Image Selected", Toast.LENGTH_LONG).show()


        }
        uploadImg.setOnClickListener {
            if (!ImagesList.isEmpty()) {
                mProgressDialog.setMessage("Uploading Images "+ co + "/"+ ImagesList.size)
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
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == Activity.RESULT_OK
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
                        mProgressDialog.setMessage("Uploading Images "+ co + "/"+ ImagesList.size)
                        if (co==recyclerview.size) {
                            mProgressDialog.dismiss()
                            Toast.makeText(this, "Images Uploaded Successfully", Toast.LENGTH_LONG)
                                .show()
                            ImagesList.clear()
                            adap.setImagesArrayList(ImagesList)
                            co=0
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
