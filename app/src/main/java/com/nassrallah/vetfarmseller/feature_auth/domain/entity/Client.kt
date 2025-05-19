package com.nassrallah.vetfarmseller.feature_auth.domain.entity

data class Client(
    val id: Int? = null,
    val user: User,
    val businessName: String,
    val wilaya: String,
    val commune: String,
    val address: String,
    val category: Category
)
