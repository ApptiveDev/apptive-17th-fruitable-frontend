package com.fruitable.Fruitable.app.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fruitable.Fruitable.app.presentation.viewmodel.SignViewModel

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel : SignViewModel = hiltViewModel()
){
    val emailState = viewModel.signInEmail.value
    val passwordState = viewModel.signInPassword.value

}