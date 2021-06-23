package com.egorov.vetfind.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.egorov.vetfind.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {
    val response: MutableLiveData<NetworkResult<*>> = MutableLiveData()

    fun setResponse(res: NetworkResult<*>) {
        response.postValue(res)
    }
}