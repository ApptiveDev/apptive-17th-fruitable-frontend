package com.fruitable.Fruitable.app.presentation.component

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fruitable.Fruitable.ui.theme.*
import com.fruitable.Fruitable.R
import com.fruitable.Fruitable.app.domain.utils.NoRippleInteractionSource

@Composable
fun HashTagButton(
    onClick: () -> Unit = {},
    isSelected: Boolean = false,
    modifier: Modifier = Modifier,
    text: String = "# 과일",
    isRipple: Boolean = true,
    style: TextStyle = TextStyles.TextSmall1,
    isCancellable: Boolean = false,
    onCancelClick: () -> Unit = {},
    cornerRadius : Int = 20,
    enabled : Boolean = true,
){
    val buttonColor = if (isSelected) ButtonDefaults.buttonColors(
                            backgroundColor = MainGreen1,
                            contentColor = MainGreen1,
                            disabledBackgroundColor = MainGray6,
                            disabledContentColor = MainGray6,
                        )
                      else ButtonDefaults.buttonColors(MainGreen2)
    val textColor   = if (isSelected) White else MainGray2
    var borderColor = if (isSelected) MainGreen1 else MainGreen3

    if(!enabled) borderColor = MainGray6

    Button(
        onClick = onClick,
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius.dp))
            .border(BorderStroke(1.dp, borderColor), RoundedCornerShape(32.dp))
            .height(32.dp),
        contentPadding = if (isCancellable) PaddingValues(start = 9.dp, end = 12.dp)
                         else PaddingValues(horizontal = 15.dp),
        colors = buttonColor,
        interactionSource = if (isRipple) MutableInteractionSource()
                            else NoRippleInteractionSource(),
        enabled = enabled,
    ) {
        if (isCancellable) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(16.dp)
                    .clickable(onClick = onCancelClick)
                    .background(MainGreen3),
                contentAlignment = Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cancel),
                    contentDescription = "cancel",
                    modifier = Modifier.size(8.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = text,
            color = textColor,
            style = style,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(CenterVertically)
        )
    }
}