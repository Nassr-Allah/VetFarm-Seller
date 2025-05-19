package com.nassrallah.vetfarmseller.feature_order.presentation.order_details_screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.nassrallah.vetfarmseller.R
import com.nassrallah.vetfarmseller.feature_inventory.domain.entity.Products
import com.nassrallah.vetfarmseller.feature_order.data.dto.OrderDTO
import com.nassrallah.vetfarmseller.feature_order.data.dto.OrderProductDetailsDTO
import com.nassrallah.vetfarmseller.feature_order.domain.entity.ClientOrder
import com.nassrallah.vetfarmseller.feature_order.domain.entity.OrderStatus
import com.nassrallah.vetfarmseller.feature_order.presentation.orders_screen.OrdersScreenViewModel
import com.nassrallah.vetfarmseller.ui.components.DetailRow
import com.nassrallah.vetfarmseller.ui.components.ExpandableDetailsCard
import com.nassrallah.vetfarmseller.ui.components.ItemCard
import com.nassrallah.vetfarmseller.ui.components.ScreenTitle
import com.nassrallah.vetfarmseller.ui.theme.BackgroundWhite
import com.nassrallah.vetfarmseller.ui.theme.GradientRed
import com.nassrallah.vetfarmseller.ui.theme.Gray
import com.nassrallah.vetfarmseller.ui.theme.OffBlack
import com.nassrallah.vetfarmseller.ui.theme.OffWhite
import com.nassrallah.vetfarmseller.ui.theme.PrimaryColorRed
import com.nassrallah.vetfarmseller.ui.theme.VetFarmSellerTheme
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime
import kotlin.math.roundToInt

data class OrderDetailsScreen(private val orderId: Int) : Screen {

    @Composable
    override fun Content() {

        val screenViewModel = getViewModel<OrderDetailsViewModel>()
        val orderDetailsScreenState = screenViewModel.orderDetailsScreenState.collectAsState()
        val orderCardsExpandableState = screenViewModel.orderCardsExpandableState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(key1 = orderDetailsScreenState.value.token) {
            if (orderDetailsScreenState.value.token != null) {
                screenViewModel.getOrderDetails(orderId)
            }
        }

        Surface(modifier = Modifier.fillMaxSize(), color = BackgroundWhite) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ScreenTitle(title = stringResource(R.string.order_details)) {
                    navigator.pop()
                }
                Spacer(modifier = Modifier.height(35.dp))
                DetailsSection(
                    order = orderDetailsScreenState.value.data,
                    expandableState = orderCardsExpandableState.value,
                    viewModel = screenViewModel
                )
                Spacer(modifier = Modifier.height(12.dp))
                StatusCard(status = orderDetailsScreenState.value.data?.status ?: OrderStatus.PENDING)
            }
        }

    }

    @Composable
    fun DetailsSection(
        modifier: Modifier = Modifier,
        order: ClientOrder?,
        expandableState: OrderCardsExpandableState,
        viewModel: OrderDetailsViewModel
    ) {

        val clientName = "${order?.clientFirstName} ${order?.clientLastName}"

        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ExpandableDetailsCard(
                title = stringResource(R.string.order_details),
                isExpanded = expandableState.detailsCardExpanded,
                onClick = { viewModel.toggleExpandableCardStatusAt(0) }
            ) {
                ItemCard(
                    onClick = { viewModel.toggleExpandableCardStatusAt(0) },
                    topSection = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.order_details),
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
                    },
                    midSection = {
                        Column {
                            DetailRow(startText = stringResource(R.string.order_num), endText = "${order?.id}")
                            DetailRow(startText = stringResource(R.string.client), endText = clientName)
                            DetailRow(startText = stringResource(R.string.address), endText = "${order?.address}")
                        }
                    }
                )
            }
            ExpandableDetailsCard(
                title = stringResource(R.string.order_items),
                isExpanded = expandableState.itemsCardExpanded,
                onClick = { viewModel.toggleExpandableCardStatusAt(1) }
            ) {
                ItemCard(
                    onClick = { viewModel.toggleExpandableCardStatusAt(1) },
                    topSection = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.order_items),
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
                    },
                    midSection = {
                        Column {

                            val products = mapOf(
                                Products.CHICKEN to stringResource(R.string.chickens),
                                Products.BREAST to stringResource(R.string.breast),
                                Products.THIGHS to stringResource(R.string.thighs),
                                Products.RABBITS to stringResource(R.string.rabbits),
                                Products.CHICKEN_MEAT to stringResource(R.string.chicken_meat),
                                Products.RABBITS_MEAT to stringResource(R.string.rabbits_meat)
                            )

                            repeat(order?.products?.size ?: return@Column) {
                                val orderProduct = order.products[it]
                                val productName = Products.valueOf(orderProduct.product.name)
                                val name = products.get(productName)
                                val quantity = orderProduct.quantityOrdered
                                val price = orderProduct.product.price * quantity
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .clip(CircleShape)
                                            .background(PrimaryColorRed)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    DetailRow(
                                        startText = "$name - $quantity " + stringResource(R.string.kg),
                                        endText = "${price.roundToInt()} " + stringResource(R.string.dzd)
                                    )
                                }
                            }
                        }
                    }
                )
            }
            ExpandableDetailsCard(
                title = stringResource(R.string.delivery_info),
                isExpanded = expandableState.transporterCardExpanded,
                onClick = { viewModel.toggleExpandableCardStatusAt(2) }
            ) {
                ItemCard(
                    onClick = { viewModel.toggleExpandableCardStatusAt(2) },
                    topSection = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.delivery_info),
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
                    },
                    midSection = {
                        Column {
                            DetailRow(startText = stringResource(R.string.delivery_price), endText = "${order?.deliveryPrice?.roundToInt()} ${stringResource(R.string.dzd)}")
                        }
                    }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(OffWhite)
                    .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.total_price),
                    color = OffBlack,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "${order?.price?.roundToInt()?.plus(order.deliveryPrice.roundToInt())} " + stringResource(R.string.dzd),
                    color = Gray,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.ExtraBold
                )
            }

        }
    }

    @Composable
    fun StatusCard(modifier: Modifier = Modifier, status: OrderStatus) {

        val animationProgress = remember {
            Animatable(0f)
        }

        val statusBarWidth = animateFloatAsState(
            when(status) {
                OrderStatus.PENDING -> 0f
                OrderStatus.PAID -> 0.75f
                OrderStatus.CONFIRMED -> 0.25f
                OrderStatus.ON_DELIVERY -> 0.375f
                OrderStatus.DELIVERED -> 0.5f
                OrderStatus.COMPLETED -> 1f
                OrderStatus.REJECTED -> 0f
                else -> 0f
            },
            animationSpec = tween(durationMillis = 2000)
        )

        LaunchedEffect(key1 = status) {
            animationProgress.animateTo(1f, tween(2000))
        }

        val statusBarColor = Brush.horizontalGradient(GradientRed)

        val dollarSignTint by animateColorAsState(
            if(statusBarWidth.value  * animationProgress.value >= 0.75f) OffWhite else OffBlack
        )
        val dollarSignBackground by animateColorAsState(
            if (animationProgress.value * statusBarWidth.value >= 0.75f) PrimaryColorRed else BackgroundWhite
        )

        val checkTint by animateColorAsState(
            if(statusBarWidth.value  * animationProgress.value >= 0.25f) OffWhite else OffBlack
        )
        val checkBackground by animateColorAsState(
            if (animationProgress.value * statusBarWidth.value >= 0.25f) PrimaryColorRed else BackgroundWhite
        )

        val truckTint by animateColorAsState(
            if(statusBarWidth.value  * animationProgress.value >= 0.5f) OffWhite else OffBlack
        )
        val truckBackground by animateColorAsState(
            if (animationProgress.value * statusBarWidth.value >= 0.5f) PrimaryColorRed else BackgroundWhite
        )

        Box(modifier = modifier.clip(RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
            ItemCard(
                topSection = {
                    Text(
                        text = stringResource(R.string.status),
                        color = OffBlack,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.ExtraBold
                    )
                },
                midSection = {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(12.dp)
                                    .clip(RoundedCornerShape(100))
                                    .background(BackgroundWhite)
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(animationProgress.value * statusBarWidth.value)
                                    .height(12.dp)
                                    .clip(RoundedCornerShape(100))
                                    .background(statusBarColor)
                                    .animateContentSize()
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                StatusIcon(
                                    painterResource = R.drawable.ic_double_check,
                                    tint = checkTint,
                                    background = checkBackground
                                )
                                StatusIcon(
                                    painterResource = R.drawable.ic_truck,
                                    tint = truckTint,
                                    background = truckBackground
                                )
                                StatusIcon(
                                    painterResource = R.drawable.ic_dollar_sign,
                                    tint = dollarSignTint,
                                    background = dollarSignBackground
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            StatusText(
                                text = stringResource(R.string.sent),
                                textPosition = 0f,
                                statusPosition = statusBarWidth.value
                            )
                            StatusText(
                                text = stringResource(R.string.confirmed),
                                textPosition = 0.25f,
                                statusPosition = statusBarWidth.value
                            )
                            StatusText(
                                text = stringResource(R.string.delivered),
                                textPosition = 0.5f,
                                statusPosition = statusBarWidth.value
                            )
                            StatusText(
                                text = stringResource(R.string.paid),
                                textPosition = 0.75f,
                                statusPosition = statusBarWidth.value
                            )
                            StatusText(
                                text = stringResource(R.string.completed),
                                textPosition = 1f,
                                statusPosition = statusBarWidth.value
                            )
                        }
                    }
                }
            )
        }
    }

    @Composable
    fun StatusIcon(painterResource: Int, tint: Color, background: Color) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(background)
                .padding(5.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(painterResource),
                contentDescription = null,
                tint = tint
            )
        }
    }

    @Composable
    fun StatusText(text: String, textPosition: Float, statusPosition: Float) {
        val textColor = if (statusPosition >= textPosition) PrimaryColorRed else Gray
        Text(text = text, color = textColor, style = MaterialTheme.typography.bodySmall)
    }

}