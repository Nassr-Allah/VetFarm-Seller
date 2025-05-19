package com.nassrallah.vetfarmseller.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nassrallah.vetfarmseller.ui.theme.OffWhite
import com.nassrallah.vetfarmseller.ui.theme.PrimaryColorRed
import com.nassrallah.vetfarmseller.ui.theme.PrimaryColorRedDisabled

@Composable
fun CustomButton(modifier: Modifier = Modifier, text: String, icon: Int? = null, onClick: () -> Unit) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = OffWhite,
            containerColor = PrimaryColorRed,
            disabledContainerColor = PrimaryColorRedDisabled
        ),
        onClick = {
            onClick()
        }
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = OffWhite,
                fontWeight = FontWeight.Bold
            )
            if (icon != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.Right
                ) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

    }
}