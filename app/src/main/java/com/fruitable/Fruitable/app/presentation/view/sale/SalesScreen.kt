package com.fruitable.Fruitable.app.presentation.view

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fruitable.Fruitable.R
import com.fruitable.Fruitable.app.data.network.dto.sale.SaleResponseDTO
import com.fruitable.Fruitable.app.presentation.component.FruitableDivider
import com.fruitable.Fruitable.app.presentation.component.HashTagButton
import com.fruitable.Fruitable.app.presentation.component._view.ResourceImage
import com.fruitable.Fruitable.app.presentation.navigation.Screen
import com.fruitable.Fruitable.app.presentation.viewmodel.sale.SalesViewModel
import com.fruitable.Fruitable.ui.theme.*
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun SalesScreen(
    navController: NavController,
    viewModel: SalesViewModel = hiltViewModel()
){
    var isFruitCheck by rememberSaveable { mutableStateOf(true)  }
    val scaffoldState = rememberScaffoldState()
    val isRefresh by viewModel.isRefresh.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefresh)
    val isSeller = viewModel.isSeller()

    LaunchedEffect(key1 = isRefresh) {
        if (viewModel.sales.value.error.isNotBlank())
            scaffoldState.snackbarHostState.showSnackbar(
                message = "🌱 ${viewModel.sales.value.error}"
            )
    }
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            if (isSeller) {
                Button(
                    onClick = { navController.navigate(Screen.AddSaleScreen.route) },
                    modifier = Modifier
                        .padding(9.dp)
                        .size(56.dp)
                        .shadow(5.dp, shape = CircleShape)
                        .clip(CircleShape),
                    colors = ButtonDefaults.buttonColors(MainGreen1),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    ResourceImage(resId = R.drawable.plusbtn, size = 24.dp)
                }
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
            isFruitCheck = IsFruitTab()
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { viewModel.refreshGetSales() },
                indicator = { state, refreshTrigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = refreshTrigger,
                        backgroundColor = Color.White,
                        contentColor = MainGreen1
                    )
                }
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        SalesContents(
                            modifier = Modifier.padding(top = 33.dp).fillMaxSize(),
                            navController = navController,
                            isFruitCheck = isFruitCheck,
                            salesDTO = viewModel.sales.value.salesDTO
                        )
                    }
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
){
    Box(
        modifier = modifier.height(54.dp).fillMaxWidth(),
        contentAlignment = CenterStart
    ) {
        Row {
            ResourceImage(boxModifier = Modifier.size(54.dp).clip(CircleShape).background(MainGreen2))
            Text(
                text = nickname,
                style = TextStyles.TextBold2,
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
            ResourceImage(
                resId = R.drawable.setting,
                size = 22.dp,
                boxModifier = Modifier.clickable(onClick = settingButton).align(CenterVertically)
            )
        }
    }
}
@Composable
fun IsFruitTab(): Boolean {
    var isFruitClick by rememberSaveable { mutableStateOf(true) }

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
    return isFruitClick
}
@Composable
fun SalesContents(
    navController: NavController,
    isFruitCheck: Boolean = true,
    salesDTO: List<SaleResponseDTO> = emptyList(),
    modifier: Modifier = Modifier
){
    var selectedItem by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(key1 = isFruitCheck) { selectedItem = "" }
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
                Row {
                    HashTagButton(
                        text = "# 전체",
                        isSelected = selectedItem == "",
                        modifier = Modifier.selectable(
                            selected = selectedItem == "",
                            onClick = { selectedItem = "" }),
                        onClick = { selectedItem = ""}
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
            item {
                val tags = mutableListOf<String>()
                salesDTO.filter{ it.vege == (if(isFruitCheck) 0 else 1) }.forEach { it.tags.forEach { tags.add(it) }}
                tags.distinct().asReversed().forEach {
                     Row {
                        HashTagButton(
                            text = "# $it",
                            isSelected = selectedItem == it,
                            modifier = Modifier.selectable(
                                selected = selectedItem == it,
                                onClick = { selectedItem = it }),
                            onClick = { selectedItem = it }
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
            salesDTO.filter { it.vege == (if(isFruitCheck) 0 else 1)
                    && (it.tags.contains(selectedItem) || selectedItem == "") }.asReversed().forEach {
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
