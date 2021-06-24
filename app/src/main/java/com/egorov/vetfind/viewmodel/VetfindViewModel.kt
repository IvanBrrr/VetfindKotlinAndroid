package com.egorov.vetfind.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.egorov.vetfind.R
import com.egorov.vetfind.data.OrganizationsRepository
import com.egorov.vetfind.data.network.VetfindApi
import com.egorov.vetfind.model.Company
import com.egorov.vetfind.model.CompanyProduct
import com.egorov.vetfind.util.ErrorStatus
import com.egorov.vetfind.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class VetfindViewModel @Inject constructor(
    private val repository: OrganizationsRepository,
    application: Application
) : AndroidViewModel(application) {

    val searchProductsResponse: MutableLiveData<NetworkResult<List<CompanyProduct>>> = MutableLiveData()
    val organizationResponse: MutableLiveData<NetworkResult<List<CompanyProduct>>> = MutableLiveData()
    val organizationsResponse: MutableLiveData<NetworkResult<List<Company>>> = MutableLiveData()
    val receiveMessage = { str: Int ->
        getApplication<Application>().getString(str)
    }

    fun searchProduct(
        shortName: String,
        sortBy: String,
        latitude: String,
        longitude: String,
        isOpenNow: Boolean
    ) = viewModelScope.launch(Dispatchers.IO) {
        searchProductsResponse.postValue(NetworkResult.Loading())
        if (isInternetAvailable()) {
            try {
                val response = repository.remote.fetchProducts(shortName, sortBy, latitude, longitude, isOpenNow)

                if (response.isSuccessful) {
                    searchProductsResponse.postValue(NetworkResult.Success(response.body()!!))
                } else {
                    searchProductsResponse.postValue(errorHandler(response))
                }
            } catch (e: Exception) {
                searchProductsResponse.postValue(NetworkResult.Error(e.message))
            }
        } else {
            searchProductsResponse.postValue(internetIsNotConnected())
        }
    }

    fun fetchOrganization(
        companyId: Long
    ) = viewModelScope.launch(Dispatchers.IO) {
        organizationResponse.postValue(NetworkResult.Loading())
        if (isInternetAvailable()) {
            try {
                val response = repository.remote.fetchOrganization(companyId)

                if (response.isSuccessful) {
                    organizationResponse.postValue(NetworkResult.Success(response.body()!!))
                } else {
                    organizationResponse.postValue(errorHandler(response))
                }
            } catch (e: Exception) {
                organizationResponse.postValue(NetworkResult.Error(e.message))
            }
        } else {
            organizationResponse.postValue(internetIsNotConnected())
        }
    }

    fun fetchOrganizations() = viewModelScope.launch(Dispatchers.IO) {
        organizationsResponse.postValue(NetworkResult.Loading())
        if (isInternetAvailable()) {
            try {
                val response = repository.remote.fetchOrganizations()

                if (response.isSuccessful) {
                    organizationsResponse.postValue(NetworkResult.Success(response.body()!!))
                } else {
                    organizationsResponse.postValue(errorHandler(response))
                }
            } catch (e: Exception) {
                organizationsResponse.postValue(NetworkResult.Error(e.message))
            }
        } else {
            organizationsResponse.postValue(internetIsNotConnected())
        }
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun <T> errorHandler(response: Response<T>): NetworkResult<T> {
        val receiveMessage = { str: Int ->
            getApplication<Application>().getString(str)
        }
        return when (response.code()) {
            404 -> NetworkResult.Error(
                receiveMessage(R.string.not_found),
                ErrorStatus.NOT_FOUND
            )
            else -> NetworkResult.Error(
                receiveMessage(R.string.try_again_later),
                ErrorStatus.INTERNAL_SERVER_ERROR
            )
        }
    }

    private fun <T> internetIsNotConnected(): NetworkResult<T> {
        return NetworkResult.Error(
            receiveMessage(R.string.internet_is_not_connected),
            ErrorStatus.INTERNET_IS_NOT_AVAILABLE
        )
    }
}