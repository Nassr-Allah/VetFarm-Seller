package com.nassrallah.vetfarmseller

import android.Manifest
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import cafe.adriel.voyager.navigator.Navigator
import com.nassrallah.vetfarmseller.feature_auth.presentation.login_screen.LoginScreen
import com.nassrallah.vetfarmseller.ui.theme.VetFarmSellerTheme
import com.nassrallah.vetfarmseller.utils.LanguageHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val language = LanguageHelper.getUserLanguage(this)
        LanguageHelper.updateLanguage(this, language)

        setContent {
            VetFarmSellerTheme {
                val layoutDirection = if (language == "ar") LayoutDirection.Rtl else LayoutDirection.Ltr

                val permissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = { }
                )

                LaunchedEffect(key1 = true) {
                    permissionLauncher.launch(arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ))
                }

                CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                    Navigator(LoginScreen())
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val language = LanguageHelper.getUserLanguage(this)
        LanguageHelper.updateLanguage(this, language)
    }
}
