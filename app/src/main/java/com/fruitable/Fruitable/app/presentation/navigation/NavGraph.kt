package com.fruitable.Fruitable.app.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fruitable.Fruitable.app.presentation.view.*
import com.fruitable.Fruitable.app.presentation.view.setting.*

fun NavGraphBuilder.fruitableGraph(
    navController: NavController
){
    /**
     * sale - 전체 게시글, 상세 게시글, 게시글 추가 Screen
     */
    composable(
        route = Screen.SalesScreen.route) {
        SalesScreen(navController)
    }
    composable(
        route = Screen.AddSaleScreen.route) {
        AddSaleScreen(navController)
    }
    composable(
        route = Screen.SaleDetailScreen.route+"/{saleId}",
        arguments = listOf(navArgument("saleId") { type = NavType.IntType })
    ){ backStackEntry ->
        val id = backStackEntry.arguments?.getInt("saleId") ?: 0
        SaleDetailScreen(navController = navController, saleId = id)
    }
    /**
     * user - 로그인, 회원가입, 회원정보 수정 Screen
     */
    composable(
        route = Screen.LogInScreen.route){
            LogInScreen(navController)
        }

    composable(
        route = Screen.SignUpScreen.route){
            SignUpScreen(navController)
    }
    composable(
        route = Screen.UserInfoUpdateScreen.route) {
        UserInfoUpdateScreen(navController)
    }
    /**
     * setting - 설정, 계정 정보, 이메일 정보, 판매자 등업, 회원 탈퇴, 공지, 공지 상세 Screen
     */
    composable(
        route = Screen.AccountScreen.route) {
        AccountScreen(navController)
    }
    composable(
        route = Screen.SettingScreen.route) {
        SettingScreen(navController)
    }
    composable(
        route = Screen.UpgradingScreen.route) {
        UpgradingScreen(navController)
    }
    composable(
        route = Screen.EmailScreen.route) {
        EmailScreen(navController)
    }
    composable(
        route = Screen.LeaveAppScreen.route) {
        LeaveAppScreen(navController)
    }
    composable(
        route = Screen.NoticeScreen.route) {
        NoticeScreen(navController)
    }
    composable(
        route = Screen.NoticeDetailScreen.route+"/{id}",
        arguments = listOf(navArgument("id") { type = NavType.IntType })
    ){ backStackEntry ->
        val id = backStackEntry.arguments?.getInt("id") ?: 0
        NoticeDetailScreen(navController = navController, id = id )
    }
    composable(
        route = Screen.SplashScreen.route) {
        SplashScreen(navController)
    }
}