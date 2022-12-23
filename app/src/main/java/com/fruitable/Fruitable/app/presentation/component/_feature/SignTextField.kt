package com.fruitable.Fruitable.app.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fruitable.Fruitable.app.presentation.event.SignInEvent
import com.fruitable.Fruitable.app.presentation.state.SignInState
import com.fruitable.Fruitable.app.presentation.viewmodel.SignInViewModel
import com.fruitable.Fruitable.ui.theme.*


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignTextField(
    state : SignInState = SignInState(),
    value : String = "",
    modifier: Modifier = Modifier,
    onValueChange : (String) -> Unit = {},
    onFocusChange : (FocusState) -> Unit = {},
    textStyle: TextStyle = TextStyles.TextDetailProfile1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError : Boolean = false,
    enable : Boolean = true,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Box(
        modifier = modifier
            .fillMaxWidth()
    ){
        OutlinedTextField(
            value = if(value == "") state.text
                    else value,
            visualTransformation = visualTransformation,
            onValueChange = onValueChange,
            textStyle = textStyle,
            singleLine = true,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onDone = {keyboardController?.hide()}
            ),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { onFocusChange(it) },
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = MainGreen2,
                focusedBorderColor = MainGreen1,
                backgroundColor = Color.White,
                textColor = Color.Black
            ),
            isError = isError,
            enabled = enable,
           )
        if(state.hintOn && value == ""){
            Text(
                text = state.hint,
                style = textStyle,
                color = MainGray6,
                textAlign = TextAlign.Center,
                modifier= Modifier
                    .padding(start = 15.dp)
                    .align(Alignment.CenterStart)
            )
        }
    }
}

@Preview
@Composable
fun test(viewModel:SignInViewModel = hiltViewModel()){
    val emailState = viewModel.signInEmail.value
    val focusRequester = remember { FocusRequester() }
    SignTextField(
        modifier = Modifier
            .focusRequester(focusRequester)
            .padding(30.dp, 14.dp, 30.dp, 0.dp)
            .height(44.dp),
        state = emailState,
        onValueChange = {viewModel.onEvent(SignInEvent.EnteredEmail(it))},
        onFocusChange = {viewModel.onEvent((SignInEvent.ChangeEmailFocus(it)))},
        isError = false
    )
}