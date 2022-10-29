package com.fruitable.Fruitable.app.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fruitable.Fruitable.app.presentation.component.FruitableDivider
import com.fruitable.Fruitable.app.presentation.component.FruitableTextField
import com.fruitable.Fruitable.app.presentation.component.IsFruitButton
import com.fruitable.Fruitable.app.presentation.component.NumberFormatting
import com.fruitable.Fruitable.app.presentation.event.AddSaleEvent
import com.fruitable.Fruitable.app.presentation.viewmodel.AddSaleViewModel
import com.fruitable.Fruitable.ui.theme.MainGray4
import com.fruitable.Fruitable.ui.theme.MainGreen1
import com.fruitable.Fruitable.ui.theme.TextStyles

@Composable
fun AddSaleScreen(
    navController: NavController,
    viewModel: AddSaleViewModel = hiltViewModel()
){
    // 제목, 가격, 연락처, 사진, 해시태그, 판매기한
    val titleState = viewModel.saleTitle.value
    val priceState = viewModel.salePrice.value
    val contactState = viewModel.saleContact.value
    val contentState = viewModel.saleContent.value

    Column(
        modifier = Modifier
            .background(White)
            .padding(30.dp)
            .fillMaxSize()
    ){
        Title(modifier = Modifier.padding(top=18.dp, bottom=14.dp), isSavable = viewModel.isSavable())
        FruitableDivider()
        CategoryChoice()
        FruitableTextField(
            state = titleState,
            onValueChange = { viewModel.onEvent(AddSaleEvent.EnteredTitle(it)) },
            onFocusChange = { viewModel.onEvent(AddSaleEvent.ChangeTitleFocus(it)) },
        )
        FruitableTextField(
            state = priceState,
            onValueChange = { if(it.length < 10) viewModel.onEvent(AddSaleEvent.EnteredPrice(it)) },
            onFocusChange = { viewModel.onEvent(AddSaleEvent.ChangePriceFocus(it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = NumberFormatting(),
            isPrice = true
        )
        FruitableTextField(
            state = contactState,
            onValueChange = { viewModel.onEvent(AddSaleEvent.EnteredContact(it)) },
            onFocusChange = { viewModel.onEvent(AddSaleEvent.ChangeContactFocus(it)) },
        )
        FruitableTextField(
            state = contentState,
            onValueChange = { viewModel.onEvent(AddSaleEvent.EnteredContent(it)) },
            onFocusChange = { viewModel.onEvent(AddSaleEvent.ChangeContentFocus(it)) },
            singleLine = false
        )
    }
}
@Composable
fun Title(
    modifier: Modifier = Modifier,
    isSavable: Boolean = false,
){
    Box(
        modifier = modifier.fillMaxWidth()
    ){
        Text(
            text = "글쓰기",
            style = TextStyles.TextProfile1,
            color = Black,
            modifier = Modifier.align(Alignment.Center)
        )
        Text(
            text = "등록",
            color = if (isSavable) MainGreen1 else MainGray4,
            style = TextStyles.TextProfile1,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}

@Composable
fun CategoryChoice(
    modifier: Modifier = Modifier
){
    IsFruitButton(
        modifier = modifier
            .padding(top = 27.dp, bottom = 23.dp)
            .fillMaxWidth()
            .height(40.dp),
    )
}


@Preview
@Composable
fun postsalepreview(){
    AddSaleScreen(rememberNavController())
}