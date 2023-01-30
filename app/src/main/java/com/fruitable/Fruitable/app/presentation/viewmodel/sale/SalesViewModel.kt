package com.fruitable.Fruitable.app.presentation.viewmodel.sale

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruitable.Fruitable.app.domain.use_case.SaleUseCase
import com.fruitable.Fruitable.app.domain.use_case.UserUseCase
import com.fruitable.Fruitable.app.domain.utils.Resource
import com.fruitable.Fruitable.app.domain.utils.log
import com.fruitable.Fruitable.app.presentation.state.SalesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalesViewModel @Inject constructor(
    private val saleUseCase: SaleUseCase,
    private val userUseCase: UserUseCase
): ViewModel() {
    private val _sales = mutableStateOf(SalesState())
    val sales = _sales

    private val _name = mutableStateOf("")
    val name = _name

    private val _isRefresh = MutableStateFlow(false)
    val isRefresh = _isRefresh.asStateFlow()

    init {
        getSales()
        getUserName()
    }
    private fun getUserName() {
        _name.value = userUseCase.getCookie("name")
    }
    private fun getSales() {
        saleUseCase.getSales().onEach { result ->
            when (result){
                is Resource.Success -> {
                    _isRefresh.value = false
                    _sales.value = result.data?.let { SalesState(salesDTO = it) }!!
                }
                is Resource.Error ->{
                    _isRefresh.value = false
                    _sales.value = SalesState(error = "전체 게시글 조회에 실패했습니다.")
                }
                is Resource.Loading -> _isRefresh.value = true
            }
        }.launchIn(viewModelScope)
    }
    fun refreshGetSales() {
        _isRefresh.value = true
        getSales()
    }
}