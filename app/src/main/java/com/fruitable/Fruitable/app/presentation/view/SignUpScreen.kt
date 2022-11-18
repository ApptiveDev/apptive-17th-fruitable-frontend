package com.fruitable.Fruitable.app.presentation.view


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun SignUpScreen(
    navController: NavController
){
    LazyColumn(){
        item {

        }
    }
}


@Composable
fun SignUpTop(){
    Column() {
        Text(
            text = "기본정보"
        )
        Text(
            text = "푸릇에이블에 오신 것을 환영합니다!"
        )
    }
}