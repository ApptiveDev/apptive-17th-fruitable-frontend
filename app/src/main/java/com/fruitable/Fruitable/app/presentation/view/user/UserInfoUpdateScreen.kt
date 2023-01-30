package com.fruitable.Fruitable.app.presentation.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fruitable.Fruitable.app.presentation.component.FruitableButton
import com.fruitable.Fruitable.app.presentation.component.FruitableTitle
import com.fruitable.Fruitable.app.presentation.component._feature.FruitablePopUp
import com.fruitable.Fruitable.app.presentation.component._feature.TextFieldBox
import com.fruitable.Fruitable.app.presentation.component._view.DialogBoxLoading
import com.fruitable.Fruitable.app.presentation.event.UserInfoUpdateEvent
import com.fruitable.Fruitable.app.presentation.navigation.Screen
import com.fruitable.Fruitable.app.presentation.viewmodel.user.UserInfoUpdateViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun UserInfoUpdateScreen(
    navController: NavController,
    viewModel: UserInfoUpdateViewModel = hiltViewModel()
) {
    val nicknameState = viewModel.nickname.value
    val passwordState = viewModel.password.value
    val newPasswordState = viewModel.newPassword.value
    val newPasswordState2 = viewModel.newPassword2.value

    val focusRequester = remember { FocusRequester() }
    val scaffoldState = rememberScaffoldState()

    var nicknameDialogOpen by remember { mutableStateOf(false) }
    var passwordDialogOpen by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UserInfoUpdateViewModel.UiEvent.SaveUserNickname -> {
                    nicknameDialogOpen = true
                }
                is UserInfoUpdateViewModel.UiEvent.SaveUserPassword -> {
                    passwordDialogOpen = true
                }
                is UserInfoUpdateViewModel.UiEvent.UpdateError -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "🌱 ${event.message}"
                    )
                }
            }
        }
    }
    if (viewModel.isLoading.value) DialogBoxLoading()
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        FruitablePopUp(
            text = "닉네임이 변경되었습니다.",
            cancel = { nicknameDialogOpen = false },
            confirm = { navController.navigate(Screen.SalesScreen.route) },
            isOpen = nicknameDialogOpen
        )
        FruitablePopUp(
            text = "비밀번호가 변경되었습니다.",
            cancel = { passwordDialogOpen = false },
            confirm = { navController.navigate(Screen.SalesScreen.route) },
            isOpen = passwordDialogOpen
        )
        FruitableTitle(
            title = "회원정보 수정",
            subtitle = "닉네임과 비밀번호를 변경할 수 있습니다 !"
        ) {
            TextFieldBox(
                state = nicknameState,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .focusRequester(focusRequester),
                onValueChange = { viewModel.onEvent(UserInfoUpdateEvent.EnteredNickname(it)) },
                onFocusChange = { viewModel.onEvent(UserInfoUpdateEvent.ChangeNicknameFocus(it)) },
            )
            FruitableButton(
                text = "닉네임 수정",
                enabled = viewModel.isNicknameUpdatable(),
                modifier = Modifier.padding(bottom = 28.dp),
                onClick = { viewModel.onEvent(UserInfoUpdateEvent.NicknameSave) }
            )
            TextFieldBox(
                state = passwordState,
                modifier = Modifier
                    .padding(bottom = 28.dp)
                    .focusRequester(focusRequester),
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { viewModel.onEvent(UserInfoUpdateEvent.EnteredPassword(it)) },
                onFocusChange = { viewModel.onEvent(UserInfoUpdateEvent.ChangePasswordFocus(it)) },
            )
            TextFieldBox(
                state = newPasswordState,
                modifier = Modifier
                    .padding(bottom = 28.dp)
                    .focusRequester(focusRequester),
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { viewModel.onEvent(UserInfoUpdateEvent.EnteredNewPassword(it)) },
                onFocusChange = { viewModel.onEvent(UserInfoUpdateEvent.ChangeNewPasswordFocus(it)) },
            )
            TextFieldBox(
                state = newPasswordState2,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .focusRequester(focusRequester),
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { viewModel.onEvent(UserInfoUpdateEvent.EnteredNewPassword2(it)) },
                onFocusChange = { viewModel.onEvent(UserInfoUpdateEvent.ChangeNewPasswordFocus2(it)) },
            )
            FruitableButton(
                text = "패스워드 수정",
                enabled = viewModel.isPasswordUpdatable(),
                textColor = Color.White,
                onClick = { viewModel.onEvent(UserInfoUpdateEvent.PasswordSave) }
            )
        }
    }
}
