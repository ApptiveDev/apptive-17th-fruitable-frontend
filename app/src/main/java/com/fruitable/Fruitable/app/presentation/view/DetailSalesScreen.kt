package com.fruitable.Fruitable.app.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fruitable.Fruitable.app._enums.HashTag
import com.fruitable.Fruitable.app.presentation.component.HashTagButton
import com.fruitable.Fruitable.app.presentation.component.ProfileImage
import com.fruitable.Fruitable.ui.theme.MainGray3
import com.fruitable.Fruitable.ui.theme.TextStyles
import java.text.DecimalFormat

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
        item{
            DetailFarmProfile(nickName = nickName, phoneNum = phoneNum, imageUrl = imageUrl)
        }
        item{
            DetailTitle(title,price)
        }
        item{
            DetailExplain(itemId = itemId)
        }
        item{
            LazyRow(
                modifier = Modifier
                    .padding(start = 22.dp , top = 20.dp , bottom = 19.dp)
            ){
                item {
                    //itemId별로 태그가 다를테니까 이거 서버 받으면 바꾸기
                    HashTag::class.sealedSubclasses.mapNotNull { it.objectInstance }.forEach {
                        Row {
                            HashTagButton(
                                text = it.name,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp)),
                                isRipple = false,
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                }
            }
            FruitableDivider()
        }
        item{
            DetailBuyBtn(navController = navController,deadline = deadline)
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


//여기에 들어가는 image가 진짜 seller이미지고 메인에 있는 sellerProfile은 우리 로고가 아닌지 물어보깅
@Composable
fun DetailFarmProfile(
    nickName : String,
    phoneNum : String,
    imageUrl : String,
){
    Column(

    ) {
        Row (
            modifier = Modifier
                .padding(start = 20.dp,top = 18.dp , bottom=18.dp)
        ){
            ProfileImage(
                imageUrl = imageUrl,
                modifier = Modifier
                    .size(56.dp)
            )
            Column(
                modifier = Modifier.padding(start = 9.dp,top=7.dp),
            ) {
                Text(
                    text = nickName,
                    style = TextStyles.TextBasic1,
                    color = Color.Black,
                    modifier = Modifier.padding(end = 6.dp)
                )
                Text(
                    text = phoneNum,
                    style = TextStyles.TextDetailProfile1,
                    color = Color.Black,
                )
            }
        }
        FruitableDivider()
    }
}


@Composable
fun DetailTitle(
    title : String,
    price : Int,
){
    val pattern = DecimalFormat("#,###")
    Column(
        modifier = Modifier
            .padding(start = 22.dp, top = 22.dp, bottom=5.dp)
            .fillMaxWidth()
    ){
        Text(
            text = pattern.format(price) +"원",
            style = TextStyles.TextDetailTitle1,
            color = Color.Black,
        )
        Text(
            text = title,
            style = TextStyles.TextDetailTitle2,
            color = Color.Black,
        )
    }
}

//여기는 나중에 데이터 받으면 받아와지는 형식에 따라 다르게 바꾸기,,
@Composable
fun DetailExplain(
    itemId : String
){
    Column(
        modifier = Modifier
            .padding(start = 20.dp,top= 18.dp)
    ){
        for (i in 1..7){
            Text(
                text = "맛있는 사과 아침에 먹어야 더 맛있는 사과",
                color = Color.Black,
                modifier = Modifier
                    .padding(bottom=5.dp)
            )
        }
    }
}


@Composable
fun DetailBuyBtn(
    navController: NavController,
    deadline : Int
){
    var boolDeadline : Boolean = true
    if (deadline <= 0) {
        boolDeadline = false
    }
    //나중에 onclick으로 navigator 넣어야지,,,
    HashTagButton(
        text = "주문하기",
        isSelected = boolDeadline,
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .clip(RoundedCornerShape(10.dp))
            .padding(30.dp,24.dp),
        isRipple = boolDeadline,
    )
}