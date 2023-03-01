package com.fruitable.Fruitable.app.presentation.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fruitable.Fruitable.R
import com.fruitable.Fruitable.app.domain.utils.*
import com.fruitable.Fruitable.app.presentation.component.*
import com.fruitable.Fruitable.app.presentation.component._feature.FruitablePopUp
import com.fruitable.Fruitable.app.presentation.component._view.DialogBoxLoading
import com.fruitable.Fruitable.app.presentation.component._view.ResourceImage
import com.fruitable.Fruitable.app.presentation.navigation.Screen
import com.fruitable.Fruitable.app.presentation.viewmodel.sale.SaleDetailViewModel
import com.fruitable.Fruitable.ui.theme.MainGray1
import com.fruitable.Fruitable.ui.theme.MainGreen1
import com.fruitable.Fruitable.ui.theme.MainGreen2
import com.fruitable.Fruitable.ui.theme.TextStyles
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun SaleDetailScreen(
    navController: NavController,
    viewModel: SaleDetailViewModel = hiltViewModel()
) {
    val saleDetail = viewModel.saleDetail.value.saleDetail
    val orderStatus = viewModel.getOrderStatus()
    val isClosed = saleDetail.endDate.dateFormat() <= 0L && !saleDetail.endDate.isNullOrBlank()
    var isDialogOpen by remember { mutableStateOf(false) }

    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val coroutine = rememberCoroutineScope()

    val intent: Intent = when (orderStatus) {
        ORDER_PHONE ->  Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + saleDetail.contact))
        ORDER_URL ->  Intent(Intent.ACTION_VIEW, Uri.parse(saleDetail.contact))
        else -> Intent()
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is SaleDetailViewModel.UiEvent.ErrorEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "🌱 ${event.message}"
                    )
                }
                is SaleDetailViewModel.UiEvent.DeleteSuccess -> {
                    navController.navigate(Screen.SalesScreen.route)
                }
            }
        }
    }
    OrderPopDialog(
        contact = saleDetail.contact,
        text = when (orderStatus) {
            ORDER_PHONE -> "전화 걸기 및 연락처 저장하기"
            ORDER_URL -> "홈페이지 바로가기"
            else -> ""
        },
        contactMethod = {
            try { context.startActivity(intent) }
            catch (e: Exception) { "연결 실패".log() }
        },
        cancelMethod = { isDialogOpen = false },
        copyMethod = {
            clipboardManager.setText(AnnotatedString(saleDetail.contact))
            coroutine.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    "🌱 클립보드 복사 성공"
                )
            }
        },
        isOpen = isDialogOpen
    )
    if (viewModel.isLoading.value || viewModel.saleDetail.value.isLoading) DialogBoxLoading()
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            Column(
                modifier = Modifier.background(Color.White).fillMaxWidth()
            ) {
                FruitableDivider()
                FruitableButton (
                    text = "주문하기",
                    enabled = !isClosed,
                    modifier = Modifier.padding(30.dp, 14.dp, 30.dp, 30.dp),
                    onClick = { if (!isClosed) isDialogOpen = true }
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item { DetailTop(
                isModifiable = viewModel.isModifiable(),
                isClosed = isClosed,
                deleteSale = { viewModel.deleteSale(saleDetail.id) },
                updateSale = { navController.navigate("${Screen.AddSaleScreen.route}?saleId=${saleDetail.id}") },
                itemImageUrl = saleDetail.fileURL
            ) }
            item { DetailFarmProfile(
                isClosed = isClosed,
                nickName = saleDetail.userId.name,
                phoneNum = saleDetail.contact,
                deadLine = saleDetail.endDate
            ) }
            item { FruitableDivider(padding = PaddingValues(horizontal = 28.dp)) }
            item { DetailContent(
                price = saleDetail.price,
                title = saleDetail.title,
                content = saleDetail.content
            ) }
            item { DetailHashTag(tags = saleDetail.tags) }
            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
@Composable
fun OrderPopDialog(
    contact: String = "051-456-45978",
    text: String = "",
    contactMethod: () -> Unit = {},
    cancelMethod: () -> Unit = {},
    copyMethod: () -> Unit = {},
    isOpen: Boolean = false
){
    val dialogModifier = Modifier.padding(15.dp)
    if (isOpen) {
        Dialog(
            onDismissRequest = cancelMethod,
            properties = DialogProperties()
        ){
            Column(
                modifier = dialogModifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.White)
            ){
                Text(
                    text = contact.ifBlank { "연락처 없음" },
                    style = TextStyles.TextBold5,
                    modifier = dialogModifier
                )
                if (text.isNotBlank()) {
                    Text(
                        text = text,
                        style = TextStyles.TextBasic3,
                        modifier = dialogModifier.clickable(onClick = contactMethod)
                    )
                    FruitableDivider()
                }
                Text(
                    text = "클립보드에 복사하기",
                    style = TextStyles.TextBasic3,
                    modifier = dialogModifier.clickable(onClick = copyMethod)
                )
                FruitableDivider()
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "취소",
                    style = TextStyles.TextBasic3,
                    color = MainGray1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .clickable(onClick = cancelMethod),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@Composable
fun DetailHashTag(
    tags: List<String> = listOf("푸릇 농장", "제주 감귤", "귤", "제주도", "세계제일")
) {
    LazyRow(
        modifier = Modifier.padding(start = 22.dp, top = 20.dp, bottom = 19.dp)
    ) {
        item {
            tags.forEach {
                Row {
                    HashTagButton(text = "# $it", enabled = false)
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

@Composable
fun DetailContent(
    price: Int = 26900,
    title: String = "귤탐 당도선별 하우스 감귤 로열과",
    content: String = """ ❗️ 깨끗한 산지에서 재배한 감귤
 ❗ 탱글탱글한 과육과 새콤달콤한 과즙
 ❗ 비타민C, A 등을 함유한 과일
 ❗ 디저트, 주스 등의 요리로 활용"""
) {
    Column(
        modifier = Modifier
            .padding(start = 22.dp, top = 22.dp, bottom = 5.dp, end = 30.dp)
            .fillMaxWidth()
    ){
        Text(
            text = formatAmountOrMessage(price.toString()) +"원",
            style = TextStyles.TextHeavyBold,
        )
        Text( text = title.ifBlank { "제목 없음" }, style = TextStyles.TextBold3)
        Text (
            text = content,
            modifier = Modifier.padding(top = 18.dp, bottom = 5.dp),
            style = TextStyles.TextSmall3
        )
    }
}

@Composable
fun DetailTop(
    isModifiable: Boolean = true,
    isClosed: Boolean = true,
    deleteSale: () -> Unit = {},
    updateSale: () -> Unit = {},
    itemImageUrl: List<String> = (0 until 5).map { sampleUrl }
){
    var isDialogOpen by remember { mutableStateOf(false) }
    FruitablePopUp(
        text = "게시글을 삭제하시겠습니까?",
        cancelText = "취소",
        confirmText = "삭제하기",
        cancel = { isDialogOpen = false },
        confirm = deleteSale,
        isOpen = isDialogOpen
    )
    Box(
        modifier = Modifier.height(300.dp).fillMaxWidth(),
        contentAlignment = TopEnd
    ){
        ImagePager(isClosed, itemImageUrl)
        if (isModifiable) {
            Row(
                modifier = Modifier.padding(top = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ResourceImage(resId = R.drawable.delete, size = 22.dp, boxModifier = Modifier.clickable{ isDialogOpen = true })
                ResourceImage(resId = R.drawable.update, size = 20.dp, boxModifier = Modifier.clickable(onClick = updateSale))
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImagePager(
    isClosed: Boolean = true,
    items: List<String>
) {
    val pagerState = rememberPagerState()
    Box {
        HorizontalPager(count = items.size, state = pagerState) { index ->
            FruitableImage(
                modifier = Modifier.fillMaxSize(),
                imageUrl = items[index],
                clip = RectangleShape,
                alpha = if (isClosed) 0.6f else 1f
            )
        }
        if (items.isEmpty()) FruitableImage(modifier = Modifier.fillMaxSize(), imageUrl = sampleUrl, clip = RectangleShape) // exception case
        if (isClosed) {
            Text(
                text = "마감",
                style = TextStyles.TextBold6,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Center)
            )
        }
        if (items.size >= 2) {
            HorizontalPagerIndicator(
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp),
                pagerState = pagerState,
                pageIndexMapping = {
                    when (it) {
                        0 -> 0
                        items.size - 1 -> 2
                        else -> 1
                    }
                },
                pageCount = if (items.size < 3) items.size else 3,
                spacing = 10.dp,
                indicatorHeight = 8.dp,
                indicatorWidth = 8.dp,
                indicatorShape = CircleShape,
                activeColor = Color.White,
            )
        }
    }
}

@Composable
fun DetailFarmProfile(
    isClosed : Boolean = true,
    nickName : String = "푸릇농장",
    phoneNum : String = "051-456-5978",
    deadLine : String? = "2023.01.25"
){
    Box(
        modifier = Modifier.padding(20.dp, 18.dp).fillMaxWidth()
    ) {
        Row {
            ResourceImage(boxModifier = Modifier.size(56.dp).clip(CircleShape).background(MainGreen2))
            Column(
                modifier = Modifier.padding(start = 9.dp, top = 7.dp)
            ) {
                Text(
                    text = nickName.ifBlank { "알 수 없는 사용자" },
                    style = TextStyles.TextBold2,
                    modifier = Modifier.padding(end = 6.dp)
                )
                Text(text = phoneNum.ifBlank { "연락처 없음" }, style = TextStyles.TextSmall2)
            }
        }
        if (!isClosed && !deadLine.isNullOrBlank()) {
            Box(
                modifier = Modifier.clip(RoundedCornerShape(32.dp))
                    .border(BorderStroke(1.dp, MainGreen1), RoundedCornerShape(32.dp))
                    .padding(12.dp, 5.dp)
                    .align(TopEnd),
                contentAlignment = Center
            ) {
                Text(
                    text = "D-${deadLine.dateFormat()}",
                    style = TextStyles.TextBasic2,
                )
            }
        }
    }

}