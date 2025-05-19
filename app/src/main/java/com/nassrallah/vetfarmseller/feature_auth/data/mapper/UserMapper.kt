package com.nassrallah.vetfarmseller.feature_auth.data.mapper

import com.nassrallah.vetfarmseller.feature_auth.data.dto.UserDTO
import com.nassrallah.vetfarmseller.feature_auth.domain.entity.User

fun UserDTO.toUser(): User {
    return User(id, firstName, lastName, phoneNumber, email, password, isApproved)
}

fun User.toDto(): UserDTO {
    return UserDTO(id, firstName, lastName, phoneNumber, email, password, isApproved)
}