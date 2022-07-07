package com.maialearning.network

import retrofit2.HttpException

sealed class UseCaseResult<out T : Any> {
    class Success<out T : Any>(val data: T) : UseCaseResult<T>()
    class Error(val exception: HttpException) : UseCaseResult<Nothing>()
    class Exception(val exception: Throwable) : UseCaseResult<Nothing>()
}