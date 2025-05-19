package com.nassrallah.vetfarmseller.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nassrallah.vetfarmseller.ui.theme.Gray
import com.nassrallah.vetfarmseller.ui.theme.OffBlack
import com.nassrallah.vetfarmseller.ui.theme.PrimaryColorRed

@Composable
fun SmsDigitField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            if (it.length == 1) {
                focusManager.moveFocus(FocusDirection.Next)
            }
            onValueChange(it)
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Gray,
            focusedBorderColor = PrimaryColorRed,
            cursorColor = PrimaryColorRed,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedTextColor = OffBlack,
            unfocusedTextColor = Gray,
            focusedLabelColor = PrimaryColorRed,
            unfocusedLabelColor = Gray,
            unfocusedLeadingIconColor = Gray,
            focusedLeadingIconColor = PrimaryColorRed
        ),
        isError = isError,
        shape = RoundedCornerShape(12.dp),
        enabled = enabled,
        keyboardOptions = keyboardOptions,
        singleLine = true
    )
}