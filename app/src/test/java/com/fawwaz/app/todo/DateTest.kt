package com.fawwaz.app.todo

import com.fawwaz.app.todo.external.extension.getCalendar
import org.junit.Test
import java.util.*

class DateTest {

    @Test
    fun calendarTest() {
        val calendar = getCalendar()
        println("Now : ${calendar.time}")
        calendar.add(Calendar.DATE, -1)
        println("Next : ${calendar.time}")
        calendar.timeInMillis = System.currentTimeMillis()
        println("Now again : ${calendar.time}")
    }
}