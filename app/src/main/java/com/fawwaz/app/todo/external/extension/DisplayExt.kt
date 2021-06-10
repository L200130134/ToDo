package com.staygrateful.developers.filesid.ext

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.*
import com.staygrateful.developers.filesid.BaseApp
import com.staygrateful.developers.filesid.Constants
import com.staygrateful.developers.filesid.R
import com.staygrateful.developers.filesid.ui.activity.HomeActivity
import com.staygrateful.developers.filesid.utils.UtilsConvert
import com.staygrateful.developers.filesid.utils.UtilsView

fun Activity?.setFullAndPadding() {
    if (this != null) {
        val window = this.window
        if (window != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                window.attributes.layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }
            window.setNoLimit(true)
            window.setLightStatus(true)

            if (this is HomeActivity) {
                this.setPadding(
                    0, UtilsConvert.statusBarHeight,
                    0, if (UtilsView.isLandscape) 0 else BaseApp.navigationHeight
                )
            }
        }
    }
}

fun Activity?.setDarkStatus(dark : Boolean, transparent: Boolean = false) {
    if (this != null) {
        window.setBackgroundDrawable(ColorDrawable(Constants.Color.colorWhite))
        window.setLightStatus(!dark, transparent)
    }
}

fun Activity?.hideSystemUI() {
    if (this != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            this.window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        window.setNoLimit(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.let {
                // Default behavior is that if navigation bar is hidden, the system will "steal" touches
                // and show it again upon user's touch. We just want the user to be able to show the
                // navigation bar by swipe, touches are handled by custom code -> change system bar behavior.
                // Alternative to deprecated SYSTEM_UI_FLAG_IMMERSIVE.
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                // make navigation bar translucent (alternative to deprecated
                // WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                // - do this already in hideSystemUI() so that the bar
                // is translucent if user swipes it up
                //window.navigationBarColor = getColor(R.color.colorTransparent)
                // Finally, hide the system bars, alternative to View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                // and SYSTEM_UI_FLAG_FULLSCREEN.
                it.hide(WindowInsets.Type.systemBars() or WindowInsets.Type.navigationBars())
            }
        } else {
            // Enables regular immersive mode.
            // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
            // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            val decorView = this.window.decorView
            decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // Hide the nav bar and status bar
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }
}

/*
    Shows the system bars by removing all the flags
    except for the ones that make the content appear under the system bars.
 */
fun Activity?.showSystemUI() {
    if (this != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            this.window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        window.setNoLimit(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // show app content in fullscreen, i. e. behind the bars when they are shown (alternative to
            // deprecated View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION and View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            window.insetsController?.let {
                //window.navigationBarColor = getColor(R.color.black)
                // finally, show the system bars
                it.show(WindowInsets.Type.systemBars() or WindowInsets.Type.navigationBars())
            }
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

fun Window?.setLightStatus(light: Boolean, transparent: Boolean = false) {
    if (this != null) {
        when {
            transparent -> {
                this.statusBarColor = Constants.Color.colorStatusBar
                this.navigationBarColor = Constants.Color.colorStatusBar
            }
            light -> {
                this.statusBarColor = Constants.Color.colorWhite
                this.navigationBarColor = Constants.Color.colorWhite
            }
            else -> {
                this.statusBarColor = Constants.Color.colorBlack
                this.navigationBarColor = Constants.Color.colorBlack
            }
        }
        var uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        if (light) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                uiOptions = (View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                        or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                uiOptions = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // light status bar
            }
        }
        this.decorView.systemUiVisibility = uiOptions
        /*showToast("light : $light")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (light) {
                this.insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            } else{
                this.insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.BEHAVIOR_SHOW_BARS_BY_TOUCH,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            }
        } else {

        }*/
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