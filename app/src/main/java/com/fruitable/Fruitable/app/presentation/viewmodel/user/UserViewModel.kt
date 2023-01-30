package com.fruitable.Fruitable.app.presentation.viewmodel.user

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruitable.Fruitable.app.domain.use_case.UserUseCase
import com.fruitable.Fruitable.app.domain.utils.Resource
import com.fruitable.Fruitable.app.domain.utils.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    val userUseCase: UserUseCase
): ViewModel() {
    val isLoading = mutableStateOf(false)
    fun logOut() {
        userUseCase.invokeNone("logOut").onEach {
            when (it) {
                is Resource.Success -> isLoading.value = false
                is Resource.Error -> isLoading.value = false
                is Resource.Loading -> isLoading.value = true
            }
        }.launchIn(viewModelScope)
    }
    fun getCookie(key: String): String {
        return userUseCase.getCookie(key)
    }
}