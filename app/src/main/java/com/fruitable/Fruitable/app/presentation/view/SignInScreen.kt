package com.fruitable.Fruitable.app.presentation.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fruitable.Fruitable.app.presentation.component.HashTagButton
import com.fruitable.Fruitable.app.presentation.component.SignTextField
import com.fruitable.Fruitable.app.presentation.event.SignEvent
import com.fruitable.Fruitable.app.presentation.navigation.Screen
import com.fruitable.Fruitable.app.presentation.viewmodel.SignViewModel
import kotlinx.coroutines.flow.collectLatest
import com.fruitable.Fruitable.R
import com.fruitable.Fruitable.app.presentation.component.SignButton
import com.fruitable.Fruitable.ui.theme.*

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel : SignViewModel = hiltViewModel()
){

    val errorTextState = remember{ mutableStateOf("")}
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event->
            when(event){
                is SignViewModel.RedEvent.ShowText -> {
                   errorTextState.value = event.message
                }
                is SignViewModel.RedEvent.login -> {
                    navController.navigate(Screen.SalesScreen.route)
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LoginImage()
        LoginField(focusRequester = focusRequester, errorTextState = errorTextState)
        LoginBtn(navController = navController, onClick = {viewModel.onEvent(SignEvent.SignIn)})
        Text(
            text="아이디/비밀번호 찾기",
            style = TextStyles.TextProfile2,
            color = MainGray6,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 3.dp, end = 35.dp),
            textAlign = TextAlign.Right,
        )
        SignButton(onClick = {},modifier = Modifier.fillMaxWidth().padding(30.dp,42.dp,30.dp,0.dp).height(44.dp))
    }
}

@Composable
fun LoginImage(){
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp /5 * 2
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight)
    ){
        Image(
            painterResource(id = R.drawable.sign_image),
            contentDescription = "login_image",
            modifier = Modifier
                .size(96.dp)
                .align(Alignment.Center),
        )
    }
}


@Composable
fun LoginField(
    viewModel: SignViewModel = hiltViewModel(),
    focusRequester: FocusRequester,
    errorTextState: MutableState<String>,
){
    val emailState = viewModel.signInEmail.value
    val passwordState = viewModel.signInPassword.value


    SignTextField(
        modifier = Modifier
            .padding(30.dp, 7.dp)
            .focusRequester(focusRequester),
        state = emailState,
        onFocusChange = { viewModel.onEvent(SignEvent.ChangeEmailFocus(it))},
        onValueChange = { viewModel.onEvent(SignEvent.EnteredEmail(it))},
    )
    SignTextField(
        modifier = Modifier
            .padding(30.dp, 7.dp)
            .focusRequester(focusRequester),
        state = passwordState,
        onFocusChange = { viewModel.onEvent(SignEvent.ChangePasswordFocus(it))},
        onValueChange = { viewModel.onEvent(SignEvent.EnteredPassword(it))},
        visualTransformation = PasswordVisualTransformation()
    )
}


@Composable
fun LoginBtn(
    navController: NavController,
    onClick : () -> Unit = {}
) {
    HashTagButton(
        text = "로그인",
        style = TextStyles.TextBasic2,
        isSelected = true,
        isRipple = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .padding(30.dp, 25.dp, 30.dp, 10.dp),
        cornerRadius = 10,
        //need verify
        onClick = onClick,
    )
}