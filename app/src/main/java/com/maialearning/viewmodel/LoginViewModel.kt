package com.maialearning.viewmodel

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
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
    private val _microUser = MutableLiveData<String>()

    val currentUser: LiveData<FirebaseUser> = _currentUser
    val microUser: LiveData<String> = _microUser

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
        val provider = OAuthProvider.newBuilder("microsoft.com")
        // Force re-consent.
        provider.addCustomParameter("prompt", "consent");

        val pendingResultTask: Task<AuthResult>? =  auth.getPendingAuthResult()
        if (pendingResultTask != null) {
            pendingResultTask
                .addOnSuccessListener(
                     OnSuccessListener<AuthResult>{

                         val cred:OAuthCredential = it.getCredential() as OAuthCredential
                         println("cred" + it.getCredential().toString())
                         _microUser.value =cred.accessToken

                     })
                .addOnFailureListener(
                    {
                        println( it.message)
                    })
        } else {
            // There's no pending result so you need to start the sign-in flow.
            // See below.


            auth
                .startActivityForSignInWithProvider( /* activity= */activity, provider.build())
                .addOnCompleteListener(
                    {
                        if(it.isSuccessful) {
                            val result = it.result



                            val cred:OAuthCredential = result.getCredential() as OAuthCredential
                            println("cred 1" + result.getCredential().toString())
                            _microUser.value =cred.accessToken
                            //val cred: OAuthCredential = result.getCredential() as OAuthCredential
                        }

                     //   println(( "name "+it.user?.displayName))
                    })
                .addOnFailureListener(
                    {
                        // Handle failure.
                        println( it.message)
                    })


     }
    }
}