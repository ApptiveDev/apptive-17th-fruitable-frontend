package com.fruitable.Fruitable.app.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fruitable.Fruitable.app.domain.utils.log
import com.fruitable.Fruitable.app.presentation.component.*
import com.fruitable.Fruitable.app.presentation.navigation.Screen
import com.fruitable.Fruitable.app.presentation.viewmodel.sale.SaleDetailViewModel
import com.fruitable.Fruitable.ui.theme.MainGreen1
import com.fruitable.Fruitable.ui.theme.TextStyles

@Composable
fun SaleDetailScreen(
    navController: NavController,
    saleId : Int = 1,
    viewModel: SaleDetailViewModel = hiltViewModel()
) {
    val saleDetail = viewModel.saleDetail.value.saleDetail
    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier.background(Color.White).fillMaxWidth()
            ) {
                FruitableDivider()
                FruitableButton (
                    text = "주문하기",
                    color = MainGreen1,
                    textColor = Color.White,
                    modifier = Modifier.padding(30.dp, 14.dp, 30.dp, 30.dp),
                    onClick = { navController.navigate(Screen.SalesScreen.route) }
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item { DetailTop(deadline = saleDetail.endDate, itemImageUrl = saleDetail.fileURL) }
            item { DetailFarmProfile(nickName = saleDetail.userId.name, phoneNum = saleDetail.contact) }
            item {
                Column(
                    modifier = Modifier.padding(start = 22.dp, top = 22.dp, bottom = 5.dp).fillMaxWidth()
                ){
                    Text(
                        text = formatAmountOrMessage(saleDetail.price.toString()) +"원",
                        style = TextStyles.TextHeavyBold,
                    )
                    Text( text = saleDetail.title, style = TextStyles.TextBold3)
                }
            }
            item {
                Text (
                    text = saleDetail.content,
                    modifier = Modifier.padding(20.dp, 18.dp, 0.dp, 5.dp),
                    style = TextStyles.TextSmall3
                )
            }
            item {
                LazyRow(
                    modifier = Modifier.padding(start = 22.dp, top = 20.dp, bottom = 19.dp)
                ) {
                    item {
                        saleDetail.tags.forEach {
                            Row {
                                HashTagButton(text = it, isRipple = false,)
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}

@Composable
fun DetailTop(
    deadline: String,
    itemImageUrl: List<String>
){
    Box(
        modifier = Modifier.height(300.dp),
        contentAlignment = Alignment.TopEnd
    ){
        ProfileImage(
            imageUrl = if (itemImageUrl.isEmpty()) "https://watermark.lovepik.com/photo/20211208/large/lovepik-the-image-of-a-farmer-doing-cheering-picture_501693759.jpg"
                else itemImageUrl.first(),
            contentDescription = "sale_image",
            modifier = Modifier.fillMaxSize(),
            clip = RoundedCornerShape(0.dp)
        )
        HashTagButton(
            text = "D-$deadline",
            style = TextStyles.TextSmall3,
            modifier = Modifier.padding(top = 20.dp, end = 20.dp),
            isRipple = false,
        )
    }
}
@Composable
fun DetailFarmProfile(
    nickName : String,
    phoneNum : String,
    imageUrl : String = "https://watermark.lovepik.com/photo/20211208/large/lovepik-the-image-of-a-farmer-doing-cheering-picture_501693759.jpg",
){
    Column {
        Row (
            modifier = Modifier.padding(start = 20.dp,top = 18.dp , bottom=18.dp)
        ){
            ProfileImage(
                imageUrl = imageUrl,
                modifier = Modifier.size(56.dp)
            )
            Column(modifier = Modifier.padding(start = 9.dp,top=7.dp)) {
                Text(
                    text = nickName,
                    style = TextStyles.TextBold2,
                    modifier = Modifier.padding(end = 6.dp)
                )
                Text(text = phoneNum, style = TextStyles.TextSmall2,)
            }
        }
        FruitableDivider(modifier = Modifier.padding(28.dp,0.dp))
    }
}