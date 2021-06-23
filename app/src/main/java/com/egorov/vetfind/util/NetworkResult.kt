package com.egorov.vetfind.util

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T): NetworkResult<T>(data)
    class Error<T>(message: String? = null, status: ErrorStatus? = null, data: T? = null): NetworkResult<T>(data, message)
    class Loading<T>: NetworkResult<T>()

}