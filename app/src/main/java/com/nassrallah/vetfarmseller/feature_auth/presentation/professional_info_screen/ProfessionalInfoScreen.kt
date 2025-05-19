package com.nassrallah.vetfarmseller.feature_auth.presentation.professional_info_screen

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.nassrallah.vetfarmseller.R
import com.nassrallah.vetfarmseller.feature_auth.data.dto.SellerDTO
import com.nassrallah.vetfarmseller.feature_auth.data.dto.UserDTO
import com.nassrallah.vetfarmseller.feature_auth.domain.entity.Category
import com.nassrallah.vetfarmseller.feature_auth.presentation.otp_screen.OtpScreen
import com.nassrallah.vetfarmseller.ui.components.CustomButton
import com.nassrallah.vetfarmseller.ui.components.CustomTextField
import com.nassrallah.vetfarmseller.ui.components.CustomTextFieldWithMenu
import com.nassrallah.vetfarmseller.ui.components.ScreenTitle
import com.nassrallah.vetfarmseller.ui.theme.BackgroundWhite
import com.nassrallah.vetfarmseller.ui.theme.Gray

data class ProfessionalInfoScreen(private val user: UserDTO) : Screen {

    @Composable
    override fun Content() {

        val screenViewModel = getViewModel<ProfessionalInfoViewModel>()
        val state = screenViewModel.state.collectAsState()
        val form = screenViewModel.form.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(key1 = state.value.selectedWilaya) {
            screenViewModel.getCommunes()
        }

        Surface(modifier = Modifier.fillMaxSize(), color = BackgroundWhite) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    ScreenTitle(title = stringResource(R.string.business_info)) {
                        navigator.pop()
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    ProfessionalInfoSection(formState = form.value, viewModel = screenViewModel)
                }
                CustomButton(text = stringResource(R.string.next), icon = R.drawable.ic_arrow_right) {
                    if (screenViewModel.validateInput()) {
                        val seller = SellerDTO(
                            user = user,
                            businessName = form.value.businessName,
                            wilaya = form.value.wilaya,
                            commune = form.value.commune,
                            address = form.value.address,
                            category = state.value.selectedCategory
                        )
                        navigator.push(OtpScreen(seller))
                    }
                }
            }
        }
    }

    @Composable
    fun ProfessionalInfoSection(formState: ProfessionalInfoFormState, viewModel: ProfessionalInfoViewModel) {
        val wilayasNames = viewModel.wilayas.value.map { it.ar_name }
        val communesNames = viewModel.communes.value.map { it.arName }
        val categories = mapOf(
            Category.ABATTOIRS to stringResource(R.string.abattoirs),
            Category.BREEDERS to stringResource(R.string.breeders)
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.insert_info),
                style = MaterialTheme.typography.bodyLarge,
                color = Gray
            )
            CustomTextField(
                value = formState.businessName,
                onValueChange = { viewModel.updateBusinessName(it) },
                label = stringResource(R.string.business_name)
            )
            CustomTextFieldWithMenu(
                label = stringResource(R.string.wilaya),
                options = wilayasNames,
                onOptionSelected = { viewModel.updateWilaya(it) })
            CustomTextFieldWithMenu(
                label = stringResource(R.string.commune),
                options = communesNames,
                onOptionSelected = { viewModel.updateCommune(it) })
            CustomTextField(
                value = formState.address,
                onValueChange = { viewModel.updateAddress(it) },
                label = stringResource(R.string.address)
            )
            CustomTextFieldWithMenu(
                label = stringResource(R.string.category),
                options = categories.values.toList(),
                onOptionSelected = {
                    viewModel.updateCategory(categories.keys.toList().get(it))
                }
            )

        }
    }
}