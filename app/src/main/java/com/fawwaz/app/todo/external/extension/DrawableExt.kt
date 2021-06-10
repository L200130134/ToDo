package com.staygrateful.developers.filesid.ext

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import java.io.File

fun Any?.getDrawableSize(resourceId: Int): Size {
    if (this != null) {
        val ctx = getContextBy(this)
        if (ctx is Activity) {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeResource(ctx.resources, resourceId, options)
            return Size(options.outWidth, options.outHeight)
        }
    }
    return Size(0, 0)
}

fun Any?.getDrawableSize(path: String?): Size {
    if (this != null && path != null) {
        val ctx = getContextBy(this)
        if (ctx is Activity) {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(path, options)
            return Size(options.outWidth, options.outHeight)
        }
    }
    return Size(0, 0)
}