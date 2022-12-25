package com.fruitable.Fruitable.app.presentation.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fruitable.Fruitable.app.presentation.component.HashTagButton
import com.fruitable.Fruitable.app.presentation.component.SignTextField
import com.fruitable.Fruitable.app.presentation.navigation.Screen
import com.fruitable.Fruitable.app.presentation.viewmodel.SignInViewModel
import kotlinx.coroutines.flow.collectLatest
import com.fruitable.Fruitable.R
import com.fruitable.Fruitable.app.presentation.component.FruitableButton
import com.fruitable.Fruitable.app.presentation.event.LeaveAppEvent
import com.fruitable.Fruitable.app.presentation.event.SignInEvent
import com.fruitable.Fruitable.ui.theme.*

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel : SignInViewModel = hiltViewModel()
){
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event->
            when(event){
                is SignInViewModel.LoginStart.login -> {
                    //Todo 통신후 적합하면..
                    navController.navigate(Screen.SalesScreen.route){
                        popUpTo(0)
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LoginImage()
        LoginField(focusRequester = focusRequester, viewModel = viewModel)
        LoginBtn(loginAble = viewModel.isLoginable(), onClick = {viewModel.onEvent(SignInEvent.SignIn)})
        Text(
            text="아이디/비밀번호 찾기",
            style = TextStyles.TextSmall1,
            color = MainGray6,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 3.dp, end = 35.dp),
            textAlign = TextAlign.Right,
        )
        FruitableButton(
            onClick = {navController.navigate(Screen.SignUpScreen.route)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 42.dp, 30.dp, 0.dp)
                .height(44.dp)
        )
    }
}

@Composable
fun LoginImage(){
    val configuration = LocalConfiguration.current
    val screenHeight = (configuration.screenHeightDp.dp / 5) * 2
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight)
    ){
        Image(
            painterResource(id = R.drawable.logo_rounded),
            contentDescription = "logo_image",
            modifier = Modifier
                .padding(bottom = 30.dp)
                .size(105.dp)
                .align(BottomCenter),
        )
    }
}


@Composable
fun LoginField(
    viewModel: SignInViewModel,
    focusRequester: FocusRequester,
){
    val emailState = viewModel.signInEmail.value
    val passwordState = viewModel.signInPassword.value

    val errorList = listOfNotNull(emailState.errorMessage,passwordState.errorMessage)

    if(errorList.isNotEmpty()){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 33.dp,bottom = 10.dp)
        ){
            Image(
                painterResource(id = R.drawable.warning),
                contentDescription = "emailError",
                modifier = Modifier
                    .size(14.dp)
                    .align(CenterVertically)
            )
            Text(
                text = errorList.first(),
                style = TextStyles.TextSmall1,
                color = Color.Red,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
    }
    SignTextField(
        modifier = Modifier
            .padding(30.dp, 7.dp)
            .focusRequester(focusRequester),
        state = emailState,
        onFocusChange = { viewModel.onEvent(SignInEvent.ChangeEmailFocus(it))},
        onValueChange = { viewModel.onEvent(SignInEvent.EnteredEmail(it))},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        isError = emailState.errorMessage != null,
    )
    SignTextField(
        modifier = Modifier
            .padding(30.dp, 7.dp)
            .focusRequester(focusRequester),
        state = passwordState,
        onFocusChange = { viewModel.onEvent(SignInEvent.ChangePasswordFocus(it))},
        onValueChange = { viewModel.onEvent(SignInEvent.EnteredPassword(it))},
        visualTransformation = PasswordVisualTransformation(),
        isError = passwordState.errorMessage != null,
    )
}


@Composable
fun LoginBtn(
    loginAble: Boolean = false,
    onClick : () -> Unit = {}
) {
    FruitableButton(
        text = "로그인",
        color = MainGreen1,
        textColor = Color.White,
        modifier = Modifier
            .padding(30.dp, 25.dp, 30.dp, 10.dp)
            .fillMaxWidth()
            .height(44.dp)
            .alpha(if (loginAble) 1f else 0.7f),
        onClick = onClick
    )
}