package com.fruitable.Fruitable.app.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fruitable.Fruitable.ui.theme.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun HashTagButton(
    onClick: () -> Unit = {},
    isSelected: Boolean = false,
    modifier: Modifier = Modifier,
    text: String = "#과일",
    isRipple: Boolean = true,
    style: TextStyle = TextStyles.TextProfile2
){
    val buttonColor = if (isSelected) ButtonDefaults.buttonColors(MainGreen1)
                      else ButtonDefaults.buttonColors(MainGreen2)
    val textColor   = if (isSelected) White else MainGray2
    val borderColor = if (isSelected) MainGreen1 else MainGreen3

    Button(
        onClick = onClick,
        modifier = modifier
            .clip(RoundedCornerShape(32.dp))
            .border(BorderStroke(1.dp, borderColor), RoundedCornerShape(32.dp))
            .height(32.dp),
        contentPadding = PaddingValues(horizontal = 15.dp),
        colors = buttonColor,
        interactionSource = if (isRipple) MutableInteractionSource() else NoRippleInteractionSource()
    ) {
        Text(
            text = text,
            color = textColor,
            style = style,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(CenterVertically)
        )
    }
}
class NoRippleInteractionSource : MutableInteractionSource {
    override val interactions: Flow<Interaction> = emptyFlow()
    override suspend fun emit(interaction: Interaction) {}
    override fun tryEmit(interaction: Interaction) = true
}