package com.fawwaz.app.todo

import android.app.Application
import android.content.Context
import android.content.res.Resources

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        fun getDimension(id: Int): Float {
            return instance.resources.getDimension(id)
        }

        lateinit var instance: MainApp
            private set
        val res: Resources get() = instance.resources
    }
}