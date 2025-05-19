package com.nassrallah.vetfarmseller.feature_profile.presentation.language_screen

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.nassrallah.vetfarmseller.R
import com.nassrallah.vetfarmseller.ui.components.CustomButton
import com.nassrallah.vetfarmseller.ui.components.CustomTextFieldWithMenu
import com.nassrallah.vetfarmseller.ui.components.ScreenTitle
import com.nassrallah.vetfarmseller.ui.theme.Gray
import com.nassrallah.vetfarmseller.ui.theme.OffWhite
import com.nassrallah.vetfarmseller.utils.LanguageHelper

class LanguageScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current.applicationContext
        val activity = LocalContext.current as Activity
        val default = LanguageHelper.getUserLanguage(context)
        val defaultLanguage = when(default) {
            "ar" -> "العربية"
            "en" -> "English"
            "fr" -> "Francais"
            else -> "العربية"
        }

        var selectedLanguage by remember {
            mutableStateOf("")
        }

        Surface(modifier = Modifier.fillMaxSize(), color = OffWhite) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                ScreenTitle(title = stringResource(R.string.language)) {
                    navigator.pop()
                }
                ChoiceSection(
                    default = defaultLanguage,
                    onSelected = { selectedLanguage = it }
                )
                CustomButton(
                    modifier = Modifier.padding(bottom = 85.dp),
                    text = stringResource(R.string.save),
                    onClick = {
                        if (selectedLanguage.isNotBlank()) {
                            LanguageHelper.storeLanguage(context, selectedLanguage)
                            LanguageHelper.updateLanguage(context, selectedLanguage)
                            activity.recreate()
                        }
                    }
                )
            }
        }
    }

    @Composable
    fun ChoiceSection(default: String, onSelected: (String) -> Unit) {

        val languages = listOf(
            "English",
            "العربية",
            "Français"
        )

        val languageTag = listOf(
            "en",
            "ar",
            "fr"
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.select_illustration),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }
            Text(
                text = stringResource(R.string.choose_lang),
                style = MaterialTheme.typography.bodyLarge,
                color = Gray,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            CustomTextFieldWithMenu(
                label = stringResource(R.string.language),
                options = languages,
                onOptionSelected = {
                    onSelected(languageTag[it])
                }
            )
        }
    }
}