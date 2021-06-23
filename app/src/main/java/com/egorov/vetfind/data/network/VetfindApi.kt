package com.egorov.vetfind.data.network

import com.egorov.vetfind.model.CompanyProduct
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface VetfindApi {
    @GET("find/by-short-name")
    suspend fun fetchProducts(
        @Query("shortName") shortName: String,
        @Query("sortBy") sortBy: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
    ): Response<List<CompanyProduct>>

    @GET("find/by-company-id")
    suspend fun fetchOrganization(
        @Query("companyId") companyId: Long
    ): Response<List<CompanyProduct>>
}