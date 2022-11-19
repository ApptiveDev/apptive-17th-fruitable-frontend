package com.fruitable.Fruitable.app.presentation.view


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fruitable.Fruitable.R
import com.fruitable.Fruitable.app.presentation.component.FruitableDivider
import com.fruitable.Fruitable.app.presentation.component.HashTagButton
import com.fruitable.Fruitable.app.presentation.component.SignButton
import com.fruitable.Fruitable.app.presentation.component.SignTextField
import com.fruitable.Fruitable.app.presentation.event.SignUpEvent
import com.fruitable.Fruitable.app.presentation.navigation.Screen
import com.fruitable.Fruitable.app.presentation.viewmodel.SignUpViewModel
import com.fruitable.Fruitable.ui.theme.TextStyles
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel : SignUpViewModel = hiltViewModel()
){
    val focusRequester = remember{FocusRequester()}

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                SignUpViewModel.RegisterStart.Register -> {
                    navController.navigate(Screen.SignInScreen.route)
                }
                SignUpViewModel.RegisterStart.PrevCertification -> {
                    //이거 아래꺼 지우고 이메일 전송하는걸로 바꾸기...
                    navController.navigate(Screen.SignInScreen.route)
                }
            }
        }
    }

    LazyColumn(){
        item {
            SignUpTop()
        }
        item {
            NameField(viewModel,focusRequester)
            NicknameField(viewModel,focusRequester)
            EmailField(viewModel,focusRequester)
        }
        item{
            PrevCertificationBtn(
                viewModel = viewModel,
                onClick = {viewModel.onEvent((SignUpEvent.PrevCertification))}
            )
        }
        item{
            PasswordField(viewModel,focusRequester)
            RepeatedPasswordField(viewModel,focusRequester)
        }
        item{
            RegisterBtn(onClick = {viewModel.onEvent(SignUpEvent.SignUp)})
        }
    }
}


@Composable
fun SignUpTop(){
    Column {
        Text(
            text = "기본정보",
            style = TextStyles.SignTitle1,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, top = 48.dp)
        )
        Text(
            text = "푸릇에이블에 오신 것을 환영합니다!",
            style = TextStyles.TextBasic1,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 7.dp, 0.dp, 0.dp)
        )
        FruitableDivider(Modifier.padding(30.dp,16.dp,28.dp,30.dp))
    }
}

@Composable
fun InputLabel(
    text : String,
    isEssential : Boolean = false,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 9.dp)
    ){
        Text(
            text = text,
            style = TextStyles.signTitle2,
            color = Color.Black,
            modifier = Modifier
                .padding(end = 3.dp)
        )
        if(isEssential){
            Image(
                painterResource(id = R.drawable.essential),
                contentDescription = "essentail mark",
            )
        }
    }
}

@Composable
fun NameField(
    viewModel: SignUpViewModel,
    focusRequester: FocusRequester,
){
    val value = viewModel.state.name
    val isError = viewModel.state.nameError != null

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 0.dp, 30.dp, 28.dp)
    ){
        InputLabel(text = "이름",true)
        SignTextField(
            modifier = Modifier
                .focusRequester(focusRequester),
            value = value,
            onValueChange = {viewModel.onEvent(SignUpEvent.EnteredName(it))},
            isError = isError,
        )
        if(isError){
            Text(
                text = viewModel.state.nameError!!,
                style = TextStyles.signUpError,
                color = Color.Red,
                modifier = Modifier.padding(top=10.dp)
            )
        }
    }
}

@Composable
fun NicknameField(
    viewModel: SignUpViewModel,
    focusRequester: FocusRequester,
){
    val value = viewModel.state.nickname
    val isError = viewModel.state.nicknameError != null

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 0.dp, 30.dp, 28.dp)
    ){
        InputLabel(text = "닉네임")
        SignTextField(
            modifier = Modifier
                .focusRequester(focusRequester),
            value = value,
            onValueChange = {viewModel.onEvent(SignUpEvent.EnteredNickname(it))},
            isError = isError,
        )
        if(isError){
            Text(
                text = viewModel.state.nicknameError!!,
                style = TextStyles.signUpError,
                color = Color.Red,
                modifier = Modifier.padding(top=10.dp)
            )
        }
    }
}

@Composable
fun EmailField(
    viewModel: SignUpViewModel,
    focusRequester: FocusRequester,
){
    val value = viewModel.state.email
    val isError = viewModel.state.emailError != null

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 0.dp, 30.dp, 28.dp)
    ) {
        InputLabel(text = "이메일")
        SignTextField(
            modifier = Modifier
                .focusRequester(focusRequester),
            value = value,
            onValueChange = {viewModel.onEvent(SignUpEvent.EnteredEmail(it))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = isError,
        )
        if(isError){
            Text(
                text = viewModel.state.emailError!!,
                style = TextStyles.signUpError,
                color = Color.Red,
                modifier = Modifier.padding(top=10.dp)
            )
        }
    }
}

@Composable
fun PasswordField(
    viewModel: SignUpViewModel,
    focusRequester: FocusRequester,
){
    val value = viewModel.state.password
    val isError = viewModel.state.passwordError != null

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 0.dp, 30.dp, 28.dp)
    ){
        InputLabel(text = "비밀번호")
        SignTextField(
            modifier = Modifier
                .focusRequester(focusRequester),
            value = value,
            onValueChange = {viewModel.onEvent(SignUpEvent.EnteredPassword(it))},
            visualTransformation = PasswordVisualTransformation(),
            isError = isError,
        )
        if(isError){
            Text(
                text = viewModel.state.passwordError!!,
                style = TextStyles.signUpError,
                color = Color.Red,
                modifier = Modifier.padding(top=10.dp)
            )
        }
    }
}

@Composable
fun RepeatedPasswordField(
    viewModel: SignUpViewModel,
    focusRequester: FocusRequester,
){
    val value = viewModel.state.repeatedPassword
    val isError = viewModel.state.repeatedPasswordError != null

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 0.dp, 30.dp, 28.dp)
    ) {
        InputLabel(text = "비밀번호 확인")
        SignTextField(
            modifier = Modifier
                .focusRequester(focusRequester),
            value = value,
            onValueChange = {viewModel.onEvent(SignUpEvent.EnteredRepeatedPassword(it))},
            visualTransformation = PasswordVisualTransformation(),
            isError = isError,
        )
        if(isError){
            Text(
                text = viewModel.state.repeatedPasswordError!!,
                style = TextStyles.signUpError,
                color = Color.Red,
                modifier = Modifier.padding(top=10.dp)
            )
        }
    }
}

@Composable
fun PrevCertificationBtn(
    viewModel: SignUpViewModel,
    onClick: () -> Unit
){
    SignButton(
        text = "인증번호 발송",
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 0.dp, 30.dp, 28.dp)
            .height(44.dp),
        onClick = onClick,
    )
}

@Composable
fun RegisterBtn(
    onClick : () -> Unit,
){
    HashTagButton(
        text = "회원가입",
        style = TextStyles.TextBasic2,
        isSelected = true,
        isRipple = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 22.dp, 30.dp, 30.dp)
            .height(44.dp),
        cornerRadius = 10,
        onClick = onClick,
    )
}