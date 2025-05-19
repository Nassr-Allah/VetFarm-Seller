package com.nassrallah.vetfarmseller.feature_auth.presentation.otp_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.nassrallah.vetfarmseller.R
import com.nassrallah.vetfarmseller.feature_auth.data.dto.ClientDTO
import com.nassrallah.vetfarmseller.feature_auth.data.dto.SellerDTO
import com.nassrallah.vetfarmseller.feature_auth.presentation.password_screen.PasswordScreen
import com.nassrallah.vetfarmseller.ui.components.CustomButton
import com.nassrallah.vetfarmseller.ui.components.CustomTextField
import com.nassrallah.vetfarmseller.ui.components.ScreenTitle
import com.nassrallah.vetfarmseller.ui.components.SmsDigitField
import com.nassrallah.vetfarmseller.ui.theme.BackgroundWhite
import com.nassrallah.vetfarmseller.ui.theme.Gray

data class OtpScreen(private val seller: SellerDTO) : Screen {

    @Composable
    override fun Content() {

        val screenViewModel = getViewModel<OtpScreenViewModel>()
        val state = screenViewModel.state.collectAsState()
        val smsState = screenViewModel.smsState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        Surface(modifier = Modifier.fillMaxSize(), color = BackgroundWhite) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    ScreenTitle(title = stringResource(R.string.phone_num)) {
                        navigator.pop()
                    }
                    PhoneNumberSection(state = state.value, viewModel = screenViewModel)
                    SmsCodeSection(state = state.value, smsState = smsState.value, viewModel = screenViewModel)
                }
                CustomButton(modifier = Modifier.padding(bottom = 50.dp), text = stringResource(R.string.check_next)) {
                    val sellerCopy = seller.copy(
                        user = seller.user.copy(
                            phoneNumber = state.value.phoneNumber
                        )
                    )
                    navigator.push(PasswordScreen(sellerCopy))
                }
            }
        }
    }

    @Composable
    fun PhoneNumberSection(state: OtpScreenState, viewModel: OtpScreenViewModel) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.confirm_phone),
                style = MaterialTheme.typography.bodyLarge,
                color = Gray,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(
                value = state.phoneNumber,
                onValueChange = { viewModel.updatePhoneNumber(it) },
                label = stringResource(R.string.phone_num),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            )
        }
    }

    @Composable
    fun SmsCodeSection(state: OtpScreenState, smsState: OtpScreenSmsState, viewModel: OtpScreenViewModel) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.confirm_phone),
                style = MaterialTheme.typography.bodyLarge,
                color = Gray,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                repeat(6) { index ->
                    SmsDigitField(
                        modifier = Modifier.weight(1f),
                        value = smsState.valueOf(index),
                        onValueChange = {
                            if (it.length <= 1) {
                                viewModel.updateDigit(index, it)
                            }
                        }
                    )
                }
            }
        }
    }
}