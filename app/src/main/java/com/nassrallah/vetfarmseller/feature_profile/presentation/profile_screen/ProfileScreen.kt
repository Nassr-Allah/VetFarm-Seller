package com.nassrallah.vetfarmseller.feature_profile.presentation.profile_screen

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.nassrallah.vetfarmseller.AuthActivity
import com.nassrallah.vetfarmseller.R
import com.nassrallah.vetfarmseller.feature_profile.presentation.contact_us_screen.ContactUsScreen
import com.nassrallah.vetfarmseller.feature_profile.presentation.language_screen.LanguageScreen
import com.nassrallah.vetfarmseller.feature_profile.presentation.personal_info_screen.PersonalInfoScreen
import com.nassrallah.vetfarmseller.ui.components.HorizontalBarsAnimation
import com.nassrallah.vetfarmseller.ui.theme.BackgroundWhite
import com.nassrallah.vetfarmseller.ui.theme.Burgundy
import com.nassrallah.vetfarmseller.ui.theme.Gray
import com.nassrallah.vetfarmseller.ui.theme.OffBlack
import com.nassrallah.vetfarmseller.ui.theme.OffWhite
import com.nassrallah.vetfarmseller.ui.theme.PrimaryColorRed

class ProfileScreen : Screen {

    @Composable
    override fun Content() {

        val screenViewModel = getViewModel<ProfileViewModel>()
        val state = screenViewModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current.applicationContext
        val activity = LocalContext.current as Activity

        LaunchedEffect(key1 = state.value.isLoggedOut) {
            if (state.value.isLoggedOut) {
                val toAuthActivity = Intent(activity, AuthActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(toAuthActivity)
                activity.finish()
            }
        }

        val items = listOf(
            "المعلومات الشخصية",
            "اللغة",
            "سياسة الخصوصية",
            "شروط الاستخدام",
            "تواصل معنا",
        )

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
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Text(
                        text = "حسابي",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Burgundy
                    )
                    MidSection(items = items) {
                        when (it) {
                            0 -> {
                                navigator.push(PersonalInfoScreen(1))
                            }

                            1 -> {
                                navigator.push(LanguageScreen())
                            }

                            2 -> {}
                            3 -> {}
                            4 -> {
                                navigator.push(ContactUsScreen())
                            }
                        }
                    }
                    LogoutTile {
                        screenViewModel.logout()
                    }
                }
            }
        }
    }

    @Composable
    fun MidSection(items: List<String>, onClick: (Int) -> Unit) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            repeat(items.size) {
                ProfileItem(title = items[it]) {
                    onClick(it)
                }
            }
        }
    }

    @Composable
    fun ProfileItem(title: String, onClick: () -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .clickable { onClick() }
                .background(OffWhite)
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = OffBlack,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.ExtraBold
            )
            Icon(
                painter = painterResource(R.drawable.ic_arrow_left),
                contentDescription = null,
                tint = Gray,
                modifier = Modifier.size(12.dp)
            )
        }
    }

    @Composable
    fun LogoutTile(onClick: () -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                }
                .clip(RoundedCornerShape(12.dp))
                .background(OffWhite)
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "تسجيل الخروج",
                color = PrimaryColorRed,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.ExtraBold
            )
            Icon(
                painter = painterResource(R.drawable.ic_logout),
                contentDescription = null,
                tint = PrimaryColorRed,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}