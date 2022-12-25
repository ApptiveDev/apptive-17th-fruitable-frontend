package com.fruitable.Fruitable.app.presentation.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fruitable.Fruitable.R
import com.fruitable.Fruitable.app.domain.utils.log
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
        token.log()
        val nextScreen = when (token.isEmpty()){
            true -> Screen.SignInScreen
            false -> Screen.SalesScreen
        }
        delay(1000L)
        navController.popBackStack()
        navController.navigate(nextScreen.route)
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.logo_rounded),
            contentDescription = "logo image",
            modifier = Modifier.size(120.dp)
        )
    }
}