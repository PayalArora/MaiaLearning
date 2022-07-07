package com.maialearning.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maialearning.model.ProfileResponse
import com.maialearning.network.UseCaseResult
import com.maialearning.repository.LoginRepository
import com.maialearning.util.Coroutines
import javax.inject.Inject

class ProfileViewModel @Inject public constructor(private val catRepository: LoginRepository) :
    ViewModel() {
    val showLoading = MutableLiveData<Boolean>()
    val observer = MutableLiveData<ProfileResponse>()
    val showError = SingleLiveEvent<String>()

    fun getProfile(token:String, id:String ){
        showLoading.value = true
        Coroutines.ioWorkerViewModel(viewModelScope) {
            val result = catRepository.getUserProfile(
                token,
                id
            )
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> observer.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }

    }
}