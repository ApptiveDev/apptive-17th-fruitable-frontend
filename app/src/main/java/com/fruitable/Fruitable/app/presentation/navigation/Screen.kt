package com.fruitable.Fruitable.app.presentation.navigation

sealed class Screen (val route: String){
    object SalesScreen: Screen("sales_screen")
    object DetailSalesScreen : Screen("detail_sales_screen/{itemId}")
    object AddSaleScreen: Screen("add_sale_screen")
}