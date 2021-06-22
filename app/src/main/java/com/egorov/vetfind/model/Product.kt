package com.egorov.vetfind.model

data class Product(
    val id: Long,
    val name: String,
    val brand: Brand,
    val bulk: String
)
