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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fruitable.Fruitable.app._enums.HashTag
import com.fruitable.Fruitable.app.presentation.component.*
import com.fruitable.Fruitable.app.presentation.event.LeaveAppEvent
import com.fruitable.Fruitable.app.presentation.event.UserInfoUpdateEvent
import com.fruitable.Fruitable.app.presentation.navigation.Screen
import com.fruitable.Fruitable.ui.theme.MainGreen1
import com.fruitable.Fruitable.ui.theme.TextStyles

@Composable
fun DetailSalesScreen(
    navController: NavController,
    itemId : Int = 1,
    //harcoding
    itemImageUrl: String = "https://images-prod.healthline.com/hlcmsresource/images/AN_images/health-benefits-of-apples-1296x728-feature.jpg",
    title: String = "프레샤인 충주 GAP 인증 당도선별 사과",
    deadline: Int = 30,
    price: Int = 14500,
    nickName : String = "푸릇농장",
    phoneNum : String = "051-123-4567",
    imageUrl : String = "https://watermark.lovepik.com/photo/20211208/large/lovepik-the-image-of-a-farmer-doing-cheering-picture_501693759.jpg",
) {
    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
            ) {
                FruitableDivider()
                FruitableButton(
                    text = "주문하기",
                    color = MainGreen1,
                    textColor = Color.White,
                    modifier = Modifier
                        .padding(30.dp, 14.dp, 30.dp, 30.dp)
                        .fillMaxWidth()
                        .height(44.dp),
                    onClick = { navController.navigate(Screen.SalesScreen.route) }
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                DetailTop(deadline = deadline, itemImageUrl = itemImageUrl)
            }
            item {
                DetailFarmProfile(nickName = nickName, phoneNum = phoneNum, imageUrl = imageUrl)
            }
            item {
                DetailTitle(title, price)
            }
            item {
                DetailExplain(itemId = itemId)
            }
            item {
                LazyRow(
                    modifier = Modifier
                        .padding(start = 22.dp, top = 20.dp, bottom = 19.dp)
                ) {
                    item {
                        //itemId별로 태그가 다를테니까 이거 서버 받으면 바꾸기
                        HashTag::class.sealedSubclasses.mapNotNull { it.objectInstance }.forEach {
                            Row {
                                HashTagButton(
                                    text = it.name,
                                    isRipple = false,
                                )
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
    deadline: Int,
    itemImageUrl: String
){
    val deadlineText:String;
    if(deadline > 0) deadlineText = "D-" + deadline.toString()
    else deadlineText = "마감"

    Box(
        modifier = Modifier.height(300.dp),
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
            style = TextStyles.TextSmall3,
            modifier = Modifier
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
                    style = TextStyles.TextBold2,
                    color = Color.Black,
                    modifier = Modifier.padding(end = 6.dp)
                )
                Text(
                    text = phoneNum,
                    style = TextStyles.TextSmall2,
                    color = Color.Black,
                )
            }
        }
        FruitableDivider(modifier = Modifier.padding(28.dp,0.dp))
    }
}


@Composable
fun DetailTitle(
    title : String,
    price : Int,
){
    Column(
        modifier = Modifier
            .padding(start = 22.dp, top = 22.dp, bottom = 5.dp)
            .fillMaxWidth()
    ){
        Text(
            text = formatAmountOrMessage(price.toString()) +"원",
            style = TextStyles.TextHeavyBold,
            color = Color.Black,
        )
        Text(
            text = title,
            style = TextStyles.TextBold3,
            color = Color.Black,
        )
    }
}

//여기는 나중에 데이터 받으면 받아와지는 형식에 따라 다르게 바꾸기,,
@Composable
fun DetailExplain(
    itemId : Int = 1
){
    val text = """❗️ 깨끗한 산지에서 재배한 감귤
❗️ 탱글탱글한 과육과 새콤달콤한 과즙
❗  비타민C, A 등을 함유한 과일
❗  디저트, 주스 등의 요리로 활용""".trimIndent()

    Text(
        text = text,
        color = Color.Black,
        modifier = Modifier.padding(20.dp, 18.dp, 0.dp, 5.dp),
        style = TextStyles.TextSmall3
    )

}