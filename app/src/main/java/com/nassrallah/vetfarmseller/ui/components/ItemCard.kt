package com.nassrallah.vetfarmseller.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nassrallah.vetfarmseller.ui.theme.BackgroundWhite
import com.nassrallah.vetfarmseller.ui.theme.OffWhite

@Composable
fun ItemCard(
    topSection: @Composable () -> Unit,
    midSection: @Composable () -> Unit,
    bottomSection: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                if (onClick != null) {
                    onClick()
                }
            }
            .background(OffWhite)
            .padding(15.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            topSection()
            Divider(
                modifier = Modifier.padding(vertical = 5.dp),
                thickness = 1.dp,
                color = BackgroundWhite
            )
            midSection()
            if (bottomSection != null) {
                Divider(
                    modifier = Modifier.padding(vertical = 5.dp),
                    thickness = 1.dp,
                    color = BackgroundWhite
                )
                bottomSection()
            }
        }
    }
}