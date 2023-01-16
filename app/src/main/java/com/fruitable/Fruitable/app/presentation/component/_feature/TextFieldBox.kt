package com.fruitable.Fruitable.app.presentation.component._feature

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.fruitable.Fruitable.app.presentation.state.TextFieldBoxState
import com.fruitable.Fruitable.ui.theme.*

/**
 * 로그인, 회원가입, 회원탈퇴, 회원정보 수정 화면에서 사용
 * 잘못된 정보를 입력할 경우, 빨간색 border가 표시됨
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFieldBox(
    modifier: Modifier = Modifier,
    state: TextFieldBoxState = TextFieldBoxState(),
    onValueChange: (String) -> Unit = {},
    isSpaced: Boolean = true,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onFocusChange: (FocusState) -> Unit = {},
){
    val keyboardController = LocalSoftwareKeyboardController.current
    val borderColor = if (state.isError) Red else MainGreen3
    Column(
        modifier = modifier
    ){
        if (isSpaced) {
            Text(text = state.title, style = TextStyles.TextBasic3)
            Spacer(modifier = Modifier.height(10.dp))
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(38.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(if(enabled) Color.White else MainGray5)
                .border(1.dp, borderColor, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            BasicTextField(
                value = state.text,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = TextStyles.TextSmall2.copy(if(enabled) Color.Black else MainGray8),
                keyboardOptions = keyboardOptions,
                keyboardActions = KeyboardActions(onDone = {keyboardController?.hide()}),
                visualTransformation = visualTransformation,
                enabled = enabled,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .onFocusChanged { onFocusChange(it) }
            )
            if (state.isHintVisible) {
                Text(
                    text = state.hint,
                    style = TextStyles.TextSmall2,
                    color = MainGray8,
                    modifier = Modifier.align(Alignment.CenterStart).padding(start = 16.dp)
                )
            }
        }
        if (state.isError && isSpaced) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = state.error,
                style = TextStyles.TextBasic1,
                color = Red
            )
        }
    }
}