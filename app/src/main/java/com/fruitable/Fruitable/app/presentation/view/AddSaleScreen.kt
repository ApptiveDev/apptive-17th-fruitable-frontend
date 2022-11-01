package com.fruitable.Fruitable.app.presentation.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.fruitable.Fruitable.R
import com.fruitable.Fruitable.app.domain.utils.log
import com.fruitable.Fruitable.app.presentation.component.FruitableDivider
import com.fruitable.Fruitable.app.presentation.component.FruitableTextField
import com.fruitable.Fruitable.app.presentation.component.IsFruitButton
import com.fruitable.Fruitable.app.presentation.component.NumberFormatting
import com.fruitable.Fruitable.app.presentation.event.AddSaleEvent
import com.fruitable.Fruitable.app.presentation.navigation.Screen
import com.fruitable.Fruitable.app.presentation.viewmodel.AddSaleViewModel
import com.fruitable.Fruitable.ui.theme.*
import com.fruitable.Fruitable.ui.theme.TextStyles.TextPostContent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddSaleScreen(
    navController: NavController,
    viewModel: AddSaleViewModel = hiltViewModel()
) {
    // 제목, 가격, 연락처, 사진, 해시태그, 판매기한
    val titleState = viewModel.saleTitle.value
    val priceState = viewModel.salePrice.value
    val contactState = viewModel.saleContact.value
    val contentState = viewModel.saleContent.value

    val scaffoldState = rememberScaffoldState()

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
        Column(
            modifier = Modifier
                .background(White)
                .padding(30.dp)
                .fillMaxSize()
        ) {
            Title(
                modifier = Modifier.padding(top = 18.dp, bottom = 14.dp),
                isSavable = viewModel.isSavable()
            )
            FruitableDivider()
            CategoryChoice()
            FruitableTextField(
                state = titleState,
                onValueChange = { viewModel.onEvent(AddSaleEvent.EnteredTitle(it)) },
                onFocusChange = { viewModel.onEvent(AddSaleEvent.ChangeTitleFocus(it)) },
            )
            FruitableTextField(
                state = priceState,
                onValueChange = { if (it.length < 10) viewModel.onEvent(AddSaleEvent.EnteredPrice(it)) },
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
            Column {
                FruitableDivider()
                PhotoPicker()
            }
            FruitableTextField(
                state = contentState,
                textStyle = TextPostContent,
                onValueChange = { viewModel.onEvent(AddSaleEvent.EnteredContent(it)) },
                onFocusChange = { viewModel.onEvent(AddSaleEvent.ChangeContentFocus(it)) },
                singleLine = false
            )
        }
    }
}
@Composable
fun PhotoPicker() {
    val viewModel = hiltViewModel<AddSaleViewModel>()
    val saleImage = viewModel.saleImage
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { viewModel.onEvent(AddSaleEvent.EnteredImage(it))}
    )
    LazyRow(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .height(66.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ){
        item {
            PhotoImage(
               onClick = { galleryLauncher.launch("image/") },
               size =  saleImage.value.listOfSelectedImages.size
            )
        }
        if (saleImage.value.listOfSelectedImages.isNotEmpty()){
            itemsIndexed(saleImage.value.listOfSelectedImages){ index: Int, item: Uri ->
                ImagePreviewItem(uri = item)
            }
        }
    }
}

@Composable
fun ImagePreviewItem(
    uri: Uri,
    size: Dp = 66.dp,
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ){
        AsyncImage(
            model = uri,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(size)
                .clip(RoundedCornerShape(4.dp))
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
                style = TextStyles.TextDetailProfile1
            )
            Text(
                text = "/5",
                color = MainGray6,
                style = TextStyles.TextDetailProfile1
            )
        }
    }
}


@Preview
@Composable
fun postsalepreview(){
    AddSaleScreen(rememberNavController())
}