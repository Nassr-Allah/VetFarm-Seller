package com.nassrallah.vetfarmseller

import android.Manifest
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.transitions.ScreenTransition
import cafe.adriel.voyager.transitions.SlideTransition
import com.nassrallah.vetfarmseller.feature_dashboard.presentation.home_screen.HomeScreen
import com.nassrallah.vetfarmseller.feature_inventory.presentation.ProductsTab
import com.nassrallah.vetfarmseller.feature_inventory.presentation.products_screen.ProductsScreen
import com.nassrallah.vetfarmseller.feature_order.presentation.OrdersTab
import com.nassrallah.vetfarmseller.feature_order.presentation.orders_screen.OrdersScreen
import com.nassrallah.vetfarmseller.feature_profile.presentation.ProfileTab
import com.nassrallah.vetfarmseller.ui.theme.Gray
import com.nassrallah.vetfarmseller.ui.theme.OffWhite
import com.nassrallah.vetfarmseller.ui.theme.PrimaryColorRed
import com.nassrallah.vetfarmseller.ui.theme.VetFarmSellerTheme
import com.nassrallah.vetfarmseller.utils.LanguageHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
                    Navigator(HomeScreen()) {
                        SlideTransition(navigator = it)
                    }
                }
            }
        }
    }

    @Composable
    private fun RowScope.TabNavigationItem(tab: Tab) {
        val tabNavigator = LocalTabNavigator.current

        NavigationBarItem(
            selected = tabNavigator.current.key == tab.key,
            onClick = { tabNavigator.current = tab },
            icon = {
                Icon(
                    painter = tab.options.icon!!,
                    contentDescription = null
                )
            },
            label = {
                Text(text = tab.options.title, style = MaterialTheme.typography.bodySmall)
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = OffWhite,
                selectedIconColor = PrimaryColorRed,
                selectedTextColor = PrimaryColorRed,
                unselectedIconColor = Gray,
                unselectedTextColor = Gray
            )
        )
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val language = LanguageHelper.getUserLanguage(this)
        LanguageHelper.updateLanguage(this, language)
    }
}