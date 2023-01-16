package com.fruitable.Fruitable.app.presentation.viewmodel.sale

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruitable.Fruitable.app.domain.use_case.SaleUseCase
import com.fruitable.Fruitable.app.domain.utils.Resource
import com.fruitable.Fruitable.app.domain.utils.log
import com.fruitable.Fruitable.app.presentation.state.SaleDetailState
import com.fruitable.Fruitable.app.presentation.state.SalesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SaleDetailViewModel @Inject constructor(
    private val saleUseCase: SaleUseCase,
   // private val savedStateHandle: SavedStateHandle
): ViewModel() {
    // 조회, 삭제
  //  val saleId = savedStateHandle.get<Int>("saleId")!!
    private val _saleDetail = mutableStateOf(SaleDetailState())
    val saleDetail = _saleDetail

    init{
       // getSaleDetail(saleId)
    }
    private fun getSaleDetail(saleId: Int) {
        saleUseCase.getSale(saleId).onEach { result ->
            when (result){
                is Resource.Success -> _saleDetail.value = result.data?.let { SaleDetailState(saleDetail = it) }!!
                is Resource.Error -> _saleDetail.value = SaleDetailState(error = result.message ?: "전체 게시글 조회에 실패했습니다.")
                is Resource.Loading -> _saleDetail.value = SaleDetailState(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }
    fun deleteSale(saleId: Int) {
        saleUseCase.deleteSale(saleId).onEach { result ->
            when (result){
                is Resource.Success -> "$saleId 번째 게시글 삭제 성공".log()
                is Resource.Error ->  "$saleId 번째 게시글 삭제 실패".log()
                is Resource.Loading ->  "$saleId 번째 게시글 삭제 로딩중".log()
            }
        }.launchIn(viewModelScope)
    }
}