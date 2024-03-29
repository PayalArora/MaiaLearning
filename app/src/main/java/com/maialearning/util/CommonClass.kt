package com.maialearning.util

import android.text.format.DateFormat
import java.util.*

object CommonClass {

    fun getDate(timestamp: Long) :String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timestamp * 1000
        val date = DateFormat.format("MMM dd yyyy , hh:mm a",calendar).toString()
        return date
    }
    fun getTime(timestamp: Long) :String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timestamp * 1000L
        val date = DateFormat.format("hh:mm",calendar).toString()
        return date
    }
    fun getDateOnly(timestamp: Long) :String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timestamp * 1000
        val date = DateFormat.format("MMM dd yyyy",calendar).toString()
        return date
    }}
