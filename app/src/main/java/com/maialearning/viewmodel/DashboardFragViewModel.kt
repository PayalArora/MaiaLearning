package com.maialearning.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maialearning.model.DashboardOverdueResponse
import com.maialearning.network.UseCaseResult
import com.maialearning.repository.LoginRepository
import com.maialearning.util.Coroutines
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DashboardFragViewModel(private val catRepository: LoginRepository) : ViewModel(),CoroutineScope{
    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showLoading = MutableLiveData<Boolean>()
    val overdueObserver = MutableLiveData<DashboardOverdueResponse>()
    val showError = SingleLiveEvent<String>()

    fun getOverDueCompleted(token:String,id:String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getOverDueCompleted(token,id)
            }
           // showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> overdueObserver.value = result.data
                is UseCaseResult.Error -> {
                    showLoading.value = false
                    showError.value = result.exception.response()?.errorBody()?.string()
                }
                is UseCaseResult.Exception -> {
                    showLoading.value = false
                    showError.value = result.exception.message}

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Clear our job when the linked activity is destroyed to avoid memory leaks
        job.cancel()
    }
}