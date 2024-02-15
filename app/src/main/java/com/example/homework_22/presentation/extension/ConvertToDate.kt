package com.example.homework_22.presentation.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.convertToDate(): String {
    val date = Date(this * 1000)
    val getDate = SimpleDateFormat("dd MMMM 'at' h:mm a", Locale.getDefault())
    return getDate.format(date)
}