package com.maialearning.calbacks

import com.google.android.gms.auth.api.signin.GoogleSignInClient

interface OnSignInStartedListener {
    fun onSignInStarted(client: GoogleSignInClient?)
}