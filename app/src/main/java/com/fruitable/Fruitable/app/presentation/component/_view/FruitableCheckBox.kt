package com.fruitable.Fruitable.app.presentation.component._view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fruitable.Fruitable.ui.theme.MainGray4
import com.fruitable.Fruitable.ui.theme.MainGreen3
import com.fruitable.Fruitable.R

@Composable
fun FruitableCheckBox(
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
    borderColor: Color = MainGray4,
    backgroundColor: Color = MainGreen3,
    onClick: () -> Unit = {}
){
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .border(
                if (isChecked) BorderStroke(0.dp, White)
                else BorderStroke(1.dp, borderColor),
                shape = RoundedCornerShape(6.dp)
            )
            .size(18.dp)
            .background(if (isChecked) backgroundColor else White)
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
}