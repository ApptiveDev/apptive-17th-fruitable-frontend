package com.fruitable.Fruitable.app.presentation.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fruitable.Fruitable.R
import com.fruitable.Fruitable.app._enums.HashTag
import com.fruitable.Fruitable.app.domain.utils.log
import com.fruitable.Fruitable.app.presentation.component.FruitableDivider
import com.fruitable.Fruitable.app.presentation.component.HashTagButton
import com.fruitable.Fruitable.app.presentation.component.ProfileImage
import com.fruitable.Fruitable.app.presentation.navigation.Screen
import com.fruitable.Fruitable.app.presentation.viewmodel.sale.SalesViewModel
import com.fruitable.Fruitable.ui.theme.*

@Composable
fun SalesScreen(
    navController: NavController,
    viewModel: SalesViewModel = hiltViewModel()
){
    Scaffold(
        floatingActionButton = {
            Button(
                onClick = {navController.navigate(Screen.AddSaleScreen.route)},
                modifier = Modifier
                    .padding(9.dp)
                    .size(56.dp)
                    .shadow(5.dp, shape = CircleShape)
                    .clip(CircleShape),
                colors = ButtonDefaults.buttonColors(MainGreen1),
                contentPadding = PaddingValues(0.dp)
            ){
                Image(
                    painterResource(id = R.drawable.plusbtn),
                    contentDescription = "add_post",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            SellerProfile(
                modifier = Modifier.padding(30.dp, 48.dp, 30.dp, 49.dp),
                nickname = viewModel.name.value.ifBlank { "사용자" },
                updateButton = { navController.navigate(Screen.UserInfoUpdateScreen.route) },
                settingButton = { navController.navigate(Screen.SettingScreen.route) }
            )
            IsFruitTab()
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    SalesContents(
                        modifier = Modifier.padding(top = 33.dp).fillMaxSize(),
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun SellerProfile(
    modifier: Modifier = Modifier,
    updateButton: () -> Unit = {},
    settingButton: () -> Unit = {},
    nickname: String = "홍길동",
    imageUrl: String = "https://watermark.lovepik.com/photo/20211208/large/lovepik-the-image-of-a-farmer-doing-cheering-picture_501693759.jpg"
){
    Box(
        modifier = modifier.height(54.dp).fillMaxWidth(),
        contentAlignment = CenterStart
    ) {
        Row {
            ProfileImage(
                imageUrl = imageUrl,
                contentDescription = "profile image",
                modifier = Modifier.size(54.dp)
            )
            Text(
                text = nickname,
                style = TextStyles.TextBasic2,
                modifier = Modifier.padding(start = 10.dp).align(CenterVertically),
            )
        }
        Row(modifier = Modifier.align(BottomEnd)) {
            Box(
                modifier = Modifier
                    .size(90.dp, 32.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(MainGreen2)
                    .clickable(onClick = updateButton),
                contentAlignment = Center
            ) {
                Text(
                    text = "프로필 수정",
                    style = TextStyles.TextSmall1,
                    color = MainGray7,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Image(
                painter = painterResource(id = R.drawable.setting),
                contentDescription = "setting button",
                modifier = Modifier.size(22.dp).clickable(onClick = settingButton).align(CenterVertically)
            )
        }
    }
}
@Composable
fun IsFruitTab(){
    var isFruitClick by remember { mutableStateOf(true) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = CenterVertically
    ){
        Column(
           modifier = Modifier.clickable { isFruitClick = true }.weight(1f),
           horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "과일",
                style = TextStyles.TextBasic3,
                color = if (isFruitClick) MainGreen1 else MainGray2,
            )
            Divider(
                modifier = Modifier.padding(top = 14.dp).fillMaxWidth(),
                color = if (isFruitClick) MainGreen1 else MainGray4,
                thickness = if (isFruitClick) 3.dp else 1.dp
            )
        }
        Column(
            modifier = Modifier.clickable { isFruitClick = false }.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "채소",
                style = TextStyles.TextBasic3,
                color = if (isFruitClick) MainGray2 else MainGreen1,
            )
            Divider(
                modifier = Modifier.padding(top = 14.dp).fillMaxWidth(),
                color = if (isFruitClick) MainGray4 else MainGreen1,
                thickness = if (isFruitClick) 1.dp else 3.dp,
            )
        }
    }
}
@Composable
fun SalesContents(
    navController: NavController,
    viewModel: SalesViewModel,
    modifier: Modifier = Modifier
){
    var selectedItem by remember { mutableStateOf("all") }
    Column(modifier = modifier){
        Text(
            text = "인기 해시태그",
            style = TextStyles.TextBold2,
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
                            modifier = Modifier.selectable(
                                    selected = selectedItem == it.tag,
                                    onClick = { selectedItem = it.tag }),
                            onClick = { selectedItem = it.tag }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(17.dp),
            modifier = Modifier.padding(23.dp, 37.dp, 37.dp, 21.dp)
        ) {
            viewModel.sales.value.salesDTO.forEach {
                SaleItem(
                    itemImageUrl = it.fileURL[0],
                    title = it.title,
                    nickname = it.userId.name,
                    price = it.price,
                    deadline = it.endDate,
                    onClick = { navController.navigate("${Screen.SaleDetailScreen.route}/${it.id}") }
                )
                FruitableDivider()
            }
        }
    }
}
