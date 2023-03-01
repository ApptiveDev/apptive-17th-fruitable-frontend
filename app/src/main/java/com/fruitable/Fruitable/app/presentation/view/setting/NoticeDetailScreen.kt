package com.fruitable.Fruitable.app.presentation.view.setting

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fruitable.Fruitable.app.presentation.component.FruitableDivider
import com.fruitable.Fruitable.app.presentation.data.notices
import com.fruitable.Fruitable.app.presentation.view.setting._component.SettingTitle
import com.fruitable.Fruitable.ui.theme.MainGray8
import com.fruitable.Fruitable.ui.theme.TextStyles

@Composable
fun NoticeDetailScreen(
    navController: NavController,
    id: Int = 0
) {
    Column{
        SettingTitle("공지사항")
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(30.dp, 30.dp, 25.dp ,25.dp)
        ) {
            item { Text(text = notices[id][0], style = TextStyles.TextBold6) }
            item { Spacer(modifier = Modifier.height(14.dp)) }
            item {
                Text(
                    text = notices[id][1],
                    style = TextStyles.TextBasic2,
                    color = MainGray8
                )
            }
            item{ FruitableDivider(Modifier.padding(vertical = 30.dp)) }
            item{ Text(text = notices[id][2], style = TextStyles.TextSmall3) }
        }
    }
}