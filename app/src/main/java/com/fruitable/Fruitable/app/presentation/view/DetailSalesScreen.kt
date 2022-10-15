package com.fruitable.Fruitable.app.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fruitable.Fruitable.app.presentation.component.HashTagButton
import com.fruitable.Fruitable.app.presentation.component.ProfileImage

@Composable
fun DetailSalesScreen(
    navController: NavController,
    itemId : String,
    //harcoding
    itemImageUrl: String = "https://images-prod.healthline.com/hlcmsresource/images/AN_images/health-benefits-of-apples-1296x728-feature.jpg",
    title: String = "프레샤인 충주 GAP 인증 당도선별 사과",
    deadline: Int = 30,
    price: Int = 14500,
    nickName : String = "판매자 닉네임",
    phoneNum : String = "051-000-0000",
    imageUrl : String = "https://watermark.lovepik.com/photo/20211208/large/lovepik-the-image-of-a-farmer-doing-cheering-picture_501693759.jpg",
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ){
        item{
            DetailTop(deadline = deadline, itemImageUrl = itemImageUrl)
        }
    }
}


@Composable
fun DetailTop(
    deadline: Int,
    itemImageUrl: String
){
    val deadlineText:String;
    if(deadline > 0) deadlineText = "D-" + deadline.toString()
    else deadlineText = "마감"

    Box(
        modifier = Modifier
            .height(340.dp)
            .width(360.dp),
        contentAlignment = Alignment.TopEnd
    ){
        ProfileImage(
            imageUrl = itemImageUrl,
            contentDescription = "sale_image",
            modifier = Modifier
                .fillMaxSize(),
            clip = RoundedCornerShape(0.dp)
        )
        HashTagButton(
            text = deadlineText,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .padding(top = 20.dp, end = 20.dp),
            isRipple = false,
        )
    }
}