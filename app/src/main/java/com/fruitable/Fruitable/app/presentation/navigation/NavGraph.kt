package com.fruitable.Fruitable.app.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fruitable.Fruitable.app.presentation.view.AddSaleScreen
import com.fruitable.Fruitable.app.presentation.view.DetailSalesScreen
import com.fruitable.Fruitable.app.presentation.view.SalesScreen

fun NavGraphBuilder.fruitableGraph(
    navController: NavController
){
    composable(
        route = Screen.SalesScreen.route) {
        SalesScreen(navController)
    }
    composable(
        route = Screen.AddSaleScreen.route) {
        AddSaleScreen(navController)
    }
    composable(
        route = Screen.DetailSalesScreen.route+"/{itemId}"){ backStackEntry ->
        DetailSalesScreen(navController = navController, itemId = (backStackEntry.arguments?.getInt("itemId") ?: "") as Int)
    }
}