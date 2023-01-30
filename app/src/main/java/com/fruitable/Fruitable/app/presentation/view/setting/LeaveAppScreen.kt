package com.fruitable.Fruitable.app.presentation.view.setting

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fruitable.Fruitable.app.presentation.component.FruitableButton
import com.fruitable.Fruitable.app.presentation.component.FruitableDivider
import com.fruitable.Fruitable.app.presentation.component.FruitableTitle
import com.fruitable.Fruitable.app.presentation.component._feature.FruitablePopUp
import com.fruitable.Fruitable.app.presentation.component._feature.TextFieldBox
import com.fruitable.Fruitable.app.presentation.component._view.FruitableCheckBox
import com.fruitable.Fruitable.app.presentation.event.LeaveAppEvent
import com.fruitable.Fruitable.app.presentation.navigation.Screen
import com.fruitable.Fruitable.app.presentation.viewmodel.user.LeaveAppViewModel
import com.fruitable.Fruitable.ui.theme.MainGreen1
import com.fruitable.Fruitable.ui.theme.MainGreen1_1
import com.fruitable.Fruitable.ui.theme.TextStyles
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LeaveAppScreen(
    navController: NavController,
    viewModel: LeaveAppViewModel = hiltViewModel()
){
    val passwordState = viewModel.password.value
    val password2State = viewModel.password2.value

    val focusRequester = remember { FocusRequester() }
    var isChecked by remember { mutableStateOf(false) }
    var isDialogOpen by remember { mutableStateOf(false) }

    val isLeavable = viewModel.isLeavable() && isChecked
    val Token = LocalContext.current.getSharedPreferences("token", Context.MODE_PRIVATE)

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is LeaveAppViewModel.UiEvent.LeaveApp -> {
                    Token.edit().putString("token", "").apply()
                    navController.navigate(Screen.LogInScreen.route){ popUpTo(0) }
                }
                is LeaveAppViewModel.UiEvent.LeaveAppError -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "🌱 ${event.message}"
                    )
                }
            }
        }
    }
    if (viewModel.isLoading.value) DialogBoxLoading()
    FruitablePopUp(
        text  = "탈퇴 시 본인 계정의 모든 기록이 삭제되며 복구할 수 없습니다.\n정말 탈퇴하시겠습니까?",
        cancelText = "취소",
        confirmText = "탈퇴하기",
        cancel = { isDialogOpen = false},
        confirm = { viewModel.onEvent(LeaveAppEvent.LeaveApp) },
        isOpen = isDialogOpen
    )
    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier.background(Color.White).fillMaxWidth()
            ) {
                FruitableDivider()
                FruitableButton(
                    text = "탈퇴하기",
                    enabled = isLeavable,
                    disableColor = MainGreen1_1,
                    modifier = Modifier.padding(30.dp, 14.dp, 30.dp, 30.dp),
                    onClick = {
                        viewModel.passwordErrorCheck()
                        if (isChecked && isLeavable) isDialogOpen = true
                    }
                )
            }
        }
    ) {
        FruitableTitle(
            title = "회원 탈퇴",
            subtitle = "푸릇에이블을 떠나신다니 아쉽습니다."
        ) {
            Text(
                text = "회원 탈퇴 안내",
                style = TextStyles.TextBold6
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "지금까지 푸릇에이블을 이용해주셔서 감사합니다.\n" +
                        "회원을 탈퇴하시면 푸릇에이블 서비스 내 프로필 " +
                        "정보가 삭제되며 복구할 수 없습니다.",
                style = TextStyles.TextSmall2,
            )
            FruitableCheckBox(
                modifier = Modifier.padding(top = 30.dp, bottom = 40.dp),
                isChecked = isChecked,
                borderColor = MainGreen1,
                backgroundColor = MainGreen1,
                onClick = { isChecked = !isChecked }
            )
            TextFieldBox(
                state = passwordState,
                modifier = Modifier.focusRequester(focusRequester),
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { viewModel.onEvent(LeaveAppEvent.EnteredPassword(it)) },
                onFocusChange = { viewModel.onEvent(LeaveAppEvent.ChangePasswordFocus(it)) },
            )
            Spacer(modifier = Modifier.height(28.dp))
            TextFieldBox(
                state = password2State,
                modifier = Modifier.focusRequester(focusRequester),
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { viewModel.onEvent(LeaveAppEvent.EnteredPassword2(it)) },
                onFocusChange = { viewModel.onEvent(LeaveAppEvent.ChangePassword2Focus(it)) },
            )
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}