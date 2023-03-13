package com.example.mozillaevent

data class Attendee(val email:String? = null, val lastName:String?=null,
val name : String?=null, val workshopName: String?=null,val grade: String?=null): Comparable<Attendee> {
    override fun compareTo(other: Attendee): Int {
        this.name!!.compareTo(other.name!!)
        return this.workshopName!!.compareTo(other.workshopName!!)
    }
}