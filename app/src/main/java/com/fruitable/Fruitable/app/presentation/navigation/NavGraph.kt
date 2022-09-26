package com.fruitable.Fruitable.app.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fruitable.Fruitable.app.presentation.view.SalesScreen

fun NavGraphBuilder.fruitableGraph(
    navController: NavController
){
    composable(
        route = Screen.SalesScreen.route
    ) {
        SalesScreen(navController)
    }
}