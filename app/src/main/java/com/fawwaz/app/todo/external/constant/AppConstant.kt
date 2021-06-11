package com.fawwaz.app.todo.external.constant

import androidx.core.content.ContextCompat
import com.fawwaz.app.todo.MainApp
import com.fawwaz.app.todo.R

object AppConstant {

    object Color {
        val COLOR_WHITE : Int = ContextCompat.getColor(MainApp.instance, R.color.white)
        val COLOR_TEXT : Int = ContextCompat.getColor(MainApp.instance, R.color.text)
        val COLOR_BLUE : Int = ContextCompat.getColor(MainApp.instance, R.color.blue)
        val COLOR_RED : Int = ContextCompat.getColor(MainApp.instance, R.color.text_red)
        val COLOR_BG_CARD : Int = ContextCompat.getColor(MainApp.instance, R.color.background_card)
    }

    object Task {
        val TASK_STATUS_NOT_COMPLETED : Int = 0
        val TASK_STATUS_COMPLETED : Int = 1
    }
}