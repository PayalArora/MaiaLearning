package com.maialearning.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maialearning.model.ProfileResponse
import com.maialearning.network.UseCaseResult
import com.maialearning.repository.LoginRepository
import com.maialearning.util.Coroutines
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DashboardViewModel(private val catRepository: LoginRepository) : ViewModel(), CoroutineScope {
    // Coroutine's background job
    private val job = Job()

    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showLoading = MutableLiveData<Boolean>()
    val jwtObserver = MutableLiveData<String>()
    val showError = SingleLiveEvent<String>()

    val observer = MutableLiveData<ProfileResponse>()
    fun getJwtToken() {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getJWTToken(
                )
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> jwtObserver.value = result.data
                is UseCaseResult.Error -> {
                    showError.value = result.exception.response()?.errorBody()?.string()
                }
                is UseCaseResult.Exception -> showError.value = result.exception.message

            }
        }
    }
    fun getProfile(token: String, id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getUserProfile(token, id)
            }
            when (result) {
                is UseCaseResult.Success -> observer.value = result.data
                is UseCaseResult.Error -> {
                    showError.value = result.exception.response()?.errorBody()?.string()
                }
                is UseCaseResult.Exception -> showError.value = result.exception.message

            }
        }

    }
    override fun onCleared() {
        super.onCleared()
        // Clear our job when the linked activity is destroyed to avoid memory leaks
        job.cancel()
    }
}