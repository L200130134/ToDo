package com.staygrateful.developers.filesid.ext

import android.graphics.Rect
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes

fun ViewGroup?.addView(@LayoutRes layId : Int?) : View? {
    if (this != null && layId != null) {
        val view = LayoutInflater.from(context)
            .inflate(layId, null, false)
        this.addView(view)
        return view
    }
    return null
}

val ViewGroup.iterator: Iterator<View> get() {
    return object : Iterator<View> {
        private var i = 0

        override fun hasNext(): Boolean = childCount > 0 && i < childCount

        override fun next(): View {
            val view = getChildAt(i)
            i++
            return view
        }
    }
}

val ViewGroup.lastIterator: Iterator<View> get() {
    return object : Iterator<View> {
        private var i = Math.max(0, childCount - 1)

        override fun hasNext(): Boolean = childCount > 0 && i >= 0

        override fun next(): View {
            val view = getChildAt(i)
            i--
            return view
        }
    }
}

fun View?.removeCallbacks(vararg runnables: Runnable) {
    if (this != null) {
        for (runnable in runnables) {
            this.removeCallbacks(runnable)
        }
    }
}

fun View?.invisible(isClickable: Boolean = false) {
    if (this != null) {
        this.alpha = 0f
        this.isClickable = isClickable
        this.isFocusable = isClickable
    }
}

fun View?.visible(isClickable: Boolean = true) {
    if (this != null) {
        if (this.visibility != View.VISIBLE) {
            this.visibility = View.VISIBLE
        }
        this.alpha = 1f
        this.isClickable = isClickable
        this.isFocusable = isClickable
    }
}

fun View?.gone() {
    if (this != null) {
        this.visibility = View.GONE
    }
}

val ViewGroup.indices: IntProgression get() = 0 until this.childCount - 1

val ViewGroup.lastIndices: IntProgression get() = this.childCount - 1 downTo 0