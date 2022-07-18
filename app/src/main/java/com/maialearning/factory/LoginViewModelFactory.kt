package com.maialearning.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maialearning.calbacks.OnSignInStartedListener
import com.maialearning.viewmodel.LoginViewModel

public class LoginViewModelFactory(
    private val application: Application,
    private val listener: OnSignInStartedListener
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {

            return LoginViewModel(application, listener) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}