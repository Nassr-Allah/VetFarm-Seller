package com.nassrallah.vetfarmseller.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nassrallah.vetfarmseller.ui.theme.Gray

@Composable
fun DetailRow(startText: String, endText: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = startText, color = Gray, style = MaterialTheme.typography.bodySmall)
        Text(text = endText, color = Gray, style = MaterialTheme.typography.bodySmall)
    }
}