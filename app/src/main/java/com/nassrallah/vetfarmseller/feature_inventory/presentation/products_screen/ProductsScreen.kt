package com.nassrallah.vetfarmseller.feature_inventory.presentation.products_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.nassrallah.vetfarmseller.R
import com.nassrallah.vetfarmseller.feature_auth.domain.entity.Category
import com.nassrallah.vetfarmseller.feature_inventory.data.dto.ProductDTO
import com.nassrallah.vetfarmseller.feature_inventory.domain.entity.Products
import com.nassrallah.vetfarmseller.feature_inventory.presentation.product_details_screen.ProductDetailsScreen
import com.nassrallah.vetfarmseller.ui.components.DetailRow
import com.nassrallah.vetfarmseller.ui.components.HorizontalBarsAnimation
import com.nassrallah.vetfarmseller.ui.components.ItemCard
import com.nassrallah.vetfarmseller.ui.components.ScreenTitle
import com.nassrallah.vetfarmseller.ui.theme.BackgroundWhite
import com.nassrallah.vetfarmseller.ui.theme.OffBlack
import com.nassrallah.vetfarmseller.ui.theme.OffWhite
import com.nassrallah.vetfarmseller.ui.theme.PrimaryColorRed
import com.nassrallah.vetfarmseller.ui.theme.VetFarmSellerTheme
import kotlin.math.roundToInt

class ProductsScreen : Screen {

    @Composable
    override fun Content() {

        val screenViewModel = getViewModel<ProductsScreenViewModel>()
        val productsScreenState = screenViewModel.productsScreenState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

//        LaunchedEffect(key1 = productsScreenState.value.sellerId, key2 = productsScreenState.value.token) {
//            if (productsScreenState.value.sellerId != null && productsScreenState.value.token != null) {
//                screenViewModel.getSellerProducts()
//            }
//        }

        LaunchedEffect(key1 = true) {
            screenViewModel.getSellerProducts()
        }

        Surface(modifier = Modifier.fillMaxSize(), color = BackgroundWhite) {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                floatingActionButton = {
                    FloatingActionButton(
                        shape = RoundedCornerShape(16.dp),
                        containerColor = PrimaryColorRed,
                        contentColor = OffWhite,
                        onClick = {
                            navigator.push(ProductDetailsScreen())
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_fab_plus),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                containerColor = BackgroundWhite
            ) {
                it
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(30.dp)
                ) {
                    ScreenTitle(title = stringResource(R.string.products)) {
                        navigator.pop()
                    }
                    if (productsScreenState.value.isLoading) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            HorizontalBarsAnimation(color = PrimaryColorRed, size = 30f)
                        }
                    } else {
                        ProductsListSection(
                            products = screenViewModel.products.value
                        ) { id ->
                            navigator.push(ProductDetailsScreen(id))
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ProductsListSection(
        modifier: Modifier = Modifier,
        products: List<ProductDTO>,
        onClick: (Int) -> Unit
    ) {
        LazyColumn(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(products) { product ->
                ProductItem(product = product) {
                    onClick(product.id ?: return@ProductItem)
                }
            }
        }
    }

    @Composable
    fun ProductItem(product: ProductDTO, onClick: () -> Unit) {
        val products = mapOf(
            Products.CHICKEN to stringResource(R.string.chickens),
            Products.THIGHS to stringResource(R.string.thighs),
            Products.BREAST to stringResource(R.string.breast),
            Products.RABBITS to stringResource(R.string.rabbits),
            Products.CHICKEN_MEAT to stringResource(R.string.chicken_meat),
            Products.RABBITS_MEAT to stringResource(R.string.rabbits_meat)
        )

        val productName = Products.valueOf(product.name)
        val name = products.get(productName)
        ItemCard(
            topSection = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = name ?: "",
                        color = OffBlack,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        modifier = Modifier.clickable {
                            onClick()
                        },
                        text = stringResource(R.string.edit),
                        color = PrimaryColorRed,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            },
            midSection = {
                Column {
                    DetailRow(
                        startText = stringResource(R.string.quantity_available),
                        endText = "${product.quantity} " + stringResource(R.string.kg)
                    )
                    DetailRow(
                        startText = stringResource(R.string.price),
                        endText = "${product.price.roundToInt()} " + stringResource(R.string.dzd)
                    )
                }
            },
            bottomSection = { },
        )
    }
}