package com.staygrateful.developers.filesid.ext

fun String?.singletonList(): ArrayList<String>? {
    if (this != null) {
        val result = ArrayList<String>()
        result.add(this)
        return result
    }
    return null
}

fun String?.isSmbPath(): Boolean {
    if (this != null) {
        return this.lowercase().startsWith("smb")
    }
    return false;
}

fun String?.nonNull(): String {
    if (this != null) return this
    return ""
}

fun String?.equals(that: String): Boolean {
    if (this != null) {
        return this.equals(that)
    }
    return false
}