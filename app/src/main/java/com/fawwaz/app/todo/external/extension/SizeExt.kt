package com.staygrateful.developers.filesid.ext

import android.graphics.Rect

data class Size(val width: Int, val height: Int) {
    val ratio: Float get() {
        return if (width > 0) {
            height.toFloat() / width
        } else {
            0f
        }
    }
}

operator fun Size.times(value: Float): Size {
    return Size((width * value).toInt(), (height * value).toInt())
}

operator fun Size.div(scale: Float): Size {
    return Size((width / scale).toInt(), (height / scale).toInt())
}

fun Rect.getSize(): Size {
    return Size(width(), height())
}