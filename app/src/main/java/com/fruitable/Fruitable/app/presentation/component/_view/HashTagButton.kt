package com.fruitable.Fruitable.app.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fruitable.Fruitable.R
import com.fruitable.Fruitable.app.presentation.component._view.ResourceImage
import com.fruitable.Fruitable.ui.theme.*

@Composable
fun HashTagButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    enabled : Boolean = true,
    isCancellable: Boolean = false,
    text: String = "# 과일",
    onClick: () -> Unit = {},
    onCancelClick: () -> Unit = {},
){
    val borderColor = if (isSelected) MainGreen1 else MainGreen3
    val textColor   = if (isSelected) White else MainGray2
    val buttonColor = if (isSelected) MainGreen1 else MainGreen2

    Button(
        onClick = onClick,
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .border(BorderStroke(1.dp, borderColor), RoundedCornerShape(32.dp))
            .height(32.dp),
        contentPadding = if (isCancellable) PaddingValues(start = 9.dp, end = 12.dp)
                         else PaddingValues(horizontal = 15.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = buttonColor,
            contentColor = buttonColor,
            disabledBackgroundColor = buttonColor,
            disabledContentColor = buttonColor
        )
    ) {
        if (isCancellable) {
            ResourceImage(
                resId = R.drawable.cancel,
                size = 8.dp,
                contentAlignment = Center,
                boxModifier = Modifier
                    .clip(CircleShape)
                    .size(16.dp)
                    .background(MainGreen3)
                    .clickable(onClick = onCancelClick)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = text,
            color = textColor,
            style = TextStyles.TextSmall1,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(CenterVertically)
        )
    }
}