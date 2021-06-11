package com.fawwaz.app.todo.external.extension

import android.text.format.DateFormat
import java.util.*

fun getCalendar(timestamp: Long = 0L) : Calendar {
    val calendar = Calendar.getInstance()
    if (timestamp > 0) {
        calendar.timeInMillis = timestamp
    }
    return calendar
}

fun getDateString(timestamp: Long = 0L) : String {
    val calendar = Calendar.getInstance()
    if (timestamp > 0) {
        calendar.timeInMillis = timestamp
    }
    return DateFormat.format("EEE, MMM d yyyy", calendar.time).toString()
}

fun getTimeString(millis: Long): String {
    val buf = StringBuffer()
    val hours = (millis / (1000 * 60 * 60)).toInt()
    val minutes = (millis % (1000 * 60 * 60) / (1000 * 60)).toInt()
    val seconds = (millis % (1000 * 60 * 60) % (1000 * 60) / 1000).toInt()
    if (hours > 0) {
        buf.append(String.format("%02d", hours))
            .append(":")
    }
    buf.append(String.format("%d", minutes))
        .append(":")
        .append(String.format("%02d", seconds))
    return buf.toString()
}

fun getTimeFormat(time: Long): String {
    return DateFormat.format("HH:mm", time).toString()
}