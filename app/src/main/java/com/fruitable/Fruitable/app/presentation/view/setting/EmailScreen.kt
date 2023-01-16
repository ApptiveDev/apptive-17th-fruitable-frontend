package com.fruitable.Fruitable.app.presentation.view.setting

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fruitable.Fruitable.app.domain.use_case.UserUseCase
import com.fruitable.Fruitable.app.presentation.view.setting._component.SettingTitle
import com.fruitable.Fruitable.app.presentation.viewmodel.user.UserViewModel
import com.fruitable.Fruitable.ui.theme.TextStyles

@Composable
fun EmailScreen(
    navController: NavController,
    viewModel: UserViewModel = hiltViewModel()
){
    Column {
        SettingTitle("계정 정보")
        Column(
            modifier = Modifier.padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "이메일 인증 정보",
                style = TextStyles.TextBold5,
                modifier = Modifier.padding(top = 20.dp, bottom = 30.dp)
            )
            Text(text = "인증날짜", style = TextStyles.TextBold1)
            Text(
                text = viewModel.getCookie("date"),
                style = TextStyles.TextSmall3
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "이메일", style = TextStyles.TextBold1)
            Text(
                text = viewModel.getCookie("email"),
                style = TextStyles.TextSmall3
            )
        }
    }
}