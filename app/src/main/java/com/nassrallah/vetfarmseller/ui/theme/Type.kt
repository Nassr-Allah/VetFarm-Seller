package com.nassrallah.vetfarmseller.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nassrallah.vetfarmseller.R

val Cairo = FontFamily(
    Font(R.font.cairo_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.cairo_normal, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.cairo_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.cairo_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.cairo_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.cairo_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.cairo_black, FontWeight.Black, FontStyle.Normal),
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Cairo,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Cairo,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Cairo,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Cairo,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Cairo,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Cairo,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = Cairo,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 26.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    )

)