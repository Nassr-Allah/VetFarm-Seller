package com.nassrallah.vetfarmseller.feature_profile.presentation.personal_info_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
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
import com.nassrallah.vetfarmseller.ui.components.CustomButton
import com.nassrallah.vetfarmseller.ui.components.CustomTextField
import com.nassrallah.vetfarmseller.ui.components.CustomTextFieldWithMenu
import com.nassrallah.vetfarmseller.ui.components.HorizontalBarsAnimation
import com.nassrallah.vetfarmseller.ui.components.ScreenTitle
import com.nassrallah.vetfarmseller.ui.theme.BackgroundWhite
import com.nassrallah.vetfarmseller.ui.theme.PrimaryColorRed

data class PersonalInfoScreen(private val id: Int) : Screen {

    @Composable
    override fun Content() {

        val screenViewModel = getViewModel<PersonalInfoViewModel>()
        val screenState = screenViewModel.state.collectAsState()
        val formState = screenViewModel.form.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(key1 = screenState.value.sellerId, key2 = screenState.value.token) {
            if (screenState.value.token != null && screenState.value.sellerId != null) {
                screenViewModel.getSeller()
            }
        }

        LaunchedEffect(key1 = screenState.value.selectedWilayaId) {
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
                if (screenState.value.isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        HorizontalBarsAnimation(color = PrimaryColorRed, size = 30f)
                    }
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        ScreenTitle(title = stringResource(R.string.my_info)) {
                            navigator.pop()
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        PersonalInfoForm(formState = formState.value, viewModel = screenViewModel)
                    }
                    CustomButton(
                        modifier = Modifier.padding(bottom = 75.dp),
                        text = stringResource(R.string.save)
                    ) {
                        if (screenViewModel.validateInfo()) {
                            screenViewModel.updateSeller()
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun PersonalInfoForm(formState: PersonalInfoFormState, viewModel: PersonalInfoViewModel) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CustomTextField(
                value = formState.businessName,
                onValueChange = { viewModel.updateBusinessName(it) },
                label = stringResource(R.string.business_info)
            )
            CustomTextFieldWithMenu(
                label = stringResource(R.string.wilaya),
                options = viewModel.wilayas.value.map { it.ar_name },
                onOptionSelected = { viewModel.updateWilaya(it) },
                defaultOption = formState.wilaya
            )
            CustomTextFieldWithMenu(
                label = stringResource(R.string.commune),
                options = viewModel.communes.value.map { it.arName },
                onOptionSelected = { viewModel.updateCommune(it) },
                defaultOption = formState.commune
            )
            CustomTextField(
                value = formState.address,
                onValueChange = { viewModel.updateAddress(it) },
                label = stringResource(R.string.address)
            )
        }
    }
}