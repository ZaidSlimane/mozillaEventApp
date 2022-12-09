package com.example.mozillaevent

import android.view.View
import android.widget.ImageView
import java.util.Date

data class Items (
    val image: String? = null,
    val cardName: String?=null,
    val day: String?=null,
    val description: String?=null,
    val mentorImg: String?=null,
    val mentorName: String?=null,
    val Salle: String?=null,
    val startDate: Date?=null,
    val endDate: Date?=null
        )
{

    override fun toString(): String {
        return "Items(image=$image, cardName='$cardName', day='$day', description='$description')"
    }

}
