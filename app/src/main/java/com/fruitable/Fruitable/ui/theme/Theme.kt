package com.fruitable.Fruitable.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.White

private val ColorPalette = lightColors(
    primary = MainGreen1,
    primaryVariant = White,
    secondary = MainGreen2
)

@Composable
fun FRUITABLETheme(content: @Composable () -> Unit){

    MaterialTheme(
        colors = ColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}