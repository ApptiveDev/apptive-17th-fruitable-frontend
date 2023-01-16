package com.fruitable.Fruitable.app.presentation.view

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fruitable.Fruitable.R
import com.fruitable.Fruitable.app.presentation.component._view.ResourceImage
import com.fruitable.Fruitable.app.presentation.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val Token = context.getSharedPreferences("token", Context.MODE_PRIVATE)
    val token = Token.getString("token", "").toString()

    LaunchedEffect(key1 = true) {
        val nextScreen = when (token.isEmpty()){
            true -> Screen.LogInScreen
            false -> Screen.SalesScreen
        }
        delay(1000L)
        navController.popBackStack()
        navController.navigate(nextScreen.route)
    }
    ResourceImage(
        boxModifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        resId = R.drawable.logo_rounded,
        size = 120.dp
    )
}