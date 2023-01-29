package com.fruitable.Fruitable.app.presentation.viewmodel.sale

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruitable.Fruitable.app.data.network.dto.sale.SaleRequestDTO
import com.fruitable.Fruitable.app.data.network.dto.user.UserDTO
import com.fruitable.Fruitable.app.domain.use_case.SaleUseCase
import com.fruitable.Fruitable.app.domain.use_case.UserUseCase
import com.fruitable.Fruitable.app.domain.utils.Resource
import com.fruitable.Fruitable.app.domain.utils.UriUtil.toFile
import com.fruitable.Fruitable.app.domain.utils.log
import com.fruitable.Fruitable.app.presentation.event.AddSaleEvent
import com.fruitable.Fruitable.app.presentation.state.SaleDetailState
import com.fruitable.Fruitable.app.presentation.state.SaleTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddSaleViewModel @Inject constructor(
    private val saleUseCase: SaleUseCase,
    private val userUseCase: UserUseCase,
    private val savedStateHandle: SavedStateHandle,
    @ApplicationContext val context: Context
) : ViewModel(){
    private val saleCategory = mutableStateOf("")

    private val _saleTitle = mutableStateOf(SaleTextFieldState(hint = "제목"))
    val saleTitle: State<SaleTextFieldState> = _saleTitle

    private val _salePrice = mutableStateOf(SaleTextFieldState(hint = "₩ 가격"))
    val salePrice: State<SaleTextFieldState> = _salePrice

    private val _saleContact = mutableStateOf(SaleTextFieldState(hint = "연락처 (전화번호, 오픈채팅 링크 등)"))
    val saleContact: State<SaleTextFieldState> = _saleContact

    private val _saleContent = mutableStateOf(SaleTextFieldState(
        hint = "게시글 내용을 작성해주세요.(친환경, 무농약, 유기농, 오가닉, 무공해 등의 표현을 국가기관의 인증없이 사용하면 처벌 받을 수 있으니 주의하여 주세요.)"
    ))
    val saleContent: State<SaleTextFieldState> = _saleContent

    private val _saleImage = mutableStateListOf<Uri>()
    val saleImage: SnapshotStateList<Uri> = _saleImage

    private val _saleHashTag = mutableStateOf(SaleTextFieldState(hint = "# 해시태그를 입력하세요."))
    val saleHashTag: State<SaleTextFieldState> = _saleHashTag

    private val _saleDeadLine = mutableStateOf(SaleTextFieldState(hint = LocalDate.now().plusDays(7).toString()))
    val saleDeadLine: State<SaleTextFieldState> = _saleDeadLine

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
    private var currentSaleId: Int? = null
    init {
        savedStateHandle.get<Int>("saleId")?.let{ saleId ->
            if (saleId != -1) {
                currentSaleId = saleId
                saleUseCase.getSale(saleId).onEach { result ->
                    when (result){
                        is Resource.Success -> {
                            _saleTitle.value = saleTitle.value.copy(
                                text = result.data?.title ?: "제목",
                                isHintVisible = false
                            )
                        }
                        else -> {}
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    fun isSavable(): Boolean {
        val exceptionCollection = exceptionMap()
        return exceptionCollection.all { it.value }
    }
    private fun getCookie(key: String): String {
        return userUseCase.getCookie(key)
    }
    private fun exceptionMap(): MutableMap<String, Boolean> {
        return mutableMapOf(
            "제목을 입력해주세요." to saleTitle.value.text.isNotBlank(),
            "가격을 입력해주세요." to salePrice.value.text.isNotBlank(),
            "연락처를 입력해주세요." to saleContact.value.text.isNotBlank(),
            "이미지를 선택해주세요." to saleImage.isNotEmpty(),
            "해시태그를 입력해주세요." to saleHashTag.value.textList.isNotEmpty(),
            "해시태그 내용을 완성해주세요." to saleHashTag.value.text.isBlank(),
            "글 내용을 입력해주세요." to !(saleDeadLine.value.isChecked && saleDeadLine.value.text.isBlank()),
            "마감 기한 설정은 오늘 이후부터 가능합니다." to (!saleDeadLine.value.isChecked || saleDeadLine.value.text > LocalDate.now().toString()),
            "글 내용을 입력해주세요." to saleContent.value.text.isNotBlank()
        )
    }
    fun onEvent(event: AddSaleEvent){
        when(event){
            is AddSaleEvent.EnteredCategory -> {
                saleCategory.value = event.value
            }
            is AddSaleEvent.EnteredTitle -> {
                _saleTitle.value = saleTitle.value.copy(text = event.value)
            }
            is AddSaleEvent.ChangeTitleFocus -> {
                _saleTitle.value = saleTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            saleTitle.value.text.isBlank()
                )
            }
            is AddSaleEvent.EnteredPrice -> {
                _salePrice.value = salePrice.value.copy(text = event.value.toString())
            }
            is AddSaleEvent.ChangePriceFocus -> {
                _salePrice.value = salePrice.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            salePrice.value.text.isBlank()
                )
            }
            is AddSaleEvent.EnteredContact -> {
                _saleContact.value = saleContact.value.copy(text = event.value)
            }
            is AddSaleEvent.ChangeContactFocus -> {
                _saleContact.value = saleContact.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            saleContact.value.text.isBlank()
                )
            }
            is AddSaleEvent.EnteredContent -> {
                _saleContent.value = saleContent.value.copy(text = event.value)
            }
            is AddSaleEvent.ChangeContentFocus -> {
                _saleContent.value = saleContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            saleContent.value.text.isBlank()
                )
            }
            is AddSaleEvent.EnteredImage -> {
                if(_saleImage.size < 5) _saleImage.add(event.value!!)
            }
            is AddSaleEvent.RemoveImage -> {
                _saleImage.removeAt(event.index)
            }
            is AddSaleEvent.EnteredHashTag -> {
                _saleHashTag.value = saleHashTag.value.copy(text = event.value,)
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
                _saleDeadLine.value = saleDeadLine.value.copy(text = event.value)
            }
            is AddSaleEvent.ChangeDeadLine -> {
                _saleDeadLine.value = saleDeadLine.value.copy(
                    text = "",
                    isChecked = !saleDeadLine.value.isChecked
                )
            }
            is AddSaleEvent.SaveSale -> {
                if (!isSavable()){
                    exceptionMap().filter{!it.value}.firstNotNullOf { (key, value) ->
                        viewModelScope.launch {
                            _eventFlow.emit(UiEvent.ShowSnackbar(key))
                        }
                    }
                }
                else{
                    saleUseCase.postSale(
                        saleRequestDTO = SaleRequestDTO(
                            userId = UserDTO(
                                id = getCookie("id").toInt(),
                                email = getCookie("email"),
                                pwd = getCookie("pwd"),
                                name = getCookie("name"),
                                role = getCookie("role")
                            ),
                            contact = saleContact.value.text,
                            vege = if (saleCategory.value == "과일") 0 else 1,
                            title = saleTitle.value.text,
                            content = saleContent.value.text,
                            price = salePrice.value.text.toInt(),
                            endDate = saleDeadLine.value.text,
                            tags = saleHashTag.value.textList
                        ),
                        files = _saleImage.map { toFile(context, it) }
                    ).onEach {
                        when (it) {
                            is Resource.Success -> {
                                "게시글 작성 성공".log()
                                _eventFlow.emit(UiEvent.SaveInformation)
                            }
                            is Resource.Error ->  "게시글 작성 실패".log()
                            is Resource.Loading -> "게시글 작성 로딩중".log()
                        }
                    }.launchIn(viewModelScope)
                }
            }
        }
    }
    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveInformation: UiEvent()
    }
}
