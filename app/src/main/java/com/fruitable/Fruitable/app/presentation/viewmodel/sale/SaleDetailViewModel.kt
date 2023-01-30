package com.fruitable.Fruitable.app.presentation.viewmodel.sale

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruitable.Fruitable.app.domain.use_case.SaleUseCase
import com.fruitable.Fruitable.app.domain.use_case.UserUseCase
import com.fruitable.Fruitable.app.domain.utils.ORDER_PHONE
import com.fruitable.Fruitable.app.domain.utils.ORDER_URL
import com.fruitable.Fruitable.app.domain.utils.Resource
import com.fruitable.Fruitable.app.presentation.state.SaleDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SaleDetailViewModel @Inject constructor(
    private val saleUseCase: SaleUseCase,
    private val userUseCase: UserUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    val saleId = savedStateHandle.get<Int>("saleId")!!
    private val _saleDetail = mutableStateOf(SaleDetailState())
    val saleDetail = _saleDetail

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
    val isLoading = mutableStateOf(false)
    init {
        getSaleDetail(saleId)
    }
    fun isModifiable(): Boolean {
        val userId = userUseCase.getCookie("id").toInt()
        val saleUserId = saleDetail.value.saleDetail.userId.id
        return userId == saleUserId
    }
    private fun getSaleDetail(saleId: Int) {
        saleUseCase.getSale(saleId).onEach { result ->
            when (result){
                is Resource.Success -> _saleDetail.value = result.data?.let { SaleDetailState(saleDetail = it, isLoading = false) }!!
                is Resource.Error -> _saleDetail.value = SaleDetailState(isLoading = false, error = result.message ?: "전체 게시글 조회에 실패했습니다.")
                is Resource.Loading -> _saleDetail.value = SaleDetailState(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }
    fun getOrderStatus(
        contact: String = saleDetail.value.saleDetail.contact
    ): Int {
        if (Patterns.PHONE.matcher(contact).matches()) return ORDER_PHONE
        if (Patterns.WEB_URL.matcher(contact).matches()) return ORDER_URL
        return 2
    }
    fun deleteSale(saleId: Int) {
        saleUseCase.deleteSale(saleId).onEach { result ->
            when (result){
                is Resource.Success -> {
                    isLoading.value = false
                    _eventFlow.emit(UiEvent.DeleteSuccess)
                }
                is Resource.Error -> {
                    isLoading.value = false
                    _eventFlow.emit(UiEvent.ErrorEvent("게시글을 삭제하는 중 오류가 발생했습니다."))
                }
                is Resource.Loading -> isLoading.value = true
            }
        }.launchIn(viewModelScope)
    }
    sealed class UiEvent {
        data class ErrorEvent(val message: String): UiEvent()
        object DeleteSuccess: UiEvent()
    }
}