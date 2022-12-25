package com.fruitable.Fruitable.app.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruitable.Fruitable.app.presentation.event.AddSaleEvent
import com.fruitable.Fruitable.app.presentation.state.DeadlineState
import com.fruitable.Fruitable.app.presentation.state.HashTagState
import com.fruitable.Fruitable.app.presentation.state.ImageState
import com.fruitable.Fruitable.app.presentation.state.SaleTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddSaleViewModel @Inject constructor()
: ViewModel(){
    val saleCategory = mutableStateOf("")

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

    private val _saleContent = mutableStateOf(SaleTextFieldState(
        hint = "게시글 내용을 작성해주세요.(친환경, 무농약, 유기농, 오가닉, 무공해 등의 표현을 국가기관의 인증없이 사용하면 처벌 받을 수 있으니 주의하여 주세요.)"
    ))
    val saleContent: State<SaleTextFieldState> = _saleContent

    private val _saleImage = mutableStateOf(ImageState())
    val saleImage: State<ImageState> = _saleImage

    private val _saleHashTag = mutableStateOf(HashTagState(
        hint = "# 해시태그를 입력하세요."
    ))
    val saleHashTag: State<HashTagState> = _saleHashTag

    private val _saleDeadLine = mutableStateOf(DeadlineState(
        hint = LocalDate.now().plusDays(7).toString()
    ))
    val saleDeadLine: State<DeadlineState> = _saleDeadLine

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun isSavable(): Boolean {
        val exceptionCollection = ExceptMap()
        return exceptionCollection.all { it.value.second }
    }

    fun ExceptMap(): MutableMap<String, Pair<String, Boolean>> {
        return mutableMapOf(
            "title" to Pair("제목을 입력해주세요.", saleTitle.value.text.isNotBlank()),
            "price" to Pair("가격을 입력해주세요.", salePrice.value.text.isNotBlank()),
            "contact" to Pair("연락처를 입력해주세요.", saleContact.value.text.isNotBlank()),
            "image" to Pair("이미지를 선택해주세요.", saleImage.value.listOfSelectedImages.isNotEmpty()),
            "hash" to Pair("해시태그를 입력해주세요.", saleHashTag.value.textList.isNotEmpty()),
            "hashUncompleted" to Pair("해시태그 내용을 완성해주세요.", saleHashTag.value.text.isBlank()),
            "deadline" to Pair(
                "글 내용을 입력해주세요.",
                !(saleDeadLine.value.isChecked && saleDeadLine.value.text.isBlank())
            ),
            "deadlineRange" to Pair(
                "마감 기한 설정은 오늘 이후부터 가능합니다.",
                !saleDeadLine.value.isChecked || saleDeadLine.value.text > LocalDate.now()
                    .toString()
            ),
            "content" to Pair("글 내용을 입력해주세요.", saleContent.value.text.isNotBlank())
        )
    }
    fun onEvent(event: AddSaleEvent){
        when(event){
            is AddSaleEvent.EnteredCategory -> {
                saleCategory.value = event.value
            }
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
                if (event.value == null) return
                val currentList = saleImage.value.listOfSelectedImages
                _saleImage.value = saleImage.value.copy(
                    uri = event.value,
                    listOfSelectedImages = (currentList + event.value).distinct()
                )
            }
            is AddSaleEvent.RemoveImage -> {
                val updatedImage = saleImage.value.listOfSelectedImages.toMutableList()
                updatedImage.remove(event.value)
                _saleImage.value = saleImage.value.copy(
                    listOfSelectedImages = updatedImage,
                )
            }
            is AddSaleEvent.EnteredHashTag -> {
                _saleHashTag.value = saleHashTag.value.copy(
                    text = event.value,
                )
            }
            is AddSaleEvent.ChangeHashTagFocus -> {
                val updatedHashTag = saleHashTag.value.textList.toMutableList()
                val isFocused = event.focusState.isFocused
                val isBlank = saleHashTag.value.text.isBlank()

                _saleHashTag.value = saleHashTag.value.copy(
                    textList = if(!isFocused && !isBlank) {
                                    (updatedHashTag + saleHashTag.value.text).distinct()
                                } else updatedHashTag,
                    text = if(!isFocused && !isBlank) ""
                           else saleHashTag.value.text,
                    isHintVisible = !isFocused
                )
            }
            is AddSaleEvent.RemoveHashTag -> {
                val updatedHashTag = saleHashTag.value.textList.toMutableList()
                updatedHashTag.remove(event.value)
                _saleHashTag.value = saleHashTag.value.copy(
                    textList = updatedHashTag,
                )
            }
            is AddSaleEvent.EnterDeadLine -> {
                _saleDeadLine.value = saleDeadLine.value.copy(
                    text = event.value,
                )
            }
            is AddSaleEvent.ChangeDeadLine -> {
                _saleDeadLine.value = saleDeadLine.value.copy(
                    text = "",
                    isChecked = !saleDeadLine.value.isChecked
                )
            }
            is AddSaleEvent.SaveSale -> {
                val exceptCollection = ExceptMap()
                val isSavable = exceptCollection.all { it.value.second }

                if (!isSavable){
                    exceptCollection.filter{!it.value.second}.firstNotNullOf { (key, value) ->
                        viewModelScope.launch {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(value.first)
                            )
                        }
                    }
                }
                else{
                    viewModelScope.launch {
                        _eventFlow.emit(
                            UiEvent.SaveInformation
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveInformation: UiEvent()
    }
}
