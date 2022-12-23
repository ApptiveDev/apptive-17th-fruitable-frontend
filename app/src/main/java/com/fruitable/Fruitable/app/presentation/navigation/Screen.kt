package com.fruitable.Fruitable.app.presentation.navigation

sealed class Screen (val route: String){
    object SalesScreen: Screen("sales_screen")
    object DetailSalesScreen : Screen("detail_sales_screen/{itemId}")
    object AddSaleScreen: Screen("add_sale_screen")
    object SignInScreen : Screen("sign_in_screen")
    object SingUpScreen : Screen("sign_up_screen")
    object UserInfoUpdateScreen : Screen("user_info_update")
}