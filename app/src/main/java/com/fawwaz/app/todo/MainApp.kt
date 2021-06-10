package com.fawwaz.app.todo

import android.app.Application
import android.content.Context
import android.content.res.Resources

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
    }

    override fun getApplicationContext(): Context {
        return super.getApplicationContext()
    }

    companion object {
        var instance: MainApp? = null
            private set
        val res: Resources get() = instance!!.resources
    }
}