package com.fruitable.Fruitable.app.presentation.view

import android.app.DatePickerDialog
import android.net.Uri
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.fruitable.Fruitable.R
import com.fruitable.Fruitable.app._enums.fruitableSpace
import com.fruitable.Fruitable.app.domain.utils.addFocusCleaner
import com.fruitable.Fruitable.app.presentation.component.FruitableDivider
import com.fruitable.Fruitable.app.presentation.component.FruitableTextField
import com.fruitable.Fruitable.app.presentation.component.HashTagButton
import com.fruitable.Fruitable.app.presentation.component.NumberFormatting
import com.fruitable.Fruitable.app.presentation.component._view.FruitableCheckBox
import com.fruitable.Fruitable.app.presentation.event.AddSaleEvent
import com.fruitable.Fruitable.app.presentation.navigation.Screen
import com.fruitable.Fruitable.app.presentation.viewmodel.AddSaleViewModel
import com.fruitable.Fruitable.ui.theme.*
import kotlinx.coroutines.flow.collectLatest
import java.util.*

@Composable
fun AddSaleScreen(
    navController: NavController,
    viewModel: AddSaleViewModel = hiltViewModel()
) {
    val titleState = viewModel.saleTitle.value
    val priceState = viewModel.salePrice.value
    val contactState = viewModel.saleContact.value
    val contentState = viewModel.saleContent.value

    val scaffoldState = rememberScaffoldState()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val isSavable = viewModel.isSavable()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddSaleViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddSaleViewModel.UiEvent.SaveInformation -> {
                    navController.navigate(Screen.SalesScreen.route)
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column {
            Title(
                modifier = Modifier.padding(top = 48.dp, bottom = 14.dp),
                isSavable = isSavable,
                onClick = { viewModel.onEvent(AddSaleEvent.SaveSale) }
            )
            FruitableDivider(
                padding = if (isSavable) PaddingValues(horizontal = 30.dp) else PaddingValues(0.dp),
                color = if (isSavable) MainGray3 else MainGray4
            )
            LazyColumn(
                modifier = Modifier
                    .background(White)
                    .fillMaxSize()
                    .addFocusCleaner(focusManager),
                contentPadding = PaddingValues(30.dp, 0.dp)
            ) {
                item {
                    val isFruit = CategoryChoice()
                    val isFruitValue = if (isFruit) "과일" else "채소"
                    viewModel.onEvent(AddSaleEvent.EnteredCategory(isFruitValue))
                }
                item {
                    FruitableTextField(
                        modifier = Modifier.focusRequester(focusRequester),
                        state = titleState,
                        onValueChange = { viewModel.onEvent(AddSaleEvent.EnteredTitle(it)) },
                        onFocusChange = { viewModel.onEvent(AddSaleEvent.ChangeTitleFocus(it)) },
                    )
                }
                item {
                    FruitableTextField(
                        modifier = Modifier.focusRequester(focusRequester),
                        state = priceState,
                        onValueChange = {
                            if (it.length < 10) viewModel.onEvent(
                                AddSaleEvent.EnteredPrice(
                                    it
                                )
                            )
                        },
                        onFocusChange = { viewModel.onEvent(AddSaleEvent.ChangePriceFocus(it)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        visualTransformation = NumberFormatting(),
                        isPrice = true
                    )
                }
                item {
                    FruitableTextField(
                        modifier = Modifier.focusRequester(focusRequester),
                        state = contactState,
                        onValueChange = { viewModel.onEvent(AddSaleEvent.EnteredContact(it)) },
                        onFocusChange = { viewModel.onEvent(AddSaleEvent.ChangeContactFocus(it)) },
                    )
                }
                item {
                    Column {
                        FruitableDivider()
                        PhotoPicker()
                    }
                }
                item {
                    HashTagField(focusRequester = focusRequester)
                }
                item {
                    DeadLineField()
                }
                item {
                    FruitableTextField(
                        modifier = Modifier.focusRequester(focusRequester),
                        state = contentState,
                        textStyle = TextStyles.TextSmall3,
                        onValueChange = { viewModel.onEvent(AddSaleEvent.EnteredContent(it)) },
                        onFocusChange = { viewModel.onEvent(AddSaleEvent.ChangeContentFocus(it)) },
                        singleLine = false
                    )
                }
            }
        }
    }
}

@Composable
fun DeadLineField(
    viewModel: AddSaleViewModel = hiltViewModel()
) {
    val deadLine = viewModel.saleDeadLine.value

    val mCalendar = Calendar.getInstance()
    val mYear = mCalendar.get(Calendar.YEAR)
    val mMonth = mCalendar.get(Calendar.MONTH)
    val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    val mContext = LocalContext.current
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            val day = if (mDayOfMonth < 10) "0$mDayOfMonth" else mDayOfMonth
            viewModel.onEvent(AddSaleEvent.EnterDeadLine("$mYear-${mMonth+1}-$day"))
        }, mYear, mMonth, mDay
    )
    val isChecked = deadLine.isChecked

    Column {
        FruitableDivider()
        Row(
            modifier = Modifier.padding(4.dp, fruitableSpace, 0.dp, fruitableSpace)
        ) {
            FruitableCheckBox(
                isChecked = deadLine.isChecked,
                modifier = Modifier.align(CenterVertically),
                onClick = {
                    if (!isChecked) mDatePickerDialog.show()
                    viewModel.onEvent(AddSaleEvent.ChangeDeadLine)
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "판매기한",
                color = Black,
                style = TextStyles.TextSmall3,
                modifier = Modifier.align(CenterVertically)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(
                    BorderStroke(1.dp, MainGreen3),
                    shape = RoundedCornerShape(10.dp)
                )
                .clickable {
                    if (isChecked)
                        mDatePickerDialog.show()
                },
        ){
            Text(
                text = if (isChecked) deadLine.text
                       else deadLine.hint,
                style = TextStyles.TextSmall3,
                color = if (isChecked) MainGray2 else MainGray4,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(CenterVertically)
            )
        }
        Spacer(modifier = Modifier.height(fruitableSpace))
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HashTagField(
    focusRequester: FocusRequester = FocusRequester(),
    viewModel: AddSaleViewModel = hiltViewModel()
){
    val hashTag = viewModel.saleHashTag.value
    val keyboardController = LocalSoftwareKeyboardController.current

    Column{
        FruitableDivider()
        Row(
            modifier = Modifier.padding(4.dp,18.dp,0.dp,0.dp)
        ) {
            Text( text = "해시태그 (", color = Black, style = TextStyles.TextSmall3 )
            Text( text = hashTag.textList.size.toString(), color = MainGreen1, style = TextStyles.TextSmall3 )
            Text( text = "/4)", color = Black, style = TextStyles.TextSmall3 )
        }
        Spacer(modifier = Modifier.height(fruitableSpace))
        if (hashTag.textList.size < 4) {
            Box(
                modifier = Modifier.padding(4.dp, 0.dp, 0.dp, fruitableSpace)
            ) {
                BasicTextField(
                    value = hashTag.text,
                    onValueChange = { viewModel.onEvent(AddSaleEvent.EnteredHashTag(it)) },
                    singleLine = true,
                    textStyle = TextStyles.TextSmall3,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .fillMaxWidth()
                        .onFocusChanged { viewModel.onEvent(AddSaleEvent.ChangeHashTagFocus(it)) }
                )
                if (hashTag.isHintVisible) {
                    Text(text = hashTag.hint, style = TextStyles.TextSmall3, color = MainGray4)
                }
            }
        }
        if (hashTag.textList.isNotEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = fruitableSpace)
            ) {
                items(hashTag.textList) { hashTagText ->
                    HashTagButton(
                        text = "# $hashTagText",
                        isCancellable = true,
                        isRipple = false,
                        onCancelClick = { viewModel.onEvent(AddSaleEvent.RemoveHashTag(hashTagText)) }
                    )
                }
            }
        }
    }
}
@Composable
fun PhotoPicker(
    viewModel: AddSaleViewModel = hiltViewModel()
) {
    val saleImage = viewModel.saleImage
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        viewModel.onEvent(AddSaleEvent.EnteredImage(it))
    }

    LazyRow(
        modifier = Modifier
            .padding(top = 13.dp, bottom = 22.dp)
            .height(75.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(11.dp)
    ){
        item {
            PhotoImage(
               onClick = { galleryLauncher.launch("image/") },
               size =  saleImage.value.listOfSelectedImages.size
            )
        }
        item {
            Spacer(modifier = Modifier.width(5.dp))
        }
        if (saleImage.value.listOfSelectedImages.isNotEmpty()){
            itemsIndexed(saleImage.value.listOfSelectedImages){ index: Int, item: Uri ->
                ImagePreviewItem(uri = item, onDeleteClick = { viewModel.onEvent(AddSaleEvent.RemoveImage(item)) })
            }
        }
    }
}

@Composable
fun ImagePreviewItem(
    uri: Uri,
    size: Dp = 66.dp,
    onDeleteClick: () -> Unit = {}
){
    Box(
        modifier = Modifier.size(75.dp)
    ){
        AsyncImage(
            model = uri,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(BottomStart)
                .size(size)
                .clip(RoundedCornerShape(4.dp))
        )
        Image(
            painter = painterResource(id = R.drawable.delete_picture),
            contentDescription = "delete picture button",
            modifier = Modifier
                .align(TopEnd)
                .clickable(onClick = onDeleteClick)
                .size(18.dp)
        )
    }
}
@Composable
fun Title(
    modifier: Modifier = Modifier,
    isSavable: Boolean = false,
    onClick: () -> Unit = {}
){
    Box(
        modifier = modifier.fillMaxWidth()
    ){
        Text(
            text = "글쓰기",
            style = TextStyles.TextBold2,
            color = Black,
            modifier = Modifier.align(Alignment.Center)
        )
        Text(
            text = "등록",
            color = if (isSavable) MainGreen1 else MainGray4,
            style = TextStyles.TextBold2,
            fontWeight = if (isSavable) FontWeight.Bold else FontWeight.Medium,
            modifier = Modifier
                .padding(end = 30.dp)
                .align(Alignment.CenterEnd)
                .clickable(onClick = onClick)
        )
    }
}

@Composable
fun CategoryChoice(
    modifier: Modifier = Modifier
): Boolean {
    val isFruitClick = remember{ mutableStateOf(true) }
    val color = if (isFruitClick.value) White else MainGreen1
    val notColor = if (isFruitClick.value) MainGreen1 else White
    Row(
        modifier = modifier
            .padding(top = 24.dp, bottom = 23.dp)
            .fillMaxWidth()
            .height(40.dp)
            .border(
                width = 1.5.dp,
                color = MainGreen1,
                shape = RoundedCornerShape(10.dp)
            ),
    ){
        Box(
            modifier = Modifier
                .clickable { isFruitClick.value = true }
                .weight(1f)
                .fillMaxHeight()
                .background(notColor, RoundedCornerShape(10.dp, 0.dp, 0.dp, 10.dp)),
        ) {
            Text(
                text = "과일",
                color = color,
                style = TextStyles.TextBasic2,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Center)
            )
        }
        Box(
            modifier = Modifier
                .clickable { isFruitClick.value = false }
                .weight(1f)
                .fillMaxHeight()
                .background(color, RoundedCornerShape(0.dp, 10.dp, 10.dp, 0.dp)),
        ) {
            Text(
                text = "채소",
                color = notColor,
                style = TextStyles.TextBasic2,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Center)
            )
        }
    }
    return true
}

@Composable
fun PhotoImage(
    size: Int = 0,
    onClick: () -> Unit = {}
){
    Column(
        modifier = Modifier
            .size(66.dp)
            .clip(RoundedCornerShape(4.dp))
            .border(width = 1.dp, color = MainGray3)
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.camera),
            contentDescription = "camera image",
            modifier = Modifier.size(24.dp)
        )
        Row {
            Text(
                text = size.toString(),
                color = MainGreen1,
                style = TextStyles.TextSmall2
            )
            Text(
                text = "/5",
                color = MainGray6,
                style = TextStyles.TextSmall2
            )
        }
    }
}
