package com.nassrallah.vetfarmseller.feature_auth.data.mapper

import com.nassrallah.vetfarmseller.feature_auth.data.dto.ClientDTO
import com.nassrallah.vetfarmseller.feature_auth.domain.entity.Client

fun ClientDTO.toClient(): Client {
    return Client(id, user.toUser(), businessName, wilaya, commune, address, category)
}

fun Client.toDto(): ClientDTO {
    return ClientDTO(id, user.toDto(), businessName, wilaya, commune, address, category)
}