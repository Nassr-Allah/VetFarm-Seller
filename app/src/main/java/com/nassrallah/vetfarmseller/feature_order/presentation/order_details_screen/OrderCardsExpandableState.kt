package com.nassrallah.vetfarmseller.feature_order.presentation.order_details_screen

data class OrderCardsExpandableState(
    val detailsCardExpanded: Boolean = false,
    val itemsCardExpanded: Boolean = false,
    val transporterCardExpanded: Boolean = false
)
