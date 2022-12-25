package com.fruitable.Fruitable.app.presentation.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
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
                    navController.navigate(Screen.SalesScreen.route)
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LoginImage()
        LoginField(focusRequester = focusRequester, viewModel = viewModel)
        LoginBtn(navController = navController, onClick = {viewModel.onEvent(SignInEvent.SignIn)})
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
            onClick = {navController.navigate(Screen.SingUpScreen.route)},
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
    navController: NavController,
    onClick : () -> Unit = {}
) {
    HashTagButton(
        text = "로그인",
        style = TextStyles.TextSmall3,
        isSelected = true,
        isRipple = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 25.dp, 30.dp, 10.dp)
            .height(44.dp),
        cornerRadius = 10,
        //need verify
        onClick = onClick,
    )
}