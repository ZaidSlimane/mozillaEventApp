package com.example.mozillaevent

import android.view.View
import android.widget.ImageView

data class Items (
    val image: String? = null,
    val cardName: String?=null,
    val day: String?=null,
    val description: String?=null
        )
{

    override fun toString(): String {
        return "Items(image=$image, cardName='$cardName', day='$day', description='$description')"
    }

}
