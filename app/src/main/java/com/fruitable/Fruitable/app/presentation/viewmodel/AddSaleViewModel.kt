package com.fruitable.Fruitable.app.presentation.viewmodel

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruitable.Fruitable.app.domain.utils.log
import com.fruitable.Fruitable.app.presentation.event.AddSaleEvent
import com.fruitable.Fruitable.app.presentation.state.ImageState
import com.fruitable.Fruitable.app.presentation.state.SaleTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.lang.Integer.min
import javax.inject.Inject

@HiltViewModel
class AddSaleViewModel @Inject constructor()
: ViewModel(){
    // 제목, 가격, 연락처, 판매기한, 내용,// 사진, 해시태그
    private val _saleTitle = mutableStateOf(SaleTextFieldState(
        hint = "제목"
    ))
    val saleTitle: State<SaleTextFieldState> = _saleTitle

    private val _salePrice = mutableStateOf(SaleTextFieldState(
        hint = "₩ 가격"
    ))
    val salePrice: State<SaleTextFieldState> = _salePrice

    private val _saleContact = mutableStateOf(SaleTextFieldState(
        hint = "연락처 (전화번호, 오픈채팅 링크 등)"
    ))
    val saleContact: State<SaleTextFieldState> = _saleContact

    private val _saleDeadline = mutableStateOf(SaleTextFieldState(
        hint = "제목"
    ))
    val saleDeadline: State<SaleTextFieldState> = _saleDeadline

    private val _saleContent = mutableStateOf(SaleTextFieldState(
        hint = "게시글 내용을 작성해주세요.(친환경, 무농약, 유기농, 오가닉, 무공해 등의 표현을 국가기관의 인증없이 사용하면 처벌 받을 수 있으니 주의하여 주세요.)"
    ))
    val saleContent: State<SaleTextFieldState> = _saleContent

    val saleImage = mutableStateOf(ImageState())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun isSavable(): Boolean {
        return saleTitle.value.text.isNotBlank() && salePrice.value.text.isNotBlank()
                && saleContact.value.text.isNotBlank() && saleContent.value.text.isNotBlank()
    }

    fun onEvent(event: AddSaleEvent){
        when(event){
            is AddSaleEvent.EnteredTitle -> {
                _saleTitle.value = saleTitle.value.copy(
                    text = event.value
                )
            }
            is AddSaleEvent.ChangeTitleFocus -> {
                _saleTitle.value = saleTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            saleTitle.value.text.isBlank()
                )
            }
            is AddSaleEvent.EnteredPrice -> {
                _salePrice.value = salePrice.value.copy(
                    text = event.value
                )
            }
            is AddSaleEvent.ChangePriceFocus -> {
                _salePrice.value = salePrice.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            salePrice.value.text.isBlank()
                )
            }
            is AddSaleEvent.EnteredContact -> {
                _saleContact.value = saleContact.value.copy(
                    text = event.value
                )
            }
            is AddSaleEvent.ChangeContactFocus -> {
                _saleContact.value = saleContact.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            saleContact.value.text.isBlank()
                )
            }
            is AddSaleEvent.EnteredDeadline -> {
                _saleDeadline.value = saleDeadline.value.copy(
                    text = event.value
                )
            }
            is AddSaleEvent.ChangeDeadlineFocus -> {
                _saleDeadline.value = saleDeadline.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            saleDeadline.value.text.isBlank()
                )
            }
            is AddSaleEvent.EnteredContent -> {
                _saleContent.value = saleContent.value.copy(
                    text = event.value
                )
            }
            is AddSaleEvent.ChangeContentFocus -> {
                _saleContent.value = saleContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            saleContent.value.text.isBlank()
                )
            }
            is AddSaleEvent.EnteredImage -> {
                val imageSize = event.value.size
                var updatedImageList = event.value

                if (imageSize > 5) {
                    viewModelScope.launch {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar("이미지는 최대 5개까지 선택 가능합니다.")
                        )
                    }
                }

                if (event.value.isNotEmpty()){
                    updatedImageList = event.value.slice(0..min(4, imageSize-1))
                }
                saleImage.value = saleImage.value.copy(
                    listOfSelectedImages = updatedImageList
                )
            }
            is AddSaleEvent.SaveSale -> {
                // TODO: 게시글 작성 내용 저장
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveInformation: UiEvent()
    }
}
