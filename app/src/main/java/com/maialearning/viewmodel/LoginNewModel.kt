package com.maialearning.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maialearning.model.ForgetModel
import com.maialearning.model.LoginNewModel
import com.maialearning.network.UseCaseResult
import com.maialearning.repository.LoginRepository
import com.maialearning.util.Coroutines
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LoginNewModel(private val catRepository: LoginRepository) : ViewModel(), CoroutineScope {
    // Coroutine's background job
    private val job = Job()

    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showLoading = MutableLiveData<Boolean>()
    val loginObserver = MutableLiveData<LoginNewModel>()
    val forgetObserver = MutableLiveData<ForgetModel>()
    val showError = SingleLiveEvent<String>()



    fun userLogin(context: Context, email: String, password: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getUserLogin(
                    email,
                    password
                )
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> loginObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }
    fun googleLogin(email:String,id:String,id_token:String){
        showLoading.value = true
        Coroutines.ioWorker {
            val result = withContext(Dispatchers.IO) {
                catRepository.getGoogleLogin(
                    email,
                    id, id_token
                )
            }
            withContext(Dispatchers.Main) {
                showLoading.postValue(false)
                when (result) {
                    is UseCaseResult.Success -> loginObserver.value = result.data
                    is UseCaseResult.Error -> showError.value = result.exception.message
                }
            }
        }
    }
    fun microLogin(token:String){
        showLoading.value = true
        Coroutines.ioWorker {
            val result = withContext(Dispatchers.IO) {
                catRepository.getGoogleLogin(
                    "st1003@mailinator.com",
                    "NlrtXFV6JlZhDG1Z",""
                )
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> loginObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun forgetPassword(email:String){
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getForgetPassword(
                    email
                )
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> forgetObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        // Clear our job when the linked activity is destroyed to avoid memory leaks
        job.cancel()
    }
}