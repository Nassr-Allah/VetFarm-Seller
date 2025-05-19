package com.nassrallah.vetfarmseller.feature_inventory.presentation.product_details_screen

import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nassrallah.vetfarmseller.feature_auth.domain.entity.Category
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveCategoryUseCase
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveIdUseCase
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveTokenUseCase
import com.nassrallah.vetfarmseller.feature_inventory.data.dto.ProductDTO
import com.nassrallah.vetfarmseller.feature_inventory.domain.repository.InventoryRepository
import com.nassrallah.vetfarmseller.feature_inventory.domain.use_case.AddRequestUseCase
import com.nassrallah.vetfarmseller.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val inventoryRepository: InventoryRepository,
    private val retrieveIdUseCase: RetrieveIdUseCase,
    private val retrieveTokenUseCase: RetrieveTokenUseCase,
    private val retrieveCategoryUseCase: RetrieveCategoryUseCase,
    private val addRequestUseCase: AddRequestUseCase
) : ViewModel() {

    private val _productDetailsScreenState = MutableStateFlow(ProductDetailsScreenState())
    val productDetailsScreenState: StateFlow<ProductDetailsScreenState> = _productDetailsScreenState

    private val _productFormDataState = MutableStateFlow(ProductFormDataState())
    val productFormDataState: StateFlow<ProductFormDataState> = _productFormDataState

    init {
        retrieveSellerId()
        retrieveToken()
        retrieveCategory()
    }

    fun updateProductName(value: String) {
        _productFormDataState.value = _productFormDataState.value.copy(name = value)
    }

    fun updateProductQuantity(value: String) {
        if (value.isDigitsOnly()) {
            _productFormDataState.value = _productFormDataState.value.copy(quantity = value)
        }
    }

    fun updateProductPrice(value: String) {
        if (value.isDigitsOnly()) {
            _productFormDataState.value = _productFormDataState.value.copy(price = value)
        }
    }

    fun getProductDetails(productId: Int) {
        viewModelScope.launch {
            val token = _productDetailsScreenState.value.token ?: return@launch
            inventoryRepository.getProductDetails(productId, token).onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        Log.d("ProductDetailsViewModel", "Loading...")
                        _productDetailsScreenState.value = _productDetailsScreenState.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        Log.d("ProductDetailsViewModel", "Success: ${result.data}")
                        _productDetailsScreenState.value = _productDetailsScreenState.value.copy(
                            isLoading = false,
                            data = result.data
                        )
                        refreshProductDetails(result.data ?: return@onEach)
                    }
                    is Resource.Error -> {
                        Log.d("ProductDetailsViewModel", "Error: ${result.message}")
                        _productDetailsScreenState.value = _productDetailsScreenState.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun insertProduct() {
        viewModelScope.launch {
            val sellerId = _productDetailsScreenState.value.sellerId ?: return@launch
            val token = _productDetailsScreenState.value.token ?: return@launch
            val product = ProductDTO(
                name = _productFormDataState.value.name,
                price = _productFormDataState.value.price.toFloat(),
                quantity = _productFormDataState.value.quantity.toInt(),
                sellerID = sellerId
            )
            inventoryRepository.insertProduct(product, token).onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        Log.d("ProductDetailsViewModel", "Loading...")
                        _productDetailsScreenState.value = _productDetailsScreenState.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        Log.d("ProductDetailsViewModel", "Success: ${result.data}")
                        _productDetailsScreenState.value = _productDetailsScreenState.value.copy(
                            isLoading = false,
                            response = result.data
                        )
                    }
                    is Resource.Error -> {
                        Log.d("ProductDetailsViewModel", "Error: ${result.message}")
                        _productDetailsScreenState.value = _productDetailsScreenState.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun sendVetRequest() {
        viewModelScope.launch {
            addRequestUseCase().onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        Log.d("ProductDetailsViewModel", "Loading...")
                        _productDetailsScreenState.value = _productDetailsScreenState.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        Log.d("ProductDetailsViewModel", "Success: ${result.data}")
                        _productDetailsScreenState.value = _productDetailsScreenState.value.copy(
                            isLoading = false,
                            isRequestAdded = true
                        )
                    }
                    is Resource.Error -> {
                        Log.d("ProductDetailsViewModel", "Error: ${result.message}")
                        _productDetailsScreenState.value = _productDetailsScreenState.value.copy(
                            isLoading = false,
                            error = result.message,
                            isRequestAdded = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun refreshProductDetails(product: ProductDTO) {
        _productFormDataState.value = _productFormDataState.value.copy(
            name = product.name,
            price = product.price.toInt().toString(),
            quantity = product.quantity.toString()
        )
    }

    private fun retrieveSellerId() {
        viewModelScope.launch {
            retrieveIdUseCase().collect {
                _productDetailsScreenState.value = _productDetailsScreenState.value.copy(
                    sellerId = it
                )
            }
        }
    }

    private fun retrieveToken() {
        viewModelScope.launch {
            retrieveTokenUseCase().collect {
                _productDetailsScreenState.value = _productDetailsScreenState.value.copy(
                    token = it
                )
            }
        }
    }

    private fun retrieveCategory() {
        viewModelScope.launch {
            retrieveCategoryUseCase().collect {
                Log.d("ProductDetailsVM", "category: $it")
                if (it.isNotEmpty()) {
                    _productDetailsScreenState.value = _productDetailsScreenState.value.copy(
                        category = Category.valueOf(it)
                    )
                }
            }
        }
    }

}