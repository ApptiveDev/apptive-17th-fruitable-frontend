package com.fruitable.Fruitable.app.presentation.view.setting._component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.fruitable.Fruitable.ui.theme.MainGreen1
import com.fruitable.Fruitable.ui.theme.TextStyles

@Composable
fun SettingTwoColumn(
    modifier: Modifier = Modifier,
    text: String = "",
    value: String = "",
    onClick: () -> Unit = {}
){
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            style = TextStyles.TextBasic3,
            modifier = Modifier.align(Alignment.CenterStart)
        )
        Text(
            text = value,
            style = TextStyles.TextBasic3,
            color = MainGreen1,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable(onClick = onClick)
        )
    }
}