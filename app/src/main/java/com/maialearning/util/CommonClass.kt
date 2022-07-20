package com.maialearning.util

import android.text.format.DateFormat
import java.util.*

object CommonClass {

    fun getDate(timestamp: Long) :String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timestamp * 1000L
        val date = DateFormat.format("dd MMM yyyy, hh:mm a",calendar).toString()
        return date
    }
}