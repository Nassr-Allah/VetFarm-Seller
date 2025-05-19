package com.nassrallah.vetfarmseller.feature_inventory.presentation.product_details_screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.nassrallah.vetfarmseller.R
import com.nassrallah.vetfarmseller.feature_auth.domain.entity.Category
import com.nassrallah.vetfarmseller.feature_inventory.domain.entity.Products
import com.nassrallah.vetfarmseller.ui.components.CustomButton
import com.nassrallah.vetfarmseller.ui.components.CustomTextField
import com.nassrallah.vetfarmseller.ui.components.CustomTextFieldWithMenu
import com.nassrallah.vetfarmseller.ui.components.HorizontalBarsAnimation
import com.nassrallah.vetfarmseller.ui.components.ScreenTitle
import com.nassrallah.vetfarmseller.ui.theme.BackgroundWhite
import com.nassrallah.vetfarmseller.ui.theme.Gray
import com.nassrallah.vetfarmseller.ui.theme.PrimaryColorRed

data class ProductDetailsScreen(private val productId: Int? = null) : Screen {

    @Composable
    override fun Content() {

        val screenViewModel = getViewModel<ProductDetailsViewModel>()
        val productDetailsScreenState = screenViewModel.productDetailsScreenState.collectAsState()
        val productFormDataState = screenViewModel.productFormDataState.collectAsState()
        val context = LocalContext.current.applicationContext
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(key1 = true, key2 = productDetailsScreenState.value.token) {
            if (productId != null && productDetailsScreenState.value.token != null) {
                screenViewModel.getProductDetails(productId)
            }
        }

        LaunchedEffect(key1 = productDetailsScreenState.value.response) {
            if (productDetailsScreenState.value.response != null && !productDetailsScreenState.value.isLoading) {
                Toast.makeText(context, context.resources.getString(R.string.product_added), Toast.LENGTH_SHORT).show()
                navigator.pop()
            }
        }

        LaunchedEffect(key1 = productDetailsScreenState.value.isRequestAdded) {
            if (!productDetailsScreenState.value.isLoading) {
                if (productDetailsScreenState.value.isRequestAdded) {
                    Toast.makeText(context, context.resources.getText(R.string.vet_requested), Toast.LENGTH_SHORT).show()
                }
                if (!productDetailsScreenState.value.isRequestAdded && productDetailsScreenState.value.error != null) {
                    Toast.makeText(context, context.resources.getText(R.string.error), Toast.LENGTH_SHORT).show()
                }
            }
        }

        Surface(modifier = Modifier.fillMaxSize(), color = BackgroundWhite) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                ScreenTitle(title = stringResource(R.string.product_info)) {
                    navigator.pop()
                }
                Column {
                    ProductDetailsForm(
                        data = productFormDataState.value,
                        category = productDetailsScreenState.value.category ?: return@Column,
                        viewModel = screenViewModel
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        modifier = Modifier.clickable {
                            if (productDetailsScreenState.value.isRequestAdded) {
                                Toast.makeText(context, context.resources.getText(R.string.vet_requested), Toast.LENGTH_SHORT).show()
                                return@clickable
                            }
                            screenViewModel.sendVetRequest()
                        },
                        text = stringResource(R.string.request_vet),
                        style = MaterialTheme.typography.titleMedium,
                        color = PrimaryColorRed,
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                if (productDetailsScreenState.value.isLoading) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        HorizontalBarsAnimation(color = PrimaryColorRed, size = 30f)
                    }
                } else {
                    CustomButton(text = stringResource(R.string.save)) {
                        screenViewModel.insertProduct()
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }

    @Composable
    fun ProductDetailsForm(
        modifier: Modifier = Modifier,
        data: ProductFormDataState,
        category: Category,
        viewModel: ProductDetailsViewModel
    ) {
        val price = if (data.price.isNotEmpty()) data.price.toInt() else data.price
        val products = if (category == Category.ABATTOIRS) {
            mapOf(
                Products.CHICKEN_MEAT to stringResource(R.string.chicken_meat),
                Products.BREAST to stringResource(R.string.breast),
                Products.THIGHS to stringResource(R.string.thighs),
                Products.RABBITS_MEAT to stringResource(R.string.rabbits_meat)
            )
        } else {
            mapOf(
                Products.CHICKEN to stringResource(R.string.chickens),
                Products.RABBITS to stringResource(R.string.rabbits)
            )
        }
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.add_edit_product),
                color = Gray,
                style = MaterialTheme.typography.titleMedium
            )
            CustomTextFieldWithMenu(
                label = stringResource(R.string.product_name),
                options = products.values.toList(),
                onOptionSelected = {
                    viewModel.updateProductName(products.keys.toList().get(it).name)
                }
            )
            CustomTextField(
                value = data.quantity,
                label = stringResource(R.string.quantity_available),
                prefix = {
                    Text(text = stringResource(R.string.kg), color = PrimaryColorRed)
                },
                onValueChange = {
                    viewModel.updateProductQuantity(it)
                })
            CustomTextField(
                value = "$price",
                label = stringResource(R.string.price),
                prefix = {
                    Text(text = stringResource(R.string.dzd))
                },
                onValueChange = {
                    viewModel.updateProductPrice(it)
                })
        }
    }

}