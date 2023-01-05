package com.fruitable.Fruitable.app.presentation.view


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fruitable.Fruitable.R
import com.fruitable.Fruitable.app.presentation.component.FruitableButton
import com.fruitable.Fruitable.app.presentation.component.FruitableDivider
import com.fruitable.Fruitable.app.presentation.component.FruitableTitle
import com.fruitable.Fruitable.app.presentation.component._feature.TextFieldBox
import com.fruitable.Fruitable.app.presentation.component._view.FruitableCheckBox
import com.fruitable.Fruitable.app.presentation.event.SignUpEvent
import com.fruitable.Fruitable.app.presentation.navigation.Screen
import com.fruitable.Fruitable.app.presentation.view.user.AgreementPopUp
import com.fruitable.Fruitable.app.presentation.viewmodel.SignUpViewModel
import com.fruitable.Fruitable.ui.theme.MainGreen1
import com.fruitable.Fruitable.ui.theme.MainGreen3
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

    val certification = viewModel.certification.value
    val focusRequester = remember { FocusRequester() }
    val emailColor = if (certification%2 == 1) MainGreen1 else MainGreen4
    var isAgree by remember { mutableStateOf(false) }
    val isSignUpAble = viewModel.isSignUpAble() && isAgree

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                SignUpViewModel.UiEvent.SignUp -> {
                    if(isAgree) navController.navigate(Screen.LogInScreen.route)
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
                    modifier = Modifier.padding(30.dp, 14.dp, 30.dp, 30.dp),
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextFieldBox(
                        state = numberState,
                        isSpaced = false,
                        modifier = Modifier
                            .focusRequester(focusRequester)
                            .weight(1f),
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
                            .clickable { viewModel.onEvent(SignUpEvent.EnteredCertification("")) }
                    )
                }
                if (numberState.isError)
                    Text(
                        text = "정확한 인증번호 6자리를 입력해주세요.",
                        style = TextStyles.TextBasic1,
                        color = Color.Red,
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .align(Start)
                    )
            }
            Spacer(modifier = Modifier.height(16.dp))
            FruitableButton(
                text = if (certification <= 1) "인증번호 발송"
                        else if (certification <= 3) "인증 확인" else "인증 완료",
                color = emailColor,
                textColor = Color.White,
                onClick = { viewModel.onEvent(SignUpEvent.ChangeCertification(certification)) }
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
            Spacer(modifier = Modifier.height(28.dp))
            isAgree = Agreement()
            Spacer(modifier = Modifier.height(100.dp))
        }

    }
}
@Composable
fun Agreement(): Boolean {

    var utilCheck by remember { mutableStateOf(false) }
    var infoCheck by remember { mutableStateOf(false) }
    var ageCheck by remember { mutableStateOf(false) }

    var isUtilOpen by remember { mutableStateOf(false) }
    var isInfoOpen by remember { mutableStateOf(false) }

    AgreementPopUp(isOpen = isUtilOpen, category = true, onDismiss = {isUtilOpen = !isUtilOpen}, onAgree = {utilCheck = true})
    AgreementPopUp(isOpen = isInfoOpen, category = false, onDismiss = {isInfoOpen = !isInfoOpen}, onAgree = {infoCheck = true} )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MainGreen3, RoundedCornerShape(10.dp))
            .padding(14.dp, 19.dp, 21.dp, 22.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        FruitableCheckBox(
            isChecked = (utilCheck && infoCheck && ageCheck),
            onClick = {
                if (utilCheck && infoCheck && ageCheck) {
                    utilCheck = false
                    infoCheck = false
                    ageCheck =  false
                } else {
                    utilCheck = true
                    infoCheck = true
                    ageCheck =  true
                }
            },
            text = "모두 동의합니다",
            style = TextStyles.TextBasic3
        )
        FruitableDivider(modifier = Modifier.padding(horizontal = 5.dp))

        Box(
            modifier = Modifier.fillMaxWidth().padding(end = 5.dp)
        ) {
            FruitableCheckBox(
                isChecked = utilCheck,
                onClick = { utilCheck = !utilCheck },
                neccessary = "(필수) ",
                text = "푸릇에이블 이용약관",
                style = TextStyles.TextBasic2
            )
            Image(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = "agreement detail button",
                modifier = Modifier
                    .align(CenterEnd)
                    .width(7.dp)
                    .clickable{ isUtilOpen = !isUtilOpen}
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth().padding(end = 5.dp)
        ) {
            FruitableCheckBox(
                isChecked = infoCheck,
                onClick = { infoCheck = !infoCheck },
                neccessary = "(필수) ",
                text = "개인정보 수집 및 이용 동의",
                style = TextStyles.TextBasic2
            )
            Image(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = "agreement detail button",
                modifier = Modifier
                    .align(CenterEnd)
                    .width(7.dp)
                    .clickable{ isInfoOpen = !isInfoOpen}
            )
        }
        FruitableCheckBox(
            isChecked = ageCheck,
            onClick = { ageCheck = !ageCheck  },
            neccessary = "(필수) 만 14세 이상입니다.",
            text = "",
            style = TextStyles.TextBasic2
        )
    }

    return  utilCheck && infoCheck && ageCheck
}