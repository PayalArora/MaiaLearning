package com.maialearning.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maialearning.model.DashboardOverdueResponse
import com.maialearning.network.UseCaseResult
import com.maialearning.repository.LoginRepository
import com.maialearning.util.Coroutines
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SearchFragViewModel(private val catRepository: LoginRepository) : ViewModel(){

    val showLoading = MutableLiveData<Boolean>()
    val overdueObserver = MutableLiveData<DashboardOverdueResponse>()
    val showError = SingleLiveEvent<String>()

    fun getSearchResult() {
        showLoading.value = true
        viewModelScope.launch {

        }
//        Coroutines.mainWorker {
//            val result = withContext(Dispatchers.Main) {
//                catRepository.getOverDueCompleted(token,id)
//            }
//           // showLoading.value = false
//            when (result) {
//                is UseCaseResult.Success -> overdueObserver.value = result.data
//                is UseCaseResult.Error -> {
//                    showLoading.value = false
//                    showError.value = result.exception.response()?.errorBody()?.string()
//                }
//                is UseCaseResult.Exception -> {
//                    showLoading.value = false
//                    showError.value = result.exception.message}
//
//            }
//        }
    }

}