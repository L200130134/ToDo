package com.fawwaz.app.todo.external.extension

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

fun getUserId(context: Context?) : String? {
    if (context != null) {
        val account = getAccount(context)
        if (account != null) {
            return account.email
        }
    }
    return null
}

fun getAccount(context: Context?): GoogleSignInAccount? {
    if (context != null) {
        return GoogleSignIn.getLastSignedInAccount(context)
    }
    return null
}

fun getSignInClient(context: Context?): GoogleSignInClient? {
    if (context != null) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }
    return null
}