package com.fruitable.Fruitable.app.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.fruitable.Fruitable.app.presentation.state.SaleTextFieldState
import com.fruitable.Fruitable.ui.theme.MainGray4
import com.fruitable.Fruitable.ui.theme.TextStyles

/**
 * 게시글 등록 시 사용하는 TextField
 * 기존과 다르게 박스가 없고 에러 메시지를 출력하지 않음
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FruitableTextField(
    state: SaleTextFieldState = SaleTextFieldState(),
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
    textStyle: TextStyle = TextStyles.TextSmall3,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = true,
    onFocusChange: (FocusState) -> Unit = {},
    isPrice: Boolean = false
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier
    ) {
        FruitableDivider()
        Box(
            modifier = Modifier.padding(4.dp, 16.dp, 0.dp, 16.dp)
        ) {
            Row {
                if (isPrice) Text(text = "₩ ", style = textStyle)
                BasicTextField(
                    value = state.text,
                    onValueChange = onValueChange,
                    singleLine = singleLine,
                    textStyle = textStyle,
                    visualTransformation = visualTransformation,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = KeyboardActions(onDone = {keyboardController?.hide()}),
                    modifier = Modifier.fillMaxWidth().onFocusChanged { onFocusChange(it) }
                )
            }
            if (state.isHintVisible) {
                Text(text = state.hint, style = textStyle, color = MainGray4)
            }
        }
    }
}