package com.fruitable.Fruitable.app.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fruitable.Fruitable.ui.theme.MainGreen1
import com.fruitable.Fruitable.ui.theme.MainGreen4
import com.fruitable.Fruitable.ui.theme.TextStyles

@Composable
fun FruitableButton(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    text: String = "회원가입",
    enabled: Boolean = true,
    color: Color = MainGreen1,
    disableColor: Color = MainGreen4,
    textColor: Color = Color.White,
    shape: Shape = RoundedCornerShape(10.dp),
){
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            if (enabled) color else disableColor
        ),
        modifier = modifier.fillMaxWidth().height(44.dp).clip(shape).border(1.dp, textColor, shape),
    ) {
        Text(
            text = text,
            color = textColor,
            style = TextStyles.TextBasic3,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}