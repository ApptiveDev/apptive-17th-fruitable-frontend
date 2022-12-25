package com.fruitable.Fruitable.app.presentation.navigation

import android.accounts.Account
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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
        route = Screen.DetailSalesScreen.route+"/{itemId}"){ backStackEntry ->
        DetailSalesScreen(navController = navController, itemId = (backStackEntry.arguments?.getInt("itemId") ?: "") as Int)
    }
    /**
     * user - 로그인, 회원가입, 회원정보 수정 Screen
     */
    composable(
        route = Screen.SignInScreen.route){
            SignInScreen(navController)
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
     * setting - 설정, 계정 정보, 이메일 정보, 판매자 등업, 회원 탈퇴 Screen
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
}