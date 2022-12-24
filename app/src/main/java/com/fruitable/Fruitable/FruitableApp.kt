package com.fruitable.Fruitable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.fruitable.Fruitable.app.presentation.navigation.Screen
import com.fruitable.Fruitable.app.presentation.navigation.fruitableGraph
import com.fruitable.Fruitable.ui.theme.FRUITABLETheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun FruitableApp(){
    FRUITABLETheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Screen.SignInScreen.route
            ) {
                fruitableGraph(
                    navController = navController
                )
            }
        }
    }
}