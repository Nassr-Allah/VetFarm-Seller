package com.nassrallah.vetfarmseller.feature_inventory.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.ScaleTransition
import com.nassrallah.vetfarmseller.R
import com.nassrallah.vetfarmseller.feature_inventory.presentation.products_screen.ProductsScreen

class ProductsTab : Tab {
    
    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(R.drawable.ic_cart)
            val title = stringResource(R.string.products)

            return TabOptions(
                index = 1u,
                title = title,
                icon = icon
            )
        }

    @Composable
    override fun Content() {
        Navigator(ProductsScreen()) { navigator ->  
            ScaleTransition(navigator)
        }
    }
}