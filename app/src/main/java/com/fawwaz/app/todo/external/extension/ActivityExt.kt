package com.fawwaz.app.todo.external.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity?.addFragment(fragment: Fragment, containerId: Int) {
    if (this != null) {
        val fm = this.supportFragmentManager
        fm.beginTransaction().add(containerId, fragment)
            .addToBackStack(null)
            .commit()
    }
}