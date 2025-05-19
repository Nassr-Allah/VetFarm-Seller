package com.nassrallah.vetfarmseller.feature_order.presentation.orders_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveIdUseCase
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveTokenUseCase
import com.nassrallah.vetfarmseller.feature_order.domain.entity.ClientOrder
import com.nassrallah.vetfarmseller.feature_order.domain.entity.OrderStatus
import com.nassrallah.vetfarmseller.feature_order.domain.use_case.GetOrdersUseCase
import com.nassrallah.vetfarmseller.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersScreenViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase,
    private val retrieveIdUseCase: RetrieveIdUseCase,
    private val retrieveTokenUseCase: RetrieveTokenUseCase
) : ViewModel() {

    private val _ordersScreenState = MutableStateFlow(OrdersScreenState())
    val ordersScreenState: StateFlow<OrdersScreenState> = _ordersScreenState

    val ordersList = mutableStateOf(
        emptyList<ClientOrder>()
    )

    init {
        retrieveToken()
        retrieveSellerId()
    }

    fun getOrders() {
        viewModelScope.launch {
            val id = _ordersScreenState.value.sellerId ?: return@launch
            val token = _ordersScreenState.value.token ?: return@launch
            getOrdersUseCase(id, token).onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        Log.d("OrdersScreenViewModel", "Loading...")
                        _ordersScreenState.value = _ordersScreenState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        Log.d("OrdersScreenViewModel", "Success: ${result.data}")
                        _ordersScreenState.value = _ordersScreenState.value.copy(
                            isLoading = false,
                            data = result.data ?: emptyList()
                        )
                        ordersList.value = result.data ?: emptyList()
                    }
                    is Resource.Error -> {
                        Log.d("OrdersScreenViewModel", "Error: ${result.message}")
                        _ordersScreenState.value = _ordersScreenState.value.copy(
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
                _ordersScreenState.value = _ordersScreenState.value.copy(
                    sellerId = it
                )
            }
        }
    }

    private fun retrieveToken() {
        viewModelScope.launch {
            retrieveTokenUseCase().collect {
                _ordersScreenState.value = _ordersScreenState.value.copy(
                    token = it
                )
            }
        }
    }

    fun filterOrders(value: OrderStatus) {
        if (value == OrderStatus.ALL) {
            ordersList.value = _ordersScreenState.value.data
            return
        }
        ordersList.value = _ordersScreenState.value.data.filter { it.status == value }
    }

}