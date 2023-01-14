package com.fruitable.Fruitable.app.presentation.viewmodel.user

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

    fun logOut() {
        userUseCase.invokeNone("logOut").onEach {
            when (it) {
                is Resource.Success -> "로그아웃 성공".log()
                is Resource.Error -> "로그아웃 실패".log()
                is Resource.Loading -> "로그아웃 로딩중".log()
            }
        }.launchIn(viewModelScope)
    }
}