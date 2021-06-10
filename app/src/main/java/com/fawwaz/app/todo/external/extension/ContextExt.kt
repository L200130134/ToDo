package com.staygrateful.developers.filesid.ext

import android.app.Activity
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.fawwaz.app.todo.MainApp

fun <T : Fragment> AppCompatActivity?.getFragment(clazz: Class<T>) : T? {
    if (this != null) {
        val fm: FragmentManager = this.supportFragmentManager
        val fpl: List<Fragment> = fm.fragments
        for (it in fpl.lastIndices) {
            val fragment = fpl[it]
            if (fragment::class.java.isAssignableFrom(clazz)) {
                return fragment as T
            }
        }
    }
    return null
}

fun Fragment?.removeFragment() {
    this?.parentFragmentManager?.beginTransaction()?.remove(this)?.commit()
}

fun Drawable?.setColor(@ColorInt color : Int) {
    if (this != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_IN)
        } else {
            @Suppress("DEPRECATION")
            this.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }
    }
}

fun Any?.getDrawable(resourcesIds: Int): Drawable? {
    if (this != null) {
        val ctx : Context? = getContextBy(this)
        if (ctx is Activity) {
            return ContextCompat.getDrawable(ctx, resourcesIds)
        }
    }
    return null
}

fun Any?.postUi(runnable: Runnable) {
    if (this != null) {
        val ctx : Context? = getContextBy(this)
        if (ctx is View) {
            ctx.post(runnable)
        } else{
            Handler(Looper.getMainLooper()).post(runnable)
        }
    }
}

fun Any?.postToast(msg : String) {
    if (this != null) {
        val ctx : Context? = getContextBy(this)
        Handler(Looper.getMainLooper()).post{
            Toast.makeText(ctx!!, msg, Toast.LENGTH_SHORT).show()
        }
    }
}

fun Any?.postLongToast(msg : String) {
    if (this != null) {
        val ctx : Context? = getContextBy(this)
        Handler(Looper.getMainLooper()).post{
            Toast.makeText(ctx!!, msg, Toast.LENGTH_LONG).show()
        }
    }
}

fun Any?.showToast(msg : String, gravity: Int = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL) {
    if (this != null) {
        val ctx : Context? = getContextBy(this)
        val toast = Toast.makeText(ctx, msg, Toast.LENGTH_SHORT)
        toast.setGravity(gravity, 0, 0)
        toast.show()
    }
}

fun Any?.showLongToast(msg : String) {
    if (this != null) {
        val ctx : Context? = getContextBy(this)
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show()
    }
}

fun getContextBy(any: Any): Context? {
    return if (any is Context) {
        any
    } else if (any is Activity) {
        any
    } else if (any is Fragment) {
        any.context
    } else if (any is View) {
        any.context
    } else{
        MainApp.instance
    }
}