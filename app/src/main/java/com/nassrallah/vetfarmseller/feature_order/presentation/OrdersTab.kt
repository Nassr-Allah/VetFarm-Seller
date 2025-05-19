package com.nassrallah.vetfarmseller.feature_order.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.ScaleTransition
import com.nassrallah.vetfarmseller.R
import com.nassrallah.vetfarmseller.feature_order.presentation.orders_screen.OrdersScreen

class OrdersTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(R.drawable.ic_menu_search)
            val title = stringResource(R.string.orders)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(OrdersScreen()) { navigator ->  
            ScaleTransition(navigator)
        }
    }
}