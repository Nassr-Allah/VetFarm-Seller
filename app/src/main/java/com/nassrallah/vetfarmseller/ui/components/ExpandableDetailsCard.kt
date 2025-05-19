package com.nassrallah.vetfarmseller.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nassrallah.vetfarmseller.R
import com.nassrallah.vetfarmseller.ui.theme.OffBlack
import com.nassrallah.vetfarmseller.ui.theme.OffWhite

@Composable
fun ExpandableDetailsCard(
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false,
    title: String,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(OffWhite)
            .clickable {
                onClick()
            }
            .animateContentSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isExpanded) {
            content()
        } else {
            Row(
                modifier = Modifier.fillMaxWidth().padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    color = OffBlack,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.ExtraBold
                )
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_down),
                    contentDescription = null,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }

}