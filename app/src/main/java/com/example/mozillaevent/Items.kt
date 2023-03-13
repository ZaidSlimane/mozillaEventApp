package com.example.mozillaevent

import android.os.Parcel
import android.os.Parcelable
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
    val mentorDesc: String?=null,
    val Salle: String?=null,
    val startDate: Date?=null,
    val endDate: Date?=null
        ): Parcelable
{


    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("startDate"),
        TODO("endDate")
    ) {
    }

    override fun toString(): String {
        return "Items(image=$image, cardName='$cardName', day='$day', description='$description')"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image)
        parcel.writeString(cardName)
        parcel.writeString(day)
        parcel.writeString(description)
        parcel.writeString(mentorImg)
        parcel.writeString(mentorName)
        parcel.writeString(mentorDesc)
        parcel.writeString(Salle)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Items> {
        override fun createFromParcel(parcel: Parcel): Items {
            return Items(parcel)
        }

        override fun newArray(size: Int): Array<Items?> {
            return arrayOfNulls(size)
        }
    }

}
