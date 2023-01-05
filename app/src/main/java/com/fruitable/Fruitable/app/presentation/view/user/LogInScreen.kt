package com.fruitable.Fruitable.app.presentation.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fruitable.Fruitable.R
import com.fruitable.Fruitable.app.domain.utils.addFocusCleaner
import com.fruitable.Fruitable.app.domain.utils.log
import com.fruitable.Fruitable.app.presentation.component.FruitableButton
import com.fruitable.Fruitable.app.presentation.component._feature.TextFieldBox
import com.fruitable.Fruitable.app.presentation.event.LogInEvent
import com.fruitable.Fruitable.app.presentation.navigation.Screen
import com.fruitable.Fruitable.app.presentation.viewmodel.LogInViewModel
import com.fruitable.Fruitable.ui.theme.MainGray6
import com.fruitable.Fruitable.ui.theme.MainGreen1
import com.fruitable.Fruitable.ui.theme.TextStyles
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LogInScreen(
    navController: NavController,
    viewModel : LogInViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val Token = context.getSharedPreferences("token", Context.MODE_PRIVATE)

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event->
            when(event){
                is LogInViewModel.LogInUiEvent.LogInSuccess -> {
                    Token.edit().putString("token", "token is here").apply()
                    navController.navigate(Screen.SalesScreen.route){
                        popUpTo(0)
                    }
                }
                is LogInViewModel.LogInUiEvent.LogInError -> {
                    "login error ${event.message}".log()
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 30.dp)
    ) {
        LoginImage()
        LoginField(viewModel = viewModel)
        FruitableButton(
            text = "로그인",
            color = MainGreen1,
            textColor = Color.White,
            modifier = Modifier
                .padding(top = 32.dp)
                .alpha(if (true) 1f else 0.7f),
            onClick = {viewModel.onEvent(LogInEvent.SignIn)}
        )
        Text(
            text="아이디/비밀번호 찾기",
            style = TextStyles.TextSmall1,
            color = MainGray6,
            modifier = Modifier.fillMaxWidth().padding(top = 3.dp),
            textAlign = TextAlign.Right,
        )
        FruitableButton(
            onClick = {navController.navigate(Screen.SignUpScreen.route)},
            modifier = Modifier.padding(top = 42.dp)
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
    viewModel: LogInViewModel,
    modifier: Modifier = Modifier,
){
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier.addFocusCleaner(focusManager)
    ) {
        Row {
            Image(
                painterResource(id = R.drawable.warning),
                contentDescription = "emailError",
                modifier = Modifier
                    .size(14.dp)
                    .align(CenterVertically)
            )
            Text(
                text = "에러 발생",
                style = TextStyles.TextSmall1,
                color = Color.Red,
                modifier = Modifier.padding(start = 5.dp)
            )
        }

        TextFieldBox(
            state = viewModel.email.value,
            isSpaced = false,
            modifier = Modifier.focusRequester(focusRequester).padding(top = 10.dp, bottom = 14.dp),
            onValueChange = { viewModel.onEvent(LogInEvent.EnteredEmail(it)) },
            onFocusChange = { viewModel.onEvent(LogInEvent.ChangeEmailFocus(it)) },
        )
        TextFieldBox(
            state = viewModel.password.value,
            isSpaced = false,
            modifier = Modifier.focusRequester(focusRequester),
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = { viewModel.onEvent(LogInEvent.EnteredPassword(it)) },
            onFocusChange = { viewModel.onEvent(LogInEvent.ChangePasswordFocus(it)) },
        )
    }
}