package com.example.mozillaevent

import android.os.Parcelable
import android.view.View
import android.widget.ImageView
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Items (
    val image: String? = null,
    val cardName: String?=null,
    val day: String?=null,
    val description: String?=null,
    val mentorImg: String?=null,
    val mentorName: String?=null,
    val mentorDesc: String?=null,
    val Salle: String?=null,
    val startDate: Date?=null,
    val endDate: Date?=null
        ): Parcelable
{


    override fun toString(): String {
        return "Items(image=$image, cardName='$cardName', day='$day', description='$description')"
    }

}
