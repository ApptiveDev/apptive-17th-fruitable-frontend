package com.fruitable.Fruitable.app.presentation.view


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fruitable.Fruitable.app.presentation.component.FruitableDivider
import com.fruitable.Fruitable.app.presentation.component.SignTextField
import com.fruitable.Fruitable.ui.theme.TextStyles

@Composable
fun SignUpScreen(
    navController: NavController,
){
    val focusRequester = remember{FocusRequester()}

    LazyColumn(){
        item {
            SignUpTop()
        }
        item{

        }
    }
}


@Composable
fun SignUpTop(){
    Column {
        Text(
            text = "기본정보",
            style = TextStyles.SignTitle1,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp)
        )
        Text(
            text = "푸릇에이블에 오신 것을 환영합니다!",
            style = TextStyles.TextBasic1,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 7.dp, 16.dp, 0.dp)
        )
        FruitableDivider(Modifier.padding(bottom = 28.dp))
    }
}
