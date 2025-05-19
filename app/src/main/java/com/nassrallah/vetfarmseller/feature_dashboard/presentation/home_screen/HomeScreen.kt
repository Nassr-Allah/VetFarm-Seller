package com.nassrallah.vetfarmseller.feature_dashboard.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.nassrallah.vetfarmseller.R
import com.nassrallah.vetfarmseller.feature_dashboard.domain.entity.OrdersCount
import com.nassrallah.vetfarmseller.feature_dashboard.presentation.dashboard_screen.DashboardScreen
import com.nassrallah.vetfarmseller.feature_inventory.data.dto.ProductDTO
import com.nassrallah.vetfarmseller.feature_inventory.domain.entity.Products
import com.nassrallah.vetfarmseller.feature_inventory.presentation.products_screen.ProductsScreen
import com.nassrallah.vetfarmseller.feature_order.data.dto.OrderDTO
import com.nassrallah.vetfarmseller.feature_order.presentation.orders_screen.OrdersScreen
import com.nassrallah.vetfarmseller.feature_profile.presentation.profile_screen.ProfileScreen
import com.nassrallah.vetfarmseller.ui.components.HorizontalBarsAnimation
import com.nassrallah.vetfarmseller.ui.theme.BackgroundWhite
import com.nassrallah.vetfarmseller.ui.theme.Burgundy
import com.nassrallah.vetfarmseller.ui.theme.Gray
import com.nassrallah.vetfarmseller.ui.theme.OffBlack
import com.nassrallah.vetfarmseller.ui.theme.OffWhite
import com.nassrallah.vetfarmseller.ui.theme.PrimaryColorRed
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        
        val screenViewModel = getViewModel<HomeScreenViewModel>()
        val ordersState = screenViewModel.ordersState.collectAsState()
        val productsState = screenViewModel.productsState.collectAsState()
        val dashboardState = screenViewModel.dashboardState.collectAsState()

        val navigator = LocalNavigator.currentOrThrow
        
//        LaunchedEffect(key1 = screenViewModel.sellerId.value, key2 = screenViewModel.token.value) {
//            if (screenViewModel.sellerId.value != null && screenViewModel.token.value != null) {
//                screenViewModel.init()
//            }
//        }

        LaunchedEffect(key1 = true) {
            screenViewModel.init()
        }

        Surface(modifier = Modifier.fillMaxSize(), color = OffWhite) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.welcome),
                        style = MaterialTheme.typography.headlineLarge,
                        color = Burgundy,
                        textAlign = TextAlign.Start
                    )
                    Box(
                        modifier = Modifier
                            .shadow(elevation = 10.dp, shape = CircleShape)
                            .clip(CircleShape)
                            .background(OffWhite)
                            .clickable { navigator.push(ProfileScreen()) }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_profile),
                            contentDescription = null,
                            tint = PrimaryColorRed,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                OrdersCard(
                    orders = screenViewModel.ordersList.value,
                    isLoading = ordersState.value.isLoading,
                    onClick = { navigator.push(OrdersScreen()) }
                )
                ProductsCard(
                    products = screenViewModel.productsList.value,
                    isLoading = productsState.value.isLoading,
                    onClick = { navigator.push(ProductsScreen()) }
                )
                DashboardCard(
                    income = dashboardState.value.income,
                    ordersCount = dashboardState.value.ordersCount,
                    isLoading = dashboardState.value.isLoading,
                    onClick = {
                        navigator.push(DashboardScreen())
                    }
                )
            }
        }
    }

    @Composable
    fun OrdersCard(orders: List<OrderDTO>, isLoading: Boolean, onClick: () -> Unit) {
        SectionCard(
            title = stringResource(R.string.orders),
            description = stringResource(R.string.check_orders),
            items = orders,
            isLoading = isLoading,
            onClick = { onClick() }
        ) {
            Column {
                Text(
                    text = "${stringResource(R.string.order_num)} - ${orders[it].id}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = OffBlack
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(PrimaryColorRed)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${orders[it].price.roundToInt()} " + stringResource(R.string.dzd) ,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("ar"))
                    val date = orders[it].datetime.date.toJavaLocalDate().format(formatter)
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(PrimaryColorRed)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = date, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }

    @Composable
    fun ProductsCard(products: List<ProductDTO>, isLoading: Boolean, onClick: () -> Unit) {
        val productsMap = mapOf(
            Products.CHICKEN to stringResource(R.string.chickens),
            Products.THIGHS to stringResource(R.string.thighs),
            Products.RABBITS to stringResource(R.string.rabbits),
            Products.BREAST to stringResource(R.string.breast),
            Products.CHICKEN_MEAT to stringResource(R.string.chicken_meat),
            Products.RABBITS_MEAT to stringResource(R.string.rabbits_meat)
        )
        SectionCard(
            title = stringResource(R.string.products),
            description = stringResource(R.string.add_edit_product),
            items = products,
            isLoading = isLoading,
            onClick = { onClick() }
        ) {
            val productName = Products.valueOf(products[it].name)
            val name = productsMap.get(productName)
            Text(
                text = name ?: "",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = PrimaryColorRed
            )
        }
    }
    
    @Composable
    fun DashboardCard(income: Float, ordersCount: OrdersCount, isLoading: Boolean, onClick: () -> Unit) {
        SectionCard(
            title = stringResource(R.string.dashboard),
            description = stringResource(R.string.follow_your_business),
            isLoading = isLoading,
            items = listOf(ordersCount.completed, income, ordersCount.pending),
            onClick = { onClick() }
        ) {
            when (it) {
                0 -> DashboardItem(
                    title = stringResource(R.string.completed_orders),
                    text = "${ordersCount.completed}"
                )

                1 -> DashboardItem(
                    title = stringResource(R.string.income),
                    text = "${income.roundToInt()} " + stringResource(
                        R.string.dzd

                    )
                )

                2 -> DashboardItem(
                    title = stringResource(R.string.pending_orders),
                    text = "${ordersCount.pending}"
                )
            }
        }
    }

    @Composable
    fun DashboardItem(title: String, text: String) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = OffBlack,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = PrimaryColorRed,
                fontWeight = FontWeight.Bold
            )
        }
    }

    @Composable
    fun SectionCard(
        modifier: Modifier = Modifier,
        title: String,
        description: String,
        items: List<Any>,
        isLoading: Boolean,
        onClick: () -> Unit,
        content: @Composable (Int) -> Unit
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(BackgroundWhite)
                .padding(horizontal = 5.dp, vertical = 10.dp)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SectionCardHeader(title = title, description = description) {
                onClick()
            }
            Spacer(modifier = Modifier.height(24.dp))
            if (isLoading) {
                HorizontalBarsAnimation(color = PrimaryColorRed, size = 30f)
            } else {
                SectionCardItemsList(items = items) {
                    content(it)
                }
            }
        }
    }
    
    @Composable
    fun SectionCardHeader(title: String, description: String, onClick: () -> Unit) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = Burgundy,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = description, style = MaterialTheme.typography.bodyMedium, color = OffBlack)
                Text(
                    text = stringResource(R.string.see_all),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryColorRed,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { 
                        onClick()
                    }
                )
            }
        }
    }
    
    @Composable
    fun SectionCardItemsList(items: List<Any>, content: @Composable (Int) -> Unit) {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(items.size) { index ->  
                SectionCardItem(content = { content(index) }, item = items[index])
            }
        }
    } 
    
    @Composable
    fun SectionCardItem(content: @Composable () -> Unit, item: Any) {
        Box(
            modifier = Modifier
                .shadow(elevation = 1.5.dp, shape = RoundedCornerShape(12.dp))
                .width(125.dp)
                .height(90.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(OffWhite)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}