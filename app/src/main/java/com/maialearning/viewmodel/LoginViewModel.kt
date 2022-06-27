package com.maialearning.viewmodel

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.maialearning.R
import com.maialearning.calbacks.OnSignInStartedListener


class LoginViewModel(private val app: Application, private val listener: OnSignInStartedListener) :
    AndroidViewModel(app) {
    private var auth: FirebaseAuth = Firebase.auth


    private val _currentUser = MutableLiveData<FirebaseUser>()

    val currentUser: LiveData<FirebaseUser> = _currentUser

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(app.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    private val googleSignInClient = GoogleSignIn.getClient(app, gso)

    fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                _currentUser.value = auth.currentUser
            } else {
                _currentUser.value = null
            }
        }
    }


    fun signIn() {
        listener.onSignInStarted(googleSignInClient)
    }

    fun signOut() {
        auth.signOut()
    }

    fun signinToMicrosoft(activity: Activity) {

    }
}