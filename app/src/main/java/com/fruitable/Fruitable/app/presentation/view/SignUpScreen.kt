package com.fruitable.Fruitable.app.presentation.view


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
                    //Todo 이메일에 인증번호 전송 및 밑의 상태로 변경
                    viewModel.state = viewModel.state.copy(
                        certificationBtnOn = true
                    )
                }
                SignUpViewModel.RegisterStart.Certification -> {
                    //Todo 홈페이지 인증번호와 비교 후 같으면.. 밑에 상태로 변경하기
                    viewModel.state = viewModel.state.copy(
                        certificationCheck = true
                    )
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
            EmailField(viewModel,focusRequester,viewModel.isCertificationCheck() == false)
        }
        item{
            if(!viewModel.CertificationBtnOn()) {
                PrevCertificationBtn(
                    onClick = { viewModel.onEvent((SignUpEvent.PrevCertification)) },
                    isPrevCertifiable = viewModel.isPrevCertifiable(),
                )
            }else{
                if(!viewModel.isCertificationCheck()) {
                    CertificationField(
                        viewModel = viewModel,
                        focusRequester = focusRequester,
                        isCertifiable = viewModel.isCertifiable(),
                        reClick = { viewModel.onEvent(SignUpEvent.PrevCertification)},      //이메일 재전송
                        onClick = { viewModel.onEvent(SignUpEvent.Certification) }
                    )
                }else{
                    PrevCertificationBtn(
                        onClick = {},
                        isPrevCertifiable = false,
                        text = "인증완료"
                    )
                }
            }
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
    enable : Boolean,
){
    val value = viewModel.state.email
    val isError = viewModel.state.emailError != null

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 0.dp, 30.dp, 0.dp)
    ) {
        InputLabel(text = "이메일")
        SignTextField(
            modifier = Modifier
                .focusRequester(focusRequester),
            value = value,
            onValueChange = {viewModel.onEvent(SignUpEvent.EnteredEmail(it))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = isError,
            enable = enable
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
            .padding(30.dp, 28.dp, 30.dp, 28.dp)
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
fun CertificationField(
    viewModel: SignUpViewModel,
    focusRequester: FocusRequester,
    isCertifiable : Boolean,
    onClick: () -> Unit,
    reClick: () -> Unit,
){
    val value = viewModel.state.certification
    val isError = viewModel.state.certificationError != null

    Column(
        modifier = Modifier
            .padding(horizontal = 30.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            SignTextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .width(180.dp)
                    .height(38.dp),
                value = value,
                onValueChange = { viewModel.onEvent(SignUpEvent.EnteredCertification(it)) },
                isError = isError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            SignButton(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .padding(start = 8.dp)
                    .height(38.dp),
                text = "인증번호 재발송",
                isCancellable = true,
                style = TextStyles.signUpError,
                onClick = reClick
            )
        }
        if (isError) {
            Text(
                text = viewModel.state.certificationError!!,
                style = TextStyles.signUpError,
                color = Color.Red,
                modifier = Modifier.padding(top = 10.dp)
            )
        }

        HashTagButton(
            text = "확인",
            style = TextStyles.TextBasic2,
            isSelected = true,
            isRipple = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 44.dp)
                .height(44.dp),
            onClick = onClick,
            cornerRadius = 10,
        )
    }
}

@Composable
fun PrevCertificationBtn(
    onClick: () -> Unit,
    isPrevCertifiable : Boolean,
    text : String = "인증번호 발송"
){
    HashTagButton(
        text = text,
        style = TextStyles.TextBasic2,
        isSelected = true,
        isRipple = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 28.dp, 30.dp, 0.dp)
            .height(44.dp),
        onClick = onClick,
        cornerRadius = 10,
        enabled = isPrevCertifiable
    )
}

@Composable
fun RegisterBtn(
    onClick : () -> Unit,
){
    HashTagButton(
        text = "가입완료하기",
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