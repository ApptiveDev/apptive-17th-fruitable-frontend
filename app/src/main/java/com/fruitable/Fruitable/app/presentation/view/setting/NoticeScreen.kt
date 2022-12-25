package com.fruitable.Fruitable.app.presentation.view.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fruitable.Fruitable.app.presentation.component.FruitableDivider
import com.fruitable.Fruitable.app.presentation.data.notices
import com.fruitable.Fruitable.app.presentation.navigation.Screen
import com.fruitable.Fruitable.app.presentation.view.setting._component.NoticeItem
import com.fruitable.Fruitable.app.presentation.view.setting._component.SettingTitle

@Composable
fun NoticeScreen(
    navController: NavController
) {
    Column {
        SettingTitle("공지사항")
        Column(
            modifier = Modifier.padding(30.dp, 30.dp,30.dp ,0.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            notices.forEachIndexed { index, notice ->
                NoticeItem(
                    title = notice[0],
                    date = notice[1],
                    onClick = { navController.navigate("${Screen.NoticeDetailScreen.route}/$index") }
                )
                FruitableDivider()
            }
        }
    }
}