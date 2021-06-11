package com.fawwaz.app.todo.external.helper

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import java.util.*


object Date {

    fun showDatePicker(context: Context, dateSetListener: OnDateSetListener) : DatePickerDialog {
        val newCalendar: Calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            context, dateSetListener,
            newCalendar.get(Calendar.YEAR),
            newCalendar.get(Calendar.MONTH),
            newCalendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()

        return datePickerDialog
    }

    fun getCalendar(year: Int, monthOfYear: Int, dayOfMonth: Int) : Calendar {
        val newCalendar: Calendar = Calendar.getInstance()

        newCalendar.set(year, monthOfYear, dayOfMonth)

        return newCalendar
    }
}