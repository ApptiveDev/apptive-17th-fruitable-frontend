package com.fruitable.Fruitable.app.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fruitable.Fruitable.app._enums.HashTag
import com.fruitable.Fruitable.app.presentation.component.HashTagButton
import com.fruitable.Fruitable.app.presentation.component.ProfileImage
import com.fruitable.Fruitable.ui.theme.MainGray1
import com.fruitable.Fruitable.ui.theme.MainGray2
import com.fruitable.Fruitable.ui.theme.MainGreen1
import com.fruitable.Fruitable.ui.theme.TextStyles

@Composable
fun SalesScreen(
    navController: NavController
){
    val verticalScroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainGreen1)
    ){
        SellerProfile(
            modifier = Modifier
                .align(End)
                .padding(0.dp, 47.dp, 30.dp, 19.dp)
        )
        SalesContents(
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp))
                .background(White)
                .fillMaxSize()
        )
      //  HashTagButton(isSelected = false)
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
      modifier = modifier
          .width(113.dp)
          .height(46.dp)
    ){
        Text(
            text = nickname,
            style = TextStyles.TextProfile1,
            color = White,
            modifier = Modifier
                .align(CenterStart)
                .padding(bottom = 3.dp)
        )
        ProfileImage(
            imageUrl = imageUrl,
            contentDescription = "profile image",
            modifier = Modifier
                .align(CenterEnd)
                .size(46.dp)
        )
    }
}
@Composable
fun SalesContents(
    modifier: Modifier = Modifier
){
    var selectedItem by remember { mutableStateOf("") }
    Column(
        modifier = modifier
    ){
        IsFruitButton(
            modifier = Modifier
                .padding(30.dp, 33.dp)
                .fillMaxWidth()
                .height(40.dp),
        )
        Text(
            text = "인기 해시태그",
            style = TextStyles.TextProfile1,
            color = Black,
            modifier = Modifier.padding(start = 21.dp)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(start = 21.dp, top = 13.dp),
        ) {
            item {
                HashTag::class.sealedSubclasses.mapNotNull { it.objectInstance }.forEach {
                    Row {
                        HashTagButton(
                            text = it.name,
                            isSelected = selectedItem == it.tag,
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .selectable(
                                    selected = selectedItem == it.tag,
                                    onClick = { selectedItem = it.tag }
                                ),
                            onClick = { selectedItem = it.tag }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun IsFruitButton(
    modifier: Modifier = Modifier,
){
    var isSelected = remember { mutableStateOf(true)}

    val buttonAlign     = if (isSelected.value) CenterStart else CenterEnd
    val textAlign       = if (isSelected.value) CenterEnd else CenterStart
    val selectedText    = if (isSelected.value) "과일" else "채소"
    val nonSelectedText = if (isSelected.value) "채소" else "과일"

    Box(
        modifier = modifier,
        contentAlignment = Center,
    ){
        HashTagButton(
            text = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            isSelected = false,
            isRipple = false,
            onClick = { isSelected.value = !isSelected.value }
        )
        HashTagButton(
            text = selectedText,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(40.dp)
                .align(buttonAlign),
            isSelected = true,
            style = TextStyles.TextBasic1
        )
        Text(
            text = nonSelectedText,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(textAlign),
            textAlign = TextAlign.Center,
            style = TextStyles.TextBasic1,
            color = MainGray2
        )
    }
}

@Preview
@Composable
fun SalesPreview(){
    SalesScreen(rememberNavController())
}