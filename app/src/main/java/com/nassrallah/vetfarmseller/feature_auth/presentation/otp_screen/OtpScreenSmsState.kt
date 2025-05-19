package com.nassrallah.vetfarmseller.feature_auth.presentation.otp_screen

data class OtpScreenSmsState(
    val digit1: String = "",
    val digit2: String = "",
    val digit3: String = "",
    val digit4: String = "",
    val digit5: String = "",
    val digit6: String = "",
)

fun OtpScreenSmsState.valueOf(index: Int): String {
    return when(index) {
        0 -> digit1
        1 -> digit2
        2 -> digit3
        3 -> digit4
        4 -> digit5
        5 -> digit6
        else -> ""
    }
}
