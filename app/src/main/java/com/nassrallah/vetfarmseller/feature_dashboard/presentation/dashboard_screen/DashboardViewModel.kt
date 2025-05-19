package com.nassrallah.vetfarmseller.feature_dashboard.presentation.dashboard_screen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nassrallah.vetfarmseller.R
import com.nassrallah.vetfarmseller.feature_dashboard.data.dto.SalesInfoDto
import com.nassrallah.vetfarmseller.feature_dashboard.domain.entity.InfoItemData
import com.nassrallah.vetfarmseller.feature_dashboard.domain.use_case.GetOrdersCountUseCase
import com.nassrallah.vetfarmseller.feature_dashboard.domain.use_case.GetSalesStatsUseCase
import com.nassrallah.vetfarmseller.feature_dashboard.domain.use_case.GetSellerIncomeUseCase
import com.nassrallah.vetfarmseller.feature_inventory.data.dto.ProductDTO
import com.nassrallah.vetfarmseller.feature_inventory.domain.entity.Products
import com.nassrallah.vetfarmseller.feature_inventory.domain.use_case.GetProductsUseCase
import com.nassrallah.vetfarmseller.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getSalesStatsUseCase: GetSalesStatsUseCase,
    private val getProductsUseCase: GetProductsUseCase,
    private val getSellerIncomeUseCase: GetSellerIncomeUseCase,
    private val getOrdersCountUseCase: GetOrdersCountUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardScreenState())
    val state: StateFlow<DashboardScreenState> = _state

    private val _graphFilterData = MutableStateFlow(DashboardGraphFilterData())
    val graphFilterData: StateFlow<DashboardGraphFilterData> = _graphFilterData

    val productsList = mutableStateOf(
        listOf<ProductDTO>()
    )

    val salesStats = mutableStateOf(
        listOf<SalesInfoDto>()
    )

    init {
        getCurrentHalf()
        getProducts()
        getIncome()
        getOrdersCount()
        getSalesStats()
    }

    private fun getCurrentHalf() {
        viewModelScope.launch {
            val currentMonth = LocalDate.now().monthValue
            if (currentMonth in 1 .. 6) {
                _graphFilterData.value = _graphFilterData.value.copy(
                    from = 1,
                    to = 6
                )
            } else {
                _graphFilterData.value = _graphFilterData.value.copy(
                    from = 7,
                    to = 12
                )
            }
        }
    }

    fun updateFilterYear(value: Int) {
        _graphFilterData.value = _graphFilterData.value.copy(
            year = value
        )
    }

    fun updateFilterFromTo(selectedHalf: Int) {
        if (selectedHalf == 0) {
            _graphFilterData.value = _graphFilterData.value.copy(
                from = 1,
                to = 6
            )
        } else {
            _graphFilterData.value = _graphFilterData.value.copy(
                from = 7,
                to = 12
            )
        }
    }

    private fun getProducts() {
        viewModelScope.launch {
            getProductsUseCase().onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            products = result.data ?: emptyList()
                        )
                        productsList.value = result.data ?: emptyList()
                        getTopSeller(result.data ?: return@onEach)
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getIncome() {
        viewModelScope.launch {
            getSellerIncomeUseCase().onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            income = result.data ?: 0f
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getSalesStats() {
        viewModelScope.launch {
            val year = _graphFilterData.value.year
            val from = _graphFilterData.value.from
            val to = _graphFilterData.value.to
            getSalesStatsUseCase(from, to, year).onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        Log.d("DashboardVM", "Success: ${result.data}")
                        _state.value = _state.value.copy(
                            isLoading = false,
                            salesStats = result.data ?: emptyList()
                        )
                        salesStats.value = result.data ?: emptyList()
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
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
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            ordersCount = result.data?.completed ?: 0
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getTopSeller(products: List<ProductDTO>) {
        val topSeller = products.maxBy { it.income }.name
        _state.value = _state.value.copy(
            topSeller = topSeller
        )
    }

    fun getSalesSectionData(context: Context): List<InfoItemData> {
        val productName = Products.valueOf(_state.value.topSeller.ifEmpty { "CHICKEN" })
        val result = listOf(
            InfoItemData(
                titleResId = R.string.income,
                info = _state.value.income.roundToInt().toString() + " " + context.resources.getString(R.string.dzd)
            ),
            InfoItemData(
                titleResId = R.string.orders_total,
                info = _state.value.ordersCount.toString(),
                iconResId = R.drawable.ic_bars
            ),
            InfoItemData(
                titleResId = R.string.top_seller,
                info = productName.name,
                iconResId = R.drawable.ic_bars
            )
        )
        return result
    }

    fun getIncomeSectionData(context: Context): List<InfoItemData> {
        val result = mutableListOf<InfoItemData>()
        val productsMap = mapOf(
            Products.CHICKEN to R.string.chickens,
            Products.THIGHS to R.string.thighs,
            Products.RABBITS to R.string.rabbits,
            Products.BREAST to R.string.breast,
        )
        productsList.value.forEach { product ->
            val productName = Products.valueOf(product.name)
            val nameResId = productsMap.get(productName)
            val infoItem = InfoItemData(
                titleResId = nameResId ?: return@forEach,
                info = product.income.roundToInt().toString() + " " + context.resources.getString(R.string.dzd)
            )
            result.add(infoItem)
        }
        return result
    }

    fun getQuantitySectionData(context: Context): List<InfoItemData> {
        val result = mutableListOf<InfoItemData>()
        val productsMap = mapOf(
            Products.CHICKEN to R.string.chickens,
            Products.THIGHS to R.string.thighs,
            Products.RABBITS to R.string.rabbits,
            Products.BREAST to R.string.breast,
        )
        productsList.value.forEach { product ->
            val productName = Products.valueOf(product.name)
            val nameResId = productsMap.get(productName)
            val infoItem = InfoItemData(
                titleResId = nameResId ?: return@forEach,
                info = product.quantitySold.toString() + " " + context.resources.getString(R.string.kg)
            )
            result.add(infoItem)
        }
        return result
    }

}