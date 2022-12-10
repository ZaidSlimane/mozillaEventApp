package com.example.mozillaevent

data class Attendee(val email:String? = null, val lastName:String?=null,
val name : String?=null, val workshopName: String?=null): Comparable<Attendee> {
    override fun compareTo(other: Attendee): Int {
        return this.name!!.compareTo(other.name!!)
    }
}