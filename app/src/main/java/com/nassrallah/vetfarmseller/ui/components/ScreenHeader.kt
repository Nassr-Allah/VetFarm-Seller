package com.nassrallah.vetfarmseller.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nassrallah.vetfarmseller.R
import com.nassrallah.vetfarmseller.ui.theme.Burgundy

@Composable
fun ScreenTitle(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, color = Burgundy, style = MaterialTheme.typography.headlineLarge)
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable { onClick() },
            painter = painterResource(R.drawable.ic_arrow_back),
            contentDescription = null,
            tint = Burgundy
        )
    }
}