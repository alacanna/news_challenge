package com.amandalacanna.data.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val Date.formatDate: String
    get()  {
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return dateFormatter.format(this)
}