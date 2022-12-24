package com.fruitable.Fruitable.app.presentation.view.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fruitable.Fruitable.app.presentation.component.FruitableDivider
import com.fruitable.Fruitable.app.presentation.navigation.Screen
import com.fruitable.Fruitable.app.presentation.view.setting._component.SettingTitle
import com.fruitable.Fruitable.app.presentation.view.setting._component.SettingTwoColumn
import com.fruitable.Fruitable.ui.theme.MainGray4
import com.fruitable.Fruitable.ui.theme.MainGray8
import com.fruitable.Fruitable.ui.theme.TextStyles

@Composable
fun EmailScreen(
    navController: NavController
){
    Column(
        modifier = Modifier.padding(horizontal = 30.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SettingTitle("계정 정보")
        Text(
            text = "이메일 인증 정보",
            style = TextStyles.TextBold5,
            modifier = Modifier.padding(top = 20.dp, bottom = 30.dp)
        )
        Text(
            text = "인증날짜",
            style = TextStyles.TextBold1
        )
        Text(
            text = "2022.11.27",
            style = TextStyles.TextSmall3
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "이메일",
            style = TextStyles.TextBold1
        )
        Text(
            text = "honggildong@gmail.com",
            style = TextStyles.TextSmall3
        )
    }

}