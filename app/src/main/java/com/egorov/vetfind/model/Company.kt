package com.egorov.vetfind.model

data class Company(
    val id: Long,
    val name: String,
    val address: String,
    val geo: String,
    val phone: String
)
