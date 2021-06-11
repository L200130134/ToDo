package com.staygrateful.developers.filesid.ext

import android.app.Activity
import android.os.Build
import android.view.*
import androidx.core.content.ContextCompat
import com.fawwaz.app.todo.R

fun Activity?.hideSystemUI() {
    if (this != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            this.window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        window.setNoLimit(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.let {
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                it.hide(WindowInsets.Type.systemBars() or WindowInsets.Type.navigationBars())
            }
        } else {
            val decorView = this.window.decorView
            decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // Hide the nav bar and status bar
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }
}

fun Activity?.showSystemUI() {
    if (this != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            this.window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        window.setNoLimit(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.show(WindowInsets.Type.systemBars()
                    or WindowInsets.Type.navigationBars())
        } else {
            val decorView = this.window.decorView
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }
}

fun Window?.setNoLimit(noLimit: Boolean) {
    if (this != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.setDecorFitsSystemWindows(!noLimit)
        } else {
            if (noLimit) {
                this.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            } else {
                this.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            }
        }
    }
}

fun Window?.setLightNavigation(light: Boolean) {
    if (this != null) {
        when {
            light -> {
                this.navigationBarColor = ContextCompat.getColor(context, R.color.white)
            }
            else -> {
                this.navigationBarColor = ContextCompat.getColor(context, R.color.black)
            }
        }
        var uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        if (light) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                uiOptions = (View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
            }
        }
        this.decorView.systemUiVisibility = uiOptions
    }
}

fun Activity?.fullscreen(fullscreen : Boolean) {
    if (this != null) {
        if (fullscreen) {
            this.hideSystemUI()
        } else{
            this.showSystemUI()
        }
    }
}