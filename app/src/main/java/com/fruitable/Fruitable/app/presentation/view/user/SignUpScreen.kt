package com.fruitable.Fruitable.app.presentation.view


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fruitable.Fruitable.app.presentation.component.FruitableButton
import com.fruitable.Fruitable.app.presentation.component.FruitableDivider
import com.fruitable.Fruitable.app.presentation.component.FruitableTitle
import com.fruitable.Fruitable.app.presentation.component._feature.TextFieldBox
import com.fruitable.Fruitable.app.presentation.event.SignUpEvent
import com.fruitable.Fruitable.app.presentation.navigation.Screen
import com.fruitable.Fruitable.app.presentation.viewmodel.SignUpViewModel
import com.fruitable.Fruitable.ui.theme.MainGreen1
import com.fruitable.Fruitable.ui.theme.MainGreen4
import com.fruitable.Fruitable.ui.theme.TextStyles
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel : SignUpViewModel = hiltViewModel()
) {
    val nicknameState = viewModel.nickname.value
    val emailState = viewModel.email.value
    val numberState = viewModel.number.value
    val passwordState = viewModel.password.value
    val password2State = viewModel.password2.value

    var certification = viewModel.certification.value
    val focusRequester = remember { FocusRequester() }
    val emailColor = if (certification%2 == 1) MainGreen1 else MainGreen4
    val isSignUpAble = viewModel.isSignUpAble()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                SignUpViewModel.UiEvent.SignUp -> {
                    navController.navigate(Screen.SignInScreen.route)
                }
            }
        }
    }
    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .alpha(if (isSignUpAble) 1f else 0.7f)
            ) {
                FruitableDivider()
                FruitableButton(
                    text = "가입완료",
                    color = MainGreen1,
                    textColor = Color.White,
                    modifier = Modifier
                        .padding(30.dp, 14.dp, 30.dp, 30.dp)
                        .fillMaxWidth()
                        .height(44.dp),
                    onClick = { viewModel.onEvent(SignUpEvent.SignUp) }
                )
            }
        }
    ) {
        FruitableTitle(
            title = "회원가입",
            subtitle = "푸릇에이블에 오신 것을 환영합니다 !"
        ) {
            TextFieldBox(
                state = nicknameState,
                modifier = Modifier.focusRequester(focusRequester),
                onValueChange = { viewModel.onEvent(SignUpEvent.EnteredNickname(it)) },
                onFocusChange = { viewModel.onEvent(SignUpEvent.ChangeNicknameFocus(it)) },
            )
            Spacer(modifier = Modifier.height(28.dp))
            TextFieldBox(
                state = emailState,
                modifier = Modifier.focusRequester(focusRequester),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.EnteredEmail(it))
                },
                enabled = certification != 4,
                onFocusChange = { viewModel.onEvent(SignUpEvent.ChangeEmailFocus(it)) },
            )
            if (certification in 2..3) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextFieldBox(
                        state = numberState,
                        isSpaced = false,
                        modifier = Modifier.focusRequester(focusRequester).weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            viewModel.onEvent(SignUpEvent.EnteredCertification(it))
                        },
                    )
                    Text (
                        text = "인증번호 재발송",
                        style = TextStyles.TextBasic1,
                        color = MainGreen1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .border(1.dp, MainGreen1, RoundedCornerShape(10.dp))
                            .padding(16.dp, 12.dp)
                            .clickable{ viewModel.onEvent(SignUpEvent.EnteredCertification("")) }
                    )
                }
                if (numberState.isError)
                    Text(
                        text = "정확한 인증번호 6자리를 입력해주세요.",
                        style = TextStyles.TextBasic1,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 10.dp).align(Start)
                    )
            }
            Spacer(modifier = Modifier.height(16.dp))
            FruitableButton(
                text = if (certification <= 1) "인증번호 발송"
                        else if (certification <= 3) "인증 확인" else "인증 완료",
                color = emailColor,
                textColor = Color.White,
                modifier = Modifier.fillMaxWidth().height(44.dp),
                onClick = {
                    viewModel.onEvent(SignUpEvent.ChangeCertification(certification))
                }
            )
            Spacer(modifier = Modifier.height(28.dp))
            TextFieldBox(
                state = passwordState,
                modifier = Modifier.focusRequester(focusRequester),
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { viewModel.onEvent(SignUpEvent.EnteredPassword(it)) },
                onFocusChange = { viewModel.onEvent(SignUpEvent.ChangePasswordFocus(it)) },
            )
            Spacer(modifier = Modifier.height(28.dp))
            TextFieldBox(
                state = password2State,
                modifier = Modifier.focusRequester(focusRequester),
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { viewModel.onEvent(SignUpEvent.EnteredPassword2(it)) },
                onFocusChange = { viewModel.onEvent(SignUpEvent.ChangePassword2Focus(it)) },
            )
            Spacer(modifier = Modifier.height(100.dp))
        }

    }
}