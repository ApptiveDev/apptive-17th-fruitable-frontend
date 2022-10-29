package com.fruitable.Fruitable.app.presentation.view

import android.widget.ImageButton
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fruitable.Fruitable.R
import com.fruitable.Fruitable.app._enums.HashTag
import com.fruitable.Fruitable.app.presentation.component.FruitableDivider
import com.fruitable.Fruitable.app.presentation.component.HashTagButton
import com.fruitable.Fruitable.app.presentation.component.IsFruitButton
import com.fruitable.Fruitable.app.presentation.component.ProfileImage
import com.fruitable.Fruitable.app.presentation.navigation.Screen
import com.fruitable.Fruitable.ui.theme.*

@Composable
fun SalesScreen(
    navController: NavController
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MainGreen1)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    SellerProfile(
                        modifier = Modifier
                            .align(End)
                            .padding(0.dp, 47.dp, 30.dp, 19.dp)
                    )
                }
            }
            item {
                SalesContents(
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp))
                        .background(White)
                        .fillMaxSize(),
                    navController = navController
                )
            }
        }
       it
    }
}

@Composable
fun SellerProfile(
    modifier: Modifier = Modifier,
    nickname: String = "푸릇농장",
    imageUrl: String = "https://watermark.lovepik.com/photo/20211208/large/lovepik-the-image-of-a-farmer-doing-cheering-picture_501693759.jpg"
){
    Row(
      modifier = modifier.height(46.dp),
      verticalAlignment = CenterVertically
    ){
        Text(
            text = nickname,
            style = TextStyles.TextProfile1,
            color = White,
            modifier = Modifier.padding(end = 7.dp)
        )
        ProfileImage(
            imageUrl = imageUrl,
            contentDescription = "profile image",
            modifier = Modifier.size(46.dp)
        )
    }
}
@Composable
fun SalesContents(
    navController: NavController,
    modifier: Modifier = Modifier
){
    var selectedItem by remember { mutableStateOf("all") }
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
        Column(
            verticalArrangement = Arrangement.spacedBy(17.dp),
            modifier = Modifier.padding(23.dp, 37.dp, 37.dp, 21.dp)
        ){
           for(i in 1..10) {
                SaleItem(onClick = {
                    navController.navigate(Screen.DetailSalesScreen.route+"/itemId")
                })
               FruitableDivider()
            }
        }
    }
}

@Preview
@Composable
fun SalesPreview(){
    SalesScreen(rememberNavController())
}