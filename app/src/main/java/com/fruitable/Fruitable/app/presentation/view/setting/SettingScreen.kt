package com.fruitable.Fruitable.app.presentation.view.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fruitable.Fruitable.app.presentation.component.FruitableDivider
import com.fruitable.Fruitable.app.presentation.component._feature.FruitablePopUp
import com.fruitable.Fruitable.app.presentation.navigation.Screen
import com.fruitable.Fruitable.app.presentation.view.setting._component.SettingTitle
import com.fruitable.Fruitable.app.presentation.view.setting._component.SettingTwoColumn
import com.fruitable.Fruitable.ui.theme.MainGray4
import com.fruitable.Fruitable.ui.theme.TextStyles

@Composable
fun SettingScreen(
    navController: NavController
){
    Column {
        UserSetting(onClick = { navController.navigate(Screen.AccountScreen.route) })
        FruitableDivider(
            modifier = Modifier.padding(vertical = 30.dp),
            color = MainGray4
        )
        ExtraSetting(
            onLogOut = { navController.navigate(Screen.SignInScreen.route) },
            onLeaveApp = { navController.navigate(Screen.LeaveAppScreen.route) }
        )
    }
}

@Composable
fun UserSetting(
    onClick: () -> Unit = {}
){
    Column(
        modifier = Modifier.padding(horizontal = 30.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        SettingTitle()
        Text(
            text = "사용자 설정",
            style = TextStyles.TextBold1
        )
        Text(
            text = "계정 정보",
            style = TextStyles.TextBasic3,
            modifier = Modifier.clickable(onClick = onClick)
        )
        Text(
            text = "판매자로 등업하기",
            style = TextStyles.TextBasic3
        )
    }
}
@Composable
fun ExtraSetting(
    onNotice: () -> Unit = {},
    onLogOut: () -> Unit = {},
    onLeaveApp: () -> Unit = {}
){
    var logOutConfirmDialog by remember { mutableStateOf(false) }
    var logOutDialog by remember { mutableStateOf(false) }

    FruitablePopUp(
        text = "로그아웃 하시겠습니까?",
        cancelText = "취소",
        confirmText = "로그아웃",
        cancel = { logOutConfirmDialog = false},
        confirm = { logOutDialog = true },
        isOpen = logOutConfirmDialog
    )
    FruitablePopUp(
        text = "로그아웃 되었습니다.",
        cancelText = "",
        confirmText = "홈으로",
        confirm = onLogOut,
        isOpen = logOutDialog
    )
    Column(
        modifier = Modifier.padding(horizontal = 30.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        Text(
            text = "기타",
            style = TextStyles.TextBold1
        )
        Text(
            text = "공지사항",
            style = TextStyles.TextBasic3
        )
        SettingTwoColumn(
            text = "언어 설정",
            value = "한국어"
        )
        SettingTwoColumn(
            text = "버전 정보",
            value = "1.0.0"
        )
        Text(
            text = "로그아웃",
            style = TextStyles.TextBasic3,
            modifier = Modifier.clickable{ logOutConfirmDialog = true }
        )
        Text(
            text = "서비스 탈퇴하기",
            style = TextStyles.TextBasic3,
            modifier = Modifier.clickable(onClick = onLeaveApp)
        )
    }
}