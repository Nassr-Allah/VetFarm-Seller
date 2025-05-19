package com.nassrallah.vetfarmseller.feature_dashboard.presentation.home_screen

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveIdUseCase
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveTokenUseCase
import com.nassrallah.vetfarmseller.feature_dashboard.domain.entity.OrdersCount
import com.nassrallah.vetfarmseller.feature_dashboard.domain.use_case.GetOrdersCountUseCase
import com.nassrallah.vetfarmseller.feature_dashboard.domain.use_case.GetSellerIncomeUseCase
import com.nassrallah.vetfarmseller.feature_inventory.data.dto.ProductDTO
import com.nassrallah.vetfarmseller.feature_inventory.domain.use_case.GetProductsUseCase
import com.nassrallah.vetfarmseller.feature_order.data.dto.OrderDTO
import com.nassrallah.vetfarmseller.feature_order.domain.use_case.GetSellerOrdersUseCase
import com.nassrallah.vetfarmseller.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getSellerOrdersUseCase: GetSellerOrdersUseCase,
    private val getProductsUseCase: GetProductsUseCase,
    private val getSellerIncomeUseCase: GetSellerIncomeUseCase,
    private val getOrdersCountUseCase: GetOrdersCountUseCase,
    private val retrieveIdUseCase: RetrieveIdUseCase,
    private val retrieveTokenUseCase: RetrieveTokenUseCase
) : ViewModel() {

    private val _ordersState = MutableStateFlow(OrdersState())
    val ordersState: StateFlow<OrdersState> = _ordersState
    val ordersList = mutableStateOf(
        listOf<OrderDTO>()
    )

    private val _productsState = MutableStateFlow(ProductsState())
    val productsState: StateFlow<ProductsState> = _productsState
    val productsList = mutableStateOf(
        listOf<ProductDTO>()
    )

    private val _dashboardState = MutableStateFlow(DashboardState())
    val dashboardState: StateFlow<DashboardState> = _dashboardState

    val sellerId = mutableStateOf<Int?>(
        null
    )
    val token = mutableStateOf<String?>(
        null
    )

    init {
        getOrders()
        getProducts()
        getOrdersCount()
        getSellerIncome()
    }

    private fun retrieveSellerId() {
        viewModelScope.launch {
            retrieveIdUseCase().collect {
                sellerId.value = it
            }
        }
    }

    private fun retrieveToken() {
        viewModelScope.launch {
            retrieveTokenUseCase().collect {
                token.value = it
            }
        }
    }

    fun init() {
        getOrders()
        getProducts()
        getOrdersCount()
        getSellerIncome()
    }

    private fun getOrders() {
        viewModelScope.launch {
            getSellerOrdersUseCase().onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        _ordersState.value = _ordersState.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _ordersState.value = _ordersState.value.copy(
                            isLoading = false,
                            orders = result.data ?: emptyList()
                        )
                        ordersList.value = result.data ?: emptyList()
                    }
                    is Resource.Error -> {
                        _ordersState.value = _ordersState.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getProducts() {
        viewModelScope.launch {
            getProductsUseCase().onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        _productsState.value = _productsState.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _productsState.value = _productsState.value.copy(
                            isLoading = false,
                            products = result.data ?: emptyList()
                        )
                        productsList.value = result.data ?: emptyList()
                    }
                    is Resource.Error -> {
                        _productsState.value = _productsState.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getSellerIncome() {
        viewModelScope.launch {
            getSellerIncomeUseCase().onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        _dashboardState.value = _dashboardState.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _dashboardState.value = _dashboardState.value.copy(
                            isLoading = false,
                            income = result.data ?: 0f
                        )
                    }
                    is Resource.Error -> {
                        _dashboardState.value = _dashboardState.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getOrdersCount() {
        viewModelScope.launch {
            getOrdersCountUseCase().onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        _dashboardState.value = _dashboardState.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _dashboardState.value = _dashboardState.value.copy(
                            isLoading = false,
                            ordersCount = result.data ?: OrdersCount()
                        )
                    }
                    is Resource.Error -> {
                        _dashboardState.value = _dashboardState.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

}