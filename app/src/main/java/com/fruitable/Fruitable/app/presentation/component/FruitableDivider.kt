package com.fruitable.Fruitable.app.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fruitable.Fruitable.ui.theme.MainGray3

@Composable
fun FruitableDivider(
    modifier: Modifier = Modifier
){
    Divider(
        modifier= modifier
            .fillMaxWidth()
            .height(1.dp),
        color = MainGray3
    )
}