package com.example.mozillaevent

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
class Session(
    var image: String? = null,
    var cardName: String? = null,
    val day: String? = null,
    val description: String? = null,
    val mentorImg: String? = null,
    val mentorName: String? = null,
    val mentorFName: String? = null,
    val mentorDesc: String? = null,
    val mentorFB: String? = null,
    val mentorInst: String? = null,
    val mentorLinkedIn: String? = null,
    var Salle: String? = null,
    var startDate: Date? = null,
    val endDate: Date? = null,
    val type: String? = null,
    val hasMentor: Boolean? = null


) : Parcelable , Comparable<Session> {
    override fun compareTo(other: Session): Int {
        return this.startDate!!.time.compareTo(other.startDate!!.time)
    }

    /*constructor(
        image: String,
        sessionName: String,
        sessionType: String,
        startDate: Date,
        endDate: Date
    ) : this(image, sessionName, sessionType) {
        this.startDate = startDate
        this.endDate = endDate
    }*/

    /*fun getStartDate(): Date {
        return startDate
    }

    fun setStartDate(startDate: Date) {
        this.startDate = startDate
    }

    fun getEndDate(): Date {
        return endDate
    }

    fun setEndDate(endDate: Date) {
        this.endDate = endDate
    }*/


    /*
       TODO do the login thing
       TODO make the bottom sheet managers room working
       TODO find a solution for today text and discuss if we should keep it mentor or faci


    */


}
