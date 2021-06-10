package com.staygrateful.developers.filesid.ext

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.BaseRequestOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.signature.ObjectKey

const val LARGE_SIZE = 1000
const val MEDIUM_SIZE = 500
const val SMALL_SIZE = 300
const val THUMBNAIL_SIZE = 150
const val DEFAULT_SIZE = 0

fun ImageView?.loadSmallImage(path: String?, modified: Long? = null,
                              placeholder: Drawable? = null,
                              listener : RequestListener<Bitmap>? = null) {
    if (this != null && path != null) {
        loadImage(
            path, SMALL_SIZE, placeholder = placeholder,
            listener = listener, signature = getSignature(path, modified)
        )
    }
}

fun ImageView?.loadThumbnailImage(path: String?, modified: Long? = null,
                                  placeholder: Drawable? = null,
                                  listener : RequestListener<Bitmap>? = null) {
    if (this != null && path != null) {
        loadImage(
            path, THUMBNAIL_SIZE, placeholder = placeholder,
            listener = listener, signature = getSignature(path, modified)
        )
    }
}

fun ImageView?.loadImage(path: String?, size: Int = DEFAULT_SIZE,
                         signature: String? = "$path",
                         placeholder: Drawable? = null,
                         listener : RequestListener<Bitmap>? = null) {
    if (this != null && path != null) {
        var obj = Glide.with(this)
            .asBitmap()
            .load(path)
            .dontAnimate()
            .dontTransform()

        if (size > DEFAULT_SIZE) {
            obj = obj.override(size, size)
        }
        if (listener != null) {
            obj = obj.listener(listener)
        }
        if (placeholder != null) {
            obj = obj.placeholder(placeholder).error(placeholder)
        }
        if (signature != null) {
            obj = obj.signature(ObjectKey(signature))
        }

        obj.into(this)
    }
}

fun getSignature(path: String, modified: Long?): String {
    if (modified != null && modified > 0) {
        return path + modified
    }
    return path
}