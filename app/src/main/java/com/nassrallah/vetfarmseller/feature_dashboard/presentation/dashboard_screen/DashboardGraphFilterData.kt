package com.nassrallah.vetfarmseller.feature_dashboard.presentation.dashboard_screen

import java.util.Calendar

data class DashboardGraphFilterData(
    val year: Int = Calendar.getInstance().get(Calendar.YEAR),
    val from: Int = 1,
    val to: Int = 6
)
