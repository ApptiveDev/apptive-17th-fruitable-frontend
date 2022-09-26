package com.fruitable.Fruitable.app.presentation.navigation

sealed class Screen (val route: String){
    object SalesScreen: Screen("sales_screen")
}