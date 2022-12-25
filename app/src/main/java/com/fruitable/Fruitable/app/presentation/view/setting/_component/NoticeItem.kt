package com.fruitable.Fruitable.app.presentation.view.setting._component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fruitable.Fruitable.ui.theme.MainGray8
import com.fruitable.Fruitable.ui.theme.TextStyles

@Composable
fun NoticeItem(
    title: String = "",
    date: String = "",
    onClick: () -> Unit = {}
){
    Column(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)
    ){
        Text(
            text = title,
            style = TextStyles.TextBasic3
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = date,
            style = TextStyles.TextSmall1,
            color = MainGray8
        )
    }
}