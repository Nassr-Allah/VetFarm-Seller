package com.nassrallah.vetfarmseller.feature_order.presentation.order_details_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveTokenUseCase
import com.nassrallah.vetfarmseller.feature_order.domain.use_case.GetOrderDetailsUseCase
import com.nassrallah.vetfarmseller.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val orderDetailsUseCase: GetOrderDetailsUseCase,
    private val retrieveTokenUseCase: RetrieveTokenUseCase
) : ViewModel() {

    private val _orderDetailsScreenState = MutableStateFlow(OrderDetailsScreenState())
    val orderDetailsScreenState: StateFlow<OrderDetailsScreenState> = _orderDetailsScreenState

    private val _orderCardsExpandableState = MutableStateFlow(OrderCardsExpandableState())
    val orderCardsExpandableState: StateFlow<OrderCardsExpandableState> = _orderCardsExpandableState

    init {
        retrieveToken()
    }

    fun toggleExpandableCardStatusAt(index: Int) {
        when(index) {
            0 -> {
                _orderCardsExpandableState.value = _orderCardsExpandableState.value.copy(
                    detailsCardExpanded = !_orderCardsExpandableState.value.detailsCardExpanded
                )
            }
            1 -> {
                _orderCardsExpandableState.value = _orderCardsExpandableState.value.copy(
                    itemsCardExpanded = !_orderCardsExpandableState.value.itemsCardExpanded
                )
            }
            2 -> {
                _orderCardsExpandableState.value = _orderCardsExpandableState.value.copy(
                    transporterCardExpanded = !_orderCardsExpandableState.value.transporterCardExpanded
                )
            }
        }
    }

    fun getOrderDetails(orderId: Int) {
        viewModelScope.launch {
            val token = _orderDetailsScreenState.value.token ?: return@launch
            orderDetailsUseCase(orderId, token).onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        Log.d("OrderDetailsViewModel", "Loading...")
                        _orderDetailsScreenState.value = _orderDetailsScreenState.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        Log.d("OrderDetailsViewModel", "Success: ${result.data}")
                        _orderDetailsScreenState.value = _orderDetailsScreenState.value.copy(
                            isLoading = false,
                            data = result.data
                        )
                    }
                    is Resource.Error -> {
                        Log.d("OrderDetailsViewModel", "Error: ${result.message}")
                        _orderDetailsScreenState.value = _orderDetailsScreenState.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun retrieveToken() {
        viewModelScope.launch {
            retrieveTokenUseCase().collect {
                _orderDetailsScreenState.value = _orderDetailsScreenState.value.copy(
                    token = it
                )
            }
        }
    }

}