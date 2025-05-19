package com.nassrallah.vetfarmseller.feature_profile.presentation.contact_us_screen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.nassrallah.vetfarmseller.R
import com.nassrallah.vetfarmseller.ui.components.DetailRow
import com.nassrallah.vetfarmseller.ui.components.ItemCard
import com.nassrallah.vetfarmseller.ui.components.ScreenTitle
import com.nassrallah.vetfarmseller.ui.theme.BackgroundWhite
import com.nassrallah.vetfarmseller.ui.theme.OffBlack
import com.nassrallah.vetfarmseller.ui.theme.PrimaryColorRed
import com.nassrallah.vetfarmseller.utils.Constants.Companion.EMAIL
import com.nassrallah.vetfarmseller.utils.Constants.Companion.FACEBOOK
import com.nassrallah.vetfarmseller.utils.Constants.Companion.PHONE

class ContactUsScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        Surface(modifier = Modifier.fillMaxSize(), color = BackgroundWhite) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ScreenTitle(title = stringResource(R.string.contact_us)) {
                    navigator.pop()
                }
                Spacer(modifier = Modifier.height(24.dp))
                ContactInfoSection()
            }
        }
    }

    @Composable
    fun ContactInfoSection() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ItemCard(
                topSection = {
                    Text(
                        text = stringResource(R.string.phone_num),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = OffBlack
                    )
                },
                midSection = {
                    DetailRow(startText = stringResource(R.string.phone_num), endText = PHONE)
                },
                bottomSection = {
                    Text(
                        text = stringResource(R.string.call),
                        style = MaterialTheme.typography.bodyMedium,
                        color = PrimaryColorRed,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                onClick = {

                }
            )
            ItemCard(
                topSection = {
                    Text(
                        text = stringResource(R.string.email),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = OffBlack
                    )
                },
                midSection = {
                    DetailRow(startText = stringResource(R.string.address), endText = EMAIL)
                },
                bottomSection = {
                    Text(
                        text = stringResource(R.string.send),
                        style = MaterialTheme.typography.bodyMedium,
                        color = PrimaryColorRed,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                onClick = {

                }
            )
            ItemCard(
                topSection = {
                    Text(
                        text = stringResource(R.string.facebook),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = OffBlack
                    )
                },
                midSection = {
                    DetailRow(startText = stringResource(R.string.page_name), endText = FACEBOOK)
                },
                bottomSection = {
                    Text(
                        text = stringResource(R.string.open),
                        style = MaterialTheme.typography.bodyMedium,
                        color = PrimaryColorRed,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                onClick = {

                }
            )
        }
    }
}