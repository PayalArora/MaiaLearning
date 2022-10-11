package com.maialearning.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maialearning.model.ExperiencesModelResponse
import com.maialearning.model.ProfileResponse
import com.maialearning.network.BaseApplication
import com.maialearning.network.UseCaseResult
import com.maialearning.repository.LoginRepository
import com.maialearning.util.Coroutines
import com.maialearning.util.prefhandler.SharedHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonArray
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class PortfolioViewModel(private val catRepository: LoginRepository) : ViewModel(), CoroutineScope {
    // Coroutine's background job
    private val job = Job()

    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showLoading = MutableLiveData<Boolean>()
    val observer = MutableLiveData<ProfileResponse>()
    val experienceObserver = MutableLiveData<com.google.gson.JsonArray>()
    val showError = SingleLiveEvent<String>()

    fun getProfile(id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getUserProfile(
                    "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                    id
                )
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> observer.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun getExperiences(id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getExperiences(id)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> experienceObserver.value = result.data
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