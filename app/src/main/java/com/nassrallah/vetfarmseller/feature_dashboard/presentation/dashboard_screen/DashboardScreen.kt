package com.nassrallah.vetfarmseller.feature_dashboard.presentation.dashboard_screen

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.barchart.models.BarStyle
import co.yml.charts.ui.barchart.models.SelectionHighlightData
import com.nassrallah.vetfarmseller.R
import com.nassrallah.vetfarmseller.feature_dashboard.data.dto.SalesInfoDto
import com.nassrallah.vetfarmseller.feature_dashboard.domain.entity.InfoItemData
import com.nassrallah.vetfarmseller.feature_inventory.domain.entity.Products
import com.nassrallah.vetfarmseller.ui.components.CustomTextFieldWithMenu
import com.nassrallah.vetfarmseller.ui.components.ScreenTitle
import com.nassrallah.vetfarmseller.ui.theme.BackgroundWhite
import com.nassrallah.vetfarmseller.ui.theme.Burgundy
import com.nassrallah.vetfarmseller.ui.theme.GradientRed
import com.nassrallah.vetfarmseller.ui.theme.Gray
import com.nassrallah.vetfarmseller.ui.theme.OffBlack
import com.nassrallah.vetfarmseller.ui.theme.OffWhite
import com.nassrallah.vetfarmseller.ui.theme.PrimaryColorRed
import com.nassrallah.vetfarmseller.utils.LanguageHelper
import kotlinx.datetime.Month
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale

class DashboardScreen : Screen {

    @Composable
    override fun Content() {

        val screenViewModel = getViewModel<DashboardViewModel>()
        val state = screenViewModel.state.collectAsState()
        val graphFilterData = screenViewModel.graphFilterData.collectAsState()

        val context = LocalContext.current.applicationContext

        val scrollState = rememberScrollState()
        val navigator = LocalNavigator.currentOrThrow

        var salesSectionData by remember {
            mutableStateOf(listOf<InfoItemData>())
        }
        var incomeSectionData by remember {
            mutableStateOf(listOf<InfoItemData>())
        }
        var quantitySectionData by remember {
            mutableStateOf(listOf<InfoItemData>())
        }

        val productsMap = mapOf(
            Products.CHICKEN to stringResource(R.string.chickens),
            Products.THIGHS to stringResource(R.string.thighs),
            Products.RABBITS to stringResource(R.string.rabbits),
            Products.BREAST to stringResource(R.string.breast),
            Products.RABBITS_MEAT to stringResource(R.string.rabbits_meat),
            Products.CHICKEN_MEAT to stringResource(R.string.chicken_meat)
        )

        LaunchedEffect(key1 = state.value) {
            quantitySectionData = screenViewModel.getQuantitySectionData(context)
            incomeSectionData = screenViewModel.getIncomeSectionData(context)
            salesSectionData = screenViewModel.getSalesSectionData(context)
            val productName = Products.valueOf(salesSectionData[2].info)
            val name = productsMap.get(productName)
            salesSectionData[2].info = name ?: ""
        }

        LaunchedEffect(key1 = graphFilterData.value, key2 = true) {
            screenViewModel.getSalesStats()
        }

        Surface(modifier = Modifier.fillMaxSize(), color = BackgroundWhite) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                ScreenTitle(title = stringResource(R.string.dashboard)) {
                    navigator.pop()
                }
                DashboardSection(title = stringResource(R.string.sales), items = salesSectionData)
                DashboardSection(title = stringResource(R.string.income), items = incomeSectionData)
                DashboardSection(title = stringResource(R.string.quantity_sold), items = quantitySectionData)
                if (screenViewModel.salesStats.value.isNotEmpty()) {
                    GraphSection(
                        salesStats = screenViewModel.salesStats.value,
                        onYearSelected = { screenViewModel.updateFilterYear(it) },
                        onHalfSelected = { screenViewModel.updateFilterFromTo(it) }
                    )
                }
            }
        }
    }

    @Composable
    fun GraphSection(salesStats: List<SalesInfoDto>, onYearSelected: (Int) -> Unit, onHalfSelected: (Int) -> Unit) {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val years = (2024..currentYear).toList()
        val halves = listOf(
            stringResource(R.string.half_one),
            stringResource(R.string.half_two)
        )
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomTextFieldWithMenu(
                    modifier = Modifier.fillMaxWidth(0.4f),
                    label = stringResource(R.string.year),
                    options = years.map { it.toString() },
                    onOptionSelected = { onYearSelected(years[it]) }
                )
                CustomTextFieldWithMenu(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    label = stringResource(R.string.half),
                    options = halves,
                    onOptionSelected = { onHalfSelected(it) }
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            SalesGraph(stats = salesStats)
        }
    }

    @Composable
    fun DashboardSection(title: String, items: List<InfoItemData>) {
        Column {
            Text(text = title, style = MaterialTheme.typography.titleLarge, color = Burgundy)
            Spacer(modifier = Modifier.height(8.dp))
            InfoList(list = items)
        }
    }

    @Composable
    fun InfoList(list: List<InfoItemData>) {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(list) { item ->  
                InfoItem(infoItemData = item)
            }
        }
    }

    @Composable
    fun InfoItem(infoItemData: InfoItemData) {
        Box(
            modifier = Modifier
                .width(130.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(OffWhite)
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.Start) {
                    Text(text = stringResource(infoItemData.titleResId), style = MaterialTheme.typography.bodySmall, color = Gray, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = infoItemData.info, style = MaterialTheme.typography.bodyLarge, color = OffBlack, fontWeight = FontWeight.Bold)
                }
                Icon(painter = painterResource(infoItemData.iconResId), contentDescription = null, modifier = Modifier.size(24.dp), tint = PrimaryColorRed)
            }
        }
    }

    @Composable
    fun SalesGraph(stats: List<SalesInfoDto>) {
        val dataList = mutableListOf<BarData>()
        var highestValue = 0
        val language = LanguageHelper.getUserLanguage(LocalContext.current.applicationContext)
        stats.forEachIndexed { index, salesInfo ->
            val bar = BarData(
                point = Point(x = (index + 1).toFloat(), y = salesInfo.ordersNumber.toFloat(), description = salesInfo.ordersNumber.toString()),
                gradientColorList = GradientRed.asReversed(),
                color = PrimaryColorRed,
                label = salesInfo.ordersNumber.toString(),
                description = salesInfo.ordersNumber.toString()
            )
            dataList.add(bar)
            if (salesInfo.ordersNumber > highestValue) highestValue = salesInfo.ordersNumber
        }
        val xAxisData = AxisData.Builder()
            .steps(dataList.size - 1)
            .axisLabelAngle(25f)
            .bottomPadding(40.dp)
            .backgroundColor(OffWhite)
            .startDrawPadding(30.dp)
            .labelData { index ->
                val month = stats[index].month
                val label = Month.of(month).getDisplayName(TextStyle.SHORT, Locale(language))
                label
            }
            .build()
        val yAxisData = AxisData.Builder()
            .steps(3)
            .startPadding(10.dp)
            .labelData { index ->
                if (highestValue < 3) return@labelData index.toString()
                ((highestValue * index) / 3).toString()
            }
            .backgroundColor(OffWhite)
            .build()
        val barChartData = BarChartData(
            backgroundColor = Color.Transparent,
            paddingEnd = 0.dp,
            tapPadding = 0.dp,
            chartData = dataList,
            xAxisData = xAxisData,
            yAxisData = yAxisData,
            barStyle = BarStyle(
                barWidth = 25.dp,
                paddingBetweenBars = 20.dp,
                isGradientEnabled = true,
                selectionHighlightData = SelectionHighlightData(popUpLabel = { _, _ -> " " })
            )
        )
        BarChart(
            modifier = Modifier
                .height(300.dp)
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .background(OffWhite),
            barChartData = barChartData
        )
    }

}