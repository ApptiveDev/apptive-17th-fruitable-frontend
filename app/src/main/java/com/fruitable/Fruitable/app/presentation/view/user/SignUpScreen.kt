package com.fruitable.Fruitable.app.presentation.view


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fruitable.Fruitable.R
import com.fruitable.Fruitable.app.domain.utils.*
import com.fruitable.Fruitable.app.presentation.component.FruitableButton
import com.fruitable.Fruitable.app.presentation.component.FruitableDivider
import com.fruitable.Fruitable.app.presentation.component.FruitableTitle
import com.fruitable.Fruitable.app.presentation.component._feature.TextFieldBox
import com.fruitable.Fruitable.app.presentation.component._view.DialogBoxLoading
import com.fruitable.Fruitable.app.presentation.component._view.FruitableCheckBox
import com.fruitable.Fruitable.app.presentation.component._view.ResourceImage
import com.fruitable.Fruitable.app.presentation.event.SignUpEvent
import com.fruitable.Fruitable.app.presentation.navigation.Screen
import com.fruitable.Fruitable.app.presentation.view.user.AgreementPopUp
import com.fruitable.Fruitable.app.presentation.viewmodel.user.SignUpViewModel
import com.fruitable.Fruitable.ui.theme.MainGreen1
import com.fruitable.Fruitable.ui.theme.MainGreen3
import com.fruitable.Fruitable.ui.theme.TextStyles
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel : SignUpViewModel = hiltViewModel()
) {
    val nicknameState = viewModel.nickname.value
    val emailState = viewModel.email.value
    val numberState = viewModel.emailCode.value
    val passwordState = viewModel.password.value
    val password2State = viewModel.password2.value
    var timer by remember { mutableStateOf(3000*60L) }

    val certification = viewModel.certification.value
    val focusRequester = remember { FocusRequester() }
    var isAgree by remember { mutableStateOf(false) }
    val isSignUpAble = viewModel.isSignUpAble() && isAgree
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = timer) {
        while (timer > 0) {
            delay(1000L)
            timer -= 1000L
        }
        if (timer == 0L) viewModel.emailTimerTerminated(false)
    }
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is SignUpViewModel.UiEvent.SignUpSuccess -> {
                    navController.navigate(Screen.LogInScreen.route)
                }
                is SignUpViewModel.UiEvent.SignUpError -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "🌱 ${event.message}"
                    )
                }
            }
        }
    }
    if (viewModel.isLoading.value) DialogBoxLoading()
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
            ) {
                FruitableDivider()
                FruitableButton(
                    text = "가입완료",
                    enabled = isSignUpAble,
                    modifier = Modifier.padding(30.dp, 14.dp, 30.dp, 30.dp),
                    onClick = { viewModel.onEvent(SignUpEvent.SignUp(isAgree)) }
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
                onValueChange = { viewModel.onEvent(SignUpEvent.EnteredEmail(it)) },
                enabled = certification != 4,
                onFocusChange = { viewModel.onEvent(SignUpEvent.ChangeEmailFocus(it)) },
            )
            if (certification in EMAIL_SEND_CERTIFICATION..EMAIL_CERTIFICATION_INPUT) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        TextFieldBox(
                            state = numberState,
                            isSpaced = false,
                            modifier = Modifier.focusRequester(focusRequester),
                            onValueChange = { viewModel.onEvent(SignUpEvent.EnteredCertification(it)) },
                            enabled = (certification < EMAIL_CERTIFICATION_INPUT),
                            endPadding = 60.dp
                        )
                        Text(
                            text = if (certification == EMAIL_CERTIFICATION_INPUT) ""
                                else if (timer == 0L) "인증만료" else timer.timerFormat(),
                            color = Color.Red,
                            style = TextStyles.TextSmall1,
                            textAlign = TextAlign.End,
                            modifier = Modifier.align(CenterEnd).padding(horizontal = 16.dp)
                        )
                    }
                    Text (
                        text = "인증번호 재발송",
                        style = TextStyles.TextBasic1,
                        color = MainGreen1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .border(1.dp, MainGreen1, RoundedCornerShape(10.dp))
                            .padding(16.dp, 12.dp)
                            .clickable {
                                timer = 3000*60L
                                viewModel.onEvent(SignUpEvent.EnteredCertification(""))
                            }
                    )
                }
                if (numberState.isError)
                    Text(
                        text = numberState.error,
                        style = TextStyles.TextBasic1,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 10.dp).align(Start)
                    )
            }
            Spacer(modifier = Modifier.height(16.dp))
            FruitableButton(
                text = if (certification <= EMAIL_INPUT_SUCCESS ) "인증번호 발송"
                       else if (certification <= EMAIL_CERTIFICATION_INPUT) "인증 확인" else "인증 완료",
                enabled = (certification%2 == 1 && timer >= 0) || (numberState.text.length == 6),
                textColor = Color.White,
                onClick = {
                    if (certification == EMAIL_INPUT_SUCCESS) timer = 3000*60L
                    viewModel.onEvent(SignUpEvent.ChangeCertification(certification))
                    if (timer == 0L) viewModel.emailTimerTerminated()
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
                    utilCheck = false; infoCheck = false; ageCheck =  false
                } else {
                    utilCheck = true; infoCheck = true; ageCheck =  true
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
            ResourceImage(
                resId = R.drawable.arrow,
                size = 14.dp,
                boxModifier = Modifier.align(CenterEnd).clickable{ isUtilOpen = !isUtilOpen}
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
            ResourceImage(
                resId = R.drawable.arrow,
                boxModifier = Modifier.align(CenterEnd).clickable{ isInfoOpen = !isInfoOpen},
                size = 7.dp
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