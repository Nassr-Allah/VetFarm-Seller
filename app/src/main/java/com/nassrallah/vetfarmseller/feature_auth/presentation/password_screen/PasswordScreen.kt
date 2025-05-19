package com.nassrallah.vetfarmseller.feature_auth.presentation.password_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import co.yml.charts.common.extensions.isNotNull
import com.nassrallah.vetfarmseller.R
import com.nassrallah.vetfarmseller.feature_auth.data.dto.ClientDTO
import com.nassrallah.vetfarmseller.feature_auth.data.dto.SellerDTO
import com.nassrallah.vetfarmseller.ui.components.CustomButton
import com.nassrallah.vetfarmseller.ui.components.CustomTextField
import com.nassrallah.vetfarmseller.ui.components.HorizontalBarsAnimation
import com.nassrallah.vetfarmseller.ui.components.ScreenTitle
import com.nassrallah.vetfarmseller.ui.theme.BackgroundWhite
import com.nassrallah.vetfarmseller.ui.theme.Gray
import com.nassrallah.vetfarmseller.ui.theme.PrimaryColorRed

data class PasswordScreen(private val seller: SellerDTO) : Screen {

    @Composable
    override fun Content() {

        val screenViewModel = getViewModel<PasswordScreenViewModel>()
        val state = screenViewModel.state.collectAsState()
        val form = screenViewModel.form.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current.applicationContext

        LaunchedEffect(key1 = state.value.isAccountCreated) {
            if (state.value.isAccountCreated) {
                Toast.makeText(context, context.getString(R.string.account_created), Toast.LENGTH_LONG).show()
                navigator.popUntilRoot()
            }
        }

        LaunchedEffect(key1 = state.value.error) {
            if (!state.value.isLoading && state.value.error != null) {
                Toast.makeText(context, context.getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
        }

        Surface(modifier = Modifier.fillMaxSize(), color = BackgroundWhite) {
            if (state.value.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    HorizontalBarsAnimation(color = PrimaryColorRed, size = 30f)
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        ScreenTitle(title = stringResource(R.string.password)) {
                            navigator.pop()
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        PasswordSection(form = form.value, viewModel = screenViewModel)
                    }
                    CustomButton(text = stringResource(R.string.create_acc)) {
                        if (screenViewModel.validateInput()) {
                            screenViewModel.createSeller(seller)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun PasswordSection(form: PasswordScreenForm, viewModel: PasswordScreenViewModel) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.choose_password),
                style = MaterialTheme.typography.bodyLarge,
                color = Gray,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(
                value = form.password,
                onValueChange = { viewModel.updatePassword(it) },
                label = stringResource(R.string.password),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(
                value = form.confirmPassword,
                onValueChange = { viewModel.updateConfirmPassword(it) },
                label = stringResource(R.string.reenter_password),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )
        }
    }

}