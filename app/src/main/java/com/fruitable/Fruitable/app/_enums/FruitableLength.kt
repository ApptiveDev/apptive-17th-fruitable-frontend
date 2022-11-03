package com.fruitable.Fruitable.app._enums

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val fruitableSpace = 22.dp

@Composable
fun GetScreenSize(): Pair<Dp, Dp>{
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    return Pair(screenHeight, screenWidth)
}