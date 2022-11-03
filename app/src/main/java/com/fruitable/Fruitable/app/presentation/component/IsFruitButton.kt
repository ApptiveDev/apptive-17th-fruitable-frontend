package com.fruitable.Fruitable.app.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fruitable.Fruitable.app.domain.utils.log
import com.fruitable.Fruitable.ui.theme.MainGray2
import com.fruitable.Fruitable.ui.theme.TextStyles

@Composable
fun IsFruitButton(
    modifier: Modifier = Modifier,
): Boolean {
    var isSelected = remember { mutableStateOf(true) }

    val buttonAlign     = if (isSelected.value) Alignment.CenterStart else Alignment.CenterEnd
    val textAlign       = if (isSelected.value) Alignment.CenterEnd else Alignment.CenterStart
    val selectedText    = if (isSelected.value) "과일" else "채소"
    val nonSelectedText = if (isSelected.value) "채소" else "과일"

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ){
        HashTagButton(
            text = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            isSelected = false,
            isRipple = false,
            onClick = { isSelected.value = !isSelected.value }
        )
        HashTagButton(
            text = selectedText,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(40.dp)
                .align(buttonAlign),
            isSelected = true,
            style = TextStyles.TextBasic1
        )
        Text(
            text = nonSelectedText,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(textAlign),
            textAlign = TextAlign.Center,
            style = TextStyles.TextBasic1,
            color = MainGray2
        )
    }
    return isSelected.value
}