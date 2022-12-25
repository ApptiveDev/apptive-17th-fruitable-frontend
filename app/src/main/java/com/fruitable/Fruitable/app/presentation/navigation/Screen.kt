package com.fruitable.Fruitable.app.presentation.navigation

sealed class Screen (val route: String){
    /**
     * sale - 전체 게시글, 상세 게시글, 게시글 추가 Screen
     */
    object SalesScreen: Screen("sales_screen")
    object DetailSalesScreen : Screen("detail_sales_screen/{itemId}")
    object AddSaleScreen: Screen("add_sale_screen")

    /**
     * user - 로그인, 회원가입, 회원정보 수정 Screen
     */
    object SignInScreen : Screen("sign_in_screen")
    object SignUpScreen : Screen("sign_up_screen")
    object UserInfoUpdateScreen : Screen("user_info_update_screen")
    /**
     * setting - 설정, 계정 정보, 이메일 정보, 판매자 등업, 회원 탈퇴, 공지, 공지 상세 Screen
     */
    object SettingScreen : Screen("setting_screen")
    object AccountScreen : Screen("account_screen")
    object UpgradingScreen : Screen("upgrading_screen")
    object EmailScreen: Screen("email_screen")
    object LeaveAppScreen: Screen("leave_app_screen")
    object NoticeScreen: Screen("notice_screen")
    object NoticeDetailScreen: Screen("notice_detail_screen")
    /**
     * Splash 화면
     */
    object SplashScreen: Screen("splash_screen")
}