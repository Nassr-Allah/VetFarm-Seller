package com.nassrallah.vetfarmseller.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.nassrallah.vetfarmseller.R
import com.nassrallah.vetfarmseller.ui.theme.BackgroundWhite
import com.nassrallah.vetfarmseller.ui.theme.Gray
import com.nassrallah.vetfarmseller.ui.theme.OffBlack
import com.nassrallah.vetfarmseller.ui.theme.PrimaryColorRed

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    prefix: @Composable (() -> Unit)? = null
) {

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(0.9f),
        value = value,
        onValueChange = { onValueChange(it) },
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
        label = {
            Text(text = label)
        },
        enabled = enabled,
        leadingIcon = leadingIcon,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        prefix = prefix
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextFieldWithMenu(
    modifier: Modifier = Modifier.fillMaxWidth(0.9f),
    label: String,
    options: List<String>,
    onOptionSelected: (Int) -> Unit,
    isError: Boolean = false,
    defaultOption: String = ""
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedOption by remember {
        mutableIntStateOf(0)
    }
    var selectedOptionText by remember {
        mutableStateOf(defaultOption)
    }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            modifier = modifier.menuAnchor(),
            value = selectedOptionText,
            onValueChange = { },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Gray,
                focusedBorderColor = PrimaryColorRed,
                cursorColor = PrimaryColorRed,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = OffBlack,
                unfocusedTextColor = Gray,
                focusedLabelColor = PrimaryColorRed,
                unfocusedLabelColor = Gray
            ),
            isError = isError,
            shape = RoundedCornerShape(12.dp),
            label = {
                Text(text = label)
            },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
        )
        ExposedDropdownMenu(
            modifier = Modifier.background(BackgroundWhite),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(
                    text = { Text(text = option, style = MaterialTheme.typography.bodySmall, color = OffBlack) },
                    onClick = {
                        selectedOptionText = option
                        selectedOption = index
                        onOptionSelected(index)
                        expanded = false
                    }
                )
            }
        }
    }

}