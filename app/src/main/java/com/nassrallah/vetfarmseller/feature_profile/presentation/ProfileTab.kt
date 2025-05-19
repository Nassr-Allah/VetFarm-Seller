package com.nassrallah.vetfarmseller.feature_profile.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import com.nassrallah.vetfarmseller.R
import com.nassrallah.vetfarmseller.feature_profile.presentation.profile_screen.ProfileScreen

class ProfileTab : Tab {

    @Composable
    override fun Content() {
        Navigator(ProfileScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = "حسابي"
            val icon = painterResource(R.drawable.ic_profile)

            return remember {
                TabOptions(
                    index = 3u,
                    title = title,
                    icon = icon
                )
            }
        }
}