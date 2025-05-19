package com.nassrallah.vetfarmseller.feature_inventory.presentation.products_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nassrallah.vetfarmseller.feature_auth.domain.entity.Category
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveCategoryUseCase
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveIdUseCase
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveTokenUseCase
import com.nassrallah.vetfarmseller.feature_inventory.data.dto.ProductDTO
import com.nassrallah.vetfarmseller.feature_inventory.domain.repository.InventoryRepository
import com.nassrallah.vetfarmseller.feature_inventory.domain.use_case.GetProductsUseCase
import com.nassrallah.vetfarmseller.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsScreenViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val retrieveIdUseCase: RetrieveIdUseCase,
    private val retrieveTokenUseCase: RetrieveTokenUseCase,
    private val retrieveCategoryUseCase: RetrieveCategoryUseCase
) : ViewModel() {

    private val _productsScreenState = MutableStateFlow(ProductsScreenState())
    val productsScreenState: StateFlow<ProductsScreenState> = _productsScreenState

    val products = mutableStateOf(
        listOf<ProductDTO>()
    )

    init {
        getSellerProducts()
    }

    fun getSellerProducts() {
        viewModelScope.launch {
            getProductsUseCase().onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        Log.d("ProductsScreenViewModel", "Loading...")
                        _productsScreenState.value = _productsScreenState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        Log.d("ProductsScreenViewModel", "Success: ${result.data}")
                        _productsScreenState.value = _productsScreenState.value.copy(
                            data = result.data,
                            isLoading = false
                        )
                        products.value = result.data ?: emptyList()
                    }
                    is Resource.Error -> {
                        Log.d("ProductsScreenViewModel", "Success: ${result.message}")
                        _productsScreenState.value = _productsScreenState.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun retrieveSellerId() {
        viewModelScope.launch {
            retrieveIdUseCase().collect {
                _productsScreenState.value = _productsScreenState.value.copy(
                    sellerId = it
                )
            }
        }
    }

    private fun retrieveToken() {
        viewModelScope.launch {
            retrieveTokenUseCase().collect {
                _productsScreenState.value = _productsScreenState.value.copy(
                    token = it
                )
            }
        }
    }

}