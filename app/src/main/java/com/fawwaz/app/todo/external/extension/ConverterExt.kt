package com.fawwaz.app.todo.external.extension

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.util.DisplayMetrics
import android.util.Size
import android.util.TypedValue
import android.view.Display
import android.view.WindowManager
import com.fawwaz.app.todo.MainApp

fun density(): Float {
    return Resources.getSystem().displayMetrics.density
}

fun convertDpToPixel(dp: Float): Float {
    return try {
        val metrics: DisplayMetrics = Resources.getSystem().displayMetrics
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            metrics
        )
    } catch (e: Exception) {
        0f
    }
}

fun convertPixelToDp(pixel: Int): Int {
    return try {
        val metrics: DisplayMetrics = Resources.getSystem().displayMetrics
        Math.round(pixel / metrics.density).toInt()
    } catch (e: Exception) {
        0
    }
}

fun convertSpToPixel(sp: Float): Float {
    return try {
        val metrics: DisplayMetrics = Resources.getSystem().displayMetrics
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            sp,
            metrics
        )
    } catch (e: Exception) {
        0f
    }
}

fun convertPixelToSp(pixel: Int): Float {
    return try {
        val metrics: DisplayMetrics = Resources.getSystem().displayMetrics
        pixel / metrics.scaledDensity
    } catch (e: Exception) {
        0f
    }
}

val screenHeight: Int
    get() = try {
        val metrics: DisplayMetrics = Resources.getSystem().displayMetrics
        metrics.heightPixels
    } catch (e: Exception) {
        0
    }
val screenWidth: Int
    get() = try {
        val metrics: DisplayMetrics = Resources.getSystem().displayMetrics
        metrics.widthPixels
    } catch (e: Exception) {
        0
    }

fun getScreenWidth(padding: Float): Int {
    return Math.max(0, screenWidth - convertDpToPixel(padding).toInt())
}

val screenWidthInDp: Int
    get() = try {
        val metrics: DisplayMetrics = Resources.getSystem().displayMetrics
        Math.round(metrics.widthPixels / metrics.density).toInt()
    } catch (e: Exception) {
        0
    }
val screenHeightInDp: Int
    get() = try {
        val metrics: DisplayMetrics = Resources.getSystem().displayMetrics
        Math.round(metrics.heightPixels / metrics.density).toInt()
    } catch (e: Exception) {
        0
    }
val navigationBarHeight: Int
    get() {
        var result = 0
        val resources: Resources = MainApp.instance.resources
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

val realSize: Size
    get() = getRealSize(false)

fun getRealSize(isLandscape: Boolean): Size {
    val windowManager: WindowManager =
        MainApp.instance.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    var screenHeight = 0
    var screenWidth = 0
    val display: Display = windowManager.getDefaultDisplay()
    val outPoint = Point()
    // include navigation bar
    display.getRealSize(outPoint)
    if (outPoint.y > outPoint.x) {
        screenHeight = outPoint.y
        screenWidth = outPoint.x
    } else {
        screenHeight = outPoint.x
        screenWidth = outPoint.y
    }
    return if (isLandscape) {
        Size(screenHeight, screenWidth)
    } else Size(screenWidth, screenHeight)
}