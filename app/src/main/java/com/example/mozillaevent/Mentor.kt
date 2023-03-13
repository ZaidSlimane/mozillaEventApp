package com.example.mozillaevent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Mentor(
    val name: String? = null,
    val fName: String? = null,
    val image: String? = null,
    val mentorDesc: String? = null,
    val mentorFB: String? = null,
    val mentorInst: String? = null,
    val mentorLinkedIn: String? = null,
) : Parcelable {




}
