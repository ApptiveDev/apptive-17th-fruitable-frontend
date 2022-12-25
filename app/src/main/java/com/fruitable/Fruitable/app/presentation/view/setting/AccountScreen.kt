package com.fruitable.Fruitable.app.presentation.view.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fruitable.Fruitable.app.presentation.component.FruitableDivider
import com.fruitable.Fruitable.app.presentation.navigation.Screen
import com.fruitable.Fruitable.app.presentation.view.setting._component.SettingTitle
import com.fruitable.Fruitable.app.presentation.view.setting._component.SettingTwoColumn
import com.fruitable.Fruitable.app.presentation.view.user.AgreementPopUp
import com.fruitable.Fruitable.ui.theme.MainGray4
import com.fruitable.Fruitable.ui.theme.MainGray8
import com.fruitable.Fruitable.ui.theme.TextStyles

@Composable
fun AccountScreen(
    navController: NavController
){
    var isDialogOpen by remember { mutableStateOf(false) }
    AgreementPopUp(isOpen = isDialogOpen, category = true, onDismiss = {isDialogOpen = !isDialogOpen})

    Column {
        SettingTitle("계정 정보")
        Column(
            modifier = Modifier.padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "계정 정보",
                style = TextStyles.TextBold1,
                modifier = Modifier.padding(vertical = 20.dp)
            )
            SettingTwoColumn(
                text = "이메일 인증",
                value = "확인",
                onClick = { navController.navigate(Screen.EmailScreen.route) }
            )
            Text(
                text = "2022.11.27",
                color = MainGray8,
                style = TextStyles.TextSmall3
            )
        }
        FruitableDivider(modifier = Modifier.padding(vertical = 30.dp),)
        Column(
            modifier = Modifier.padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Text(
                text = "기타",
                style = TextStyles.TextBold1
            )
            Text(
                text = "서비스 이용약관",
                style = TextStyles.TextBasic3,
                modifier = Modifier.fillMaxWidth().clickable{isDialogOpen = !isDialogOpen}
            )
        }
    }
}