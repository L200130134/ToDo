package com.staygrateful.developers.filesid.ext

import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.roundToInt

fun Float?.formatDigit(decimalLength: Int, floor: Boolean = false): Float {
    if (this != null) {
        val div : Double = 10.0.pow(decimalLength.toDouble())
        if (floor) {
            return (floor(this * div) / div).toFloat()
        } else{
            return ((this * div).roundToInt() / div).toFloat()
        }
    }
    return 0f
}

fun Float?.nonNull(): Float {
    if (this != null) {
        return this
    }
    return 0f
}

fun Int?.plus(statement : Boolean, value : Int): Int {
    if (this != null) {
        if (statement) {
            return this + value
        } else{
            return this
        }
    }
    return 0
}

fun Float?.floorToInt(): Int {
    if (this != null) {
        return floor(this).toInt()
    }
    return 0
}

fun Int?.inRange(compare: Int?, range: Int): Boolean {
    if (this != null) {
        if (abs(this - (compare ?: 0)) <= range) {
            return true
        }
    }
    return false
}