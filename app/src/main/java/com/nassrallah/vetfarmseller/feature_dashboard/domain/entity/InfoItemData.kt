package com.nassrallah.vetfarmseller.feature_dashboard.domain.entity

import com.nassrallah.vetfarmseller.R

data class InfoItemData(
    val titleResId: Int = R.string.my_info,
    var info: String = "ipsum",
    val iconResId: Int = R.drawable.ic_dollar
)
