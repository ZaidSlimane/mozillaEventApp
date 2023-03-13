package com.example.mozillaevent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.w3c.dom.Text

class MyBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var mHeaderView: View? = null

    private lateinit var shareImg: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharetxt = view.findViewById<TextView>(R.id.shrimg)
        shareImg = view.findViewById(R.id.shr)

        sharetxt.setOnClickListener {
            Toast.makeText(context,"  ", Toast.LENGTH_LONG).show()
            val intent = Intent(activity,ShareCenter::class.java)
            startActivity(intent)

        }
        shareImg.setOnClickListener {
            Toast.makeText(context,"  ", Toast.LENGTH_LONG).show()
            val intent = Intent(activity,ShareCenter::class.java)
            startActivity(intent)

        }


        mHeaderView = view.findViewById<View>(R.id.header_view)
    }

    override fun onStart() {
        super.onStart()

        // Set the height of the header view to match the height of the button
        val button = activity?.findViewById<View>(R.id.daymain)
        Toast.makeText(context," sdf ", Toast.LENGTH_LONG).show()




        button?.let {
            mHeaderView?.layoutParams?.height = it.height
            mHeaderView?.requestLayout()
        }

    }

}
