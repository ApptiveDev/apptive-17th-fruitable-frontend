package com.fruitable.Fruitable.app.presentation.component._view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fruitable.Fruitable.R
import com.fruitable.Fruitable.ui.theme.MainGreen1
import com.fruitable.Fruitable.ui.theme.TextStyles

@Composable
fun FruitableCheckBox(
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
    borderColor: Color = MainGreen1,
    backgroundColor: Color = MainGreen1,
    onClick: () -> Unit = {},
    width: Dp = 11.dp,
    neccessary: String = "",
    text: String = "위의 내용을 숙지했으며 동의합니다.",
    style: TextStyle = TextStyles.TextSmall2
){
    Row (
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .border(
                    if (isChecked) BorderStroke(0.dp, White)
                    else BorderStroke(1.dp, borderColor),
                    shape = RoundedCornerShape(6.dp)
                )
                .size(18.dp)
                .background(if (isChecked) backgroundColor else White)
                .align(Alignment.CenterVertically)
                .clickable(onClick = onClick),
            contentAlignment = Center
        ) {
            if (isChecked) {
                Image(
                    painter = painterResource(id = R.drawable.checkbox),
                    contentDescription = "deadline checkbox",
                    modifier = Modifier.size(11.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(width))
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                    append(neccessary)
                }
                append(text)
            },
            style = style,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}