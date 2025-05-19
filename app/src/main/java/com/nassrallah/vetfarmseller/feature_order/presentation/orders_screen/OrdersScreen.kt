package com.nassrallah.vetfarmseller.feature_order.presentation.orders_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.nassrallah.vetfarmseller.R
import com.nassrallah.vetfarmseller.feature_order.domain.entity.ClientOrder
import com.nassrallah.vetfarmseller.feature_order.domain.entity.OrderStatus
import com.nassrallah.vetfarmseller.feature_order.presentation.order_details_screen.OrderDetailsScreen
import com.nassrallah.vetfarmseller.ui.components.DetailRow
import com.nassrallah.vetfarmseller.ui.components.HorizontalBarsAnimation
import com.nassrallah.vetfarmseller.ui.components.ItemCard
import com.nassrallah.vetfarmseller.ui.components.ScreenTitle
import com.nassrallah.vetfarmseller.ui.theme.BackgroundWhite
import com.nassrallah.vetfarmseller.ui.theme.Burgundy
import com.nassrallah.vetfarmseller.ui.theme.Gray
import com.nassrallah.vetfarmseller.ui.theme.OffBlack
import com.nassrallah.vetfarmseller.ui.theme.OffWhite
import com.nassrallah.vetfarmseller.ui.theme.PrimaryColorRed

class OrdersScreen : Screen {

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {

        val screenViewModel = getViewModel<OrdersScreenViewModel>()
        val ordersScreenState = screenViewModel.ordersScreenState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        val rowItems = mapOf(
            OrderStatus.ALL to stringResource(R.string.all),
            OrderStatus.PENDING to stringResource(R.string.sent),
            OrderStatus.CONFIRMED to stringResource(R.string.confirmed),
            OrderStatus.PAID to stringResource(R.string.paid),
            OrderStatus.REJECTED to stringResource(R.string.rejected),
            OrderStatus.ON_DELIVERY to stringResource(R.string.on_delivery),
            OrderStatus.DELIVERED to stringResource(R.string.delivered),
            OrderStatus.COMPLETED to stringResource(R.string.completed),
            OrderStatus.CANCELED to stringResource(R.string.canceled)
        )

        LaunchedEffect(key1 = ordersScreenState.value.sellerId, key2 = ordersScreenState.value.token) {
            if (ordersScreenState.value.sellerId != null && ordersScreenState.value.token != null) {
                screenViewModel.getOrders()
            }
        }

        Surface(modifier = Modifier.fillMaxSize(), color = BackgroundWhite) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                ScreenTitle(title = stringResource(R.string.orders)) {
                    navigator.pop()
                }
                FilterRow(items = rowItems.values.toList()) {
                    screenViewModel.filterOrders(OrderStatus.entries[it])
                }
                if (ordersScreenState.value.isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        HorizontalBarsAnimation(color = PrimaryColorRed, size = 30f)
                    }
                } else {
                    OrdersListSection(orders = screenViewModel.ordersList.value, statusList = rowItems) {
                        navigator.push(OrderDetailsScreen(it))
                    }
                }
            }
        }
    }

    @Composable
    fun FilterRow(modifier: Modifier = Modifier, items: List<String>, onFilter: (Int) -> Unit) {
        var selectedIndex by remember {
            mutableStateOf(0)
        }
        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(items) { item ->
                FilterRowItem(
                    title = item,
                    isSelected = selectedIndex == items.indexOf(item)
                ) {
                    selectedIndex = items.indexOf(item)
                    onFilter(items.indexOf(item))
                }
            }
        }
    }

    @Composable
    fun FilterRowItem(
        modifier: Modifier = Modifier,
        title: String,
        isSelected: Boolean,
        onClick: () -> Unit
    ) {
        val background by animateColorAsState(if (isSelected) PrimaryColorRed else Color.Transparent)
        val textColor by animateColorAsState(if (isSelected) OffWhite else Gray)
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(10.dp))
                .clickable { onClick() }
                .background(background)
                .padding(vertical = 15.dp, horizontal = 25.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = title, color = textColor)
        }
    }

    @Composable
    fun OrdersListSection(modifier: Modifier = Modifier, statusList: Map<OrderStatus, String>, orders: List<ClientOrder>, onClick: (Int) -> Unit) {
        LazyColumn(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(orders) { order ->
                val status = statusList.getValue(order.status)
                OrderItem(order = order, status = status) {
                    onClick(order.id ?: return@OrderItem)
                }
            }
        }
    }

    @Composable
    fun OrderItem(order: ClientOrder, status: String, onClick: () -> Unit) {
        val clientName = "${order.clientFirstName} ${order.clientLastName}"
        val time = "${order.dateTime?.hour}:${order.dateTime?.minute}"
        ItemCard(
            topSection = { 
                Text(
                    modifier = Modifier.padding(bottom = 5.dp),
                    text = stringResource(R.string.order_num) + " ${order.id}",
                    color = OffBlack,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.ExtraBold
                )
            },
            midSection = {
                Column(modifier = Modifier.padding(2.dp)) {
                    DetailRow(startText = stringResource(R.string.client), endText = clientName)
                    DetailRow(startText = stringResource(R.string.date), endText = order.dateTime?.date.toString())
                    DetailRow(startText = stringResource(R.string.time), endText = time)
                    DetailRow(startText = stringResource(R.string.price), endText = order.price.toInt().toString())
                }
            },
            bottomSection = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.status),
                        color = OffBlack,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = status,
                        color = PrimaryColorRed,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            onClick = { onClick() }
        )
    }
}