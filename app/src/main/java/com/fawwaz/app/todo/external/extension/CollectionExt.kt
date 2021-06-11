package com.staygrateful.developers.filesid.ext

import androidx.annotation.Nullable
import java.util.*

fun <T> arrayListOf(): ArrayList<T> = ArrayList()

@Nullable
fun <E> MutableList<E>?.addItem(obj : E): MutableList<E> {
    if (this != null) {
        this.add(obj)
        return this
    }
    return ArrayList()
}

@Nullable
fun <E> MutableList<E>?.getItem(position: Int): E? {
    if (this != null) {
        try {
            return this[position]
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }
    return null
}

fun <E, T> Collection<E>?.toObjects(objectConvert: ObjectConvert<E, T>): MutableList<T>? {
    if (this != null) {
        val result = mutableListOf<T>()
        for (data in this) {
            result.add(objectConvert.getObject(data))
        }
        return result
    }

    return null
}

fun <E, T> List<E>?.findIndex(objectCompare: ObjectCompare<T>): Int {
    if (this != null) {
        val len = size
        for (i in this.indices) {
            val obj = objectCompare.getData(i)
            val data = objectCompare.getDataCompare()
            val isDataGreater = if (obj is String && data is String) {
                data > obj
            } else if (obj is Char && data is Char) {
                data > obj
            } else if (obj is Int && data is Int) {
                data > obj
            } else if (obj is Float && data is Float) {
                data > obj
            } else if (obj is Long && data is Long) {
                data > obj
            } else if (obj is Double && data is Double) {
                data > obj
            } else {
                return -1
            }
            if (isDataGreater) {
                if (i >= len-1) {
                    return i + 1
                }
                //continue check
            } else {
                return i
            }
        }
    }
    return -1
}

@Nullable
fun <E> Collection<E>?.asArrayList(): ArrayList<E>? {
    if (this != null) {
        val result = ArrayList<E>()
        for (model in this) {
            result.add(model)
        }
        return result
    }
    return null
}

@Nullable
fun <E> E?.singletonList(obj : E): ArrayList<E>? {
    if (this != null) {
        val result = ArrayList<E>()
        result.add(obj)
        return result
    }
    return null
}

fun <E> Collection<E>?.hasData(): Boolean {
    if (this != null) {
        return this.isNotEmpty()
    }
    return false
}

val Collection<*>.lastIndices: IntProgression get() = this.size - 1 downTo 0


interface ObjectConvert<E, T> {
    fun getObject(data : E) : T
}

interface ObjectCompare<T> {
    fun getData(index : Int) : T
    fun getDataCompare() : T
}

/*
infix fun Int.upTo(to: Int): IntProgression {
    return IntProgression.fromClosedRange(this, to, +1)
}*/
