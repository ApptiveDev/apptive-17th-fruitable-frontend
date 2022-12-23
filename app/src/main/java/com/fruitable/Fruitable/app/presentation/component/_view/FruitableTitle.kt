package com.fruitable.Fruitable.app.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fruitable.Fruitable.ui.theme.TextStyles

@Composable
fun FruitableTitle(
    title: String = "",
    subtitle: String = "",
    content: @Composable ColumnScope.() -> Unit
){
    LazyColumn(
        modifier = Modifier.padding(30.dp, 0.dp, 30.dp, 0.dp),
    ){
        item { Spacer(modifier = Modifier.height(48.dp)) }
        item {
            Text(
                text = title,
                style = TextStyles.SignTitle1,
                color = Color.Black
            )
        }
        item { Spacer(modifier = Modifier.height(4.dp)) }
        item {
            Text(
                text = subtitle,
                style = TextStyles.TextBasic1,
                color = Color.Black
            )
        }
        item{ FruitableDivider(modifier = Modifier.padding(top = 16.dp, bottom = 30.dp)) }
        item { Column(content = content) }
    }
}
