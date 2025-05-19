package com.nassrallah.vetfarmseller.feature_order.data.mapper

import com.nassrallah.vetfarmseller.feature_order.data.dto.ClientDTO
import com.nassrallah.vetfarmseller.feature_order.data.dto.OrderDTO
import com.nassrallah.vetfarmseller.feature_order.domain.entity.ClientOrder

fun createClientOrder(order: OrderDTO, client: ClientDTO): ClientOrder {
    return ClientOrder(
        id = order.id ?: 0,
        clientFirstName = client.user.firstName,
        clientLastName = client.user.lastName,
        dateTime = order.datetime,
        price = order.price,
        deliveryPrice = order.deliveryPrice,
        status = order.status,
        address = order.destinationAddress,
        products = order.products,
        transporterFirstName = "",
        transporterLastName = "",
        transporterPhoneNumber = ""
    )
}