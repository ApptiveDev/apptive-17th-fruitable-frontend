package com.fruitable.Fruitable.app.presentation.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fruitable.Fruitable.ui.theme.MainGray3

@Composable
fun FruitableDivider(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(0.dp),
    color: Color = MainGray3
){
    Divider(
        modifier= modifier.fillMaxWidth().padding(padding).height(1.dp),
        color = color
    )
}