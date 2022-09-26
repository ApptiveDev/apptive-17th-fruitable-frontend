package com.fruitable.Fruitable.app.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fruitable.Fruitable.app.presentation.component.ProfileImage
import com.fruitable.Fruitable.ui.theme.MainGreen1
import com.fruitable.Fruitable.ui.theme.TextStyles

@Composable
fun SalesScreen(
    navController: NavController
){
    Column(
        modifier = Modifier.background(MainGreen1).fillMaxSize()
    ){
        SellerProfile()
    }
}

@Composable
fun SellerProfile(
    modifier: Modifier = Modifier,
    nickname: String = "푸릇농장",
    text: String = "인증됨ㆍ게시글 10개",
    imageUrl: String = "https://watermark.lovepik.com/photo/20211208/large/lovepik-the-image-of-a-farmer-doing-cheering-picture_501693759.jpg"
){
    Box(
      modifier = modifier.width(161.dp).height(46.dp)
    ){
        Column(
            modifier = Modifier.align(CenterStart)
        ){
            Text(
                text = nickname,
                style = TextStyles.TextProfile1,
                color = Color.White,
                modifier = Modifier.align(End).padding(bottom=3.dp)
            )
            Text(
                text = text,
                style = TextStyles.TextProfile2,
                color = Color.White,
            )
        }
        ProfileImage(
            imageUrl = imageUrl,
            contentDescription = "profile image",
            modifier = Modifier.align(CenterEnd).size(46.dp)
        )
    }

}

@Preview
@Composable
fun SalesPreview(){
    SellerProfile()
}