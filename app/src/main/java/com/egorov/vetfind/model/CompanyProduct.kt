package com.egorov.vetfind.model

data class CompanyProduct(
    val id: Long,
    val company: Company,
    val product: Product,
    val recordDate: String,
    val count: Int,
    val price: Int,
    val distance: String
)
