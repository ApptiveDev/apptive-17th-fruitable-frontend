package com.fruitable.Fruitable.app.presentation.view.setting._component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fruitable.Fruitable.app.presentation.component.FruitableDivider
import com.fruitable.Fruitable.ui.theme.TextStyles

@Composable
fun SettingTitle(
    text: String = "설정"
){
    Column(
        modifier = Modifier
            .padding(top = 48.dp)
            .fillMaxWidth()
    ){
        Text(
            text = text,
            style = TextStyles.TextBold2,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(14.dp))
        FruitableDivider()
    }
}
