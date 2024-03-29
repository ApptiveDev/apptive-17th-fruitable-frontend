package com.fruitable.Fruitable.app.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.fruitable.Fruitable.app.domain.utils.dateFormat
import com.fruitable.Fruitable.app.domain.utils.sampleUrl
import com.fruitable.Fruitable.app.presentation.component.FruitableImage
import com.fruitable.Fruitable.app.presentation.component.formatAmountOrMessage
import com.fruitable.Fruitable.ui.theme.MainGray1
import com.fruitable.Fruitable.ui.theme.TextStyles

@Composable
fun SaleItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    itemImageUrl: String = sampleUrl,
    title: String = "프레샤인 충주 GAP 인증\n당도선별 사과",
    nickname: String = "푸릇농장",
    deadline: String? = "2020.01.23",
    price: Int = 14500
){
    val deadlineFormat = if (deadline.dateFormat() > 0) "${deadline.dateFormat()}일 전" else if (deadline.isNullOrBlank()) "없음" else "완료"
    Row(
        modifier = modifier.clickable(onClick = onClick)
    ){
        FruitableImage(
            imageUrl = itemImageUrl,
            contentDescription = "sale_image",
            modifier = Modifier.size(96.dp),
            clip = RoundedCornerShape(10.dp)
        )
        Column(
            modifier = Modifier.padding(start = 19.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ){
            Text(
                text = title,
                style = TextStyles.TextSmall3,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
            )
            Text(
                text = nickname + "ㆍ마감 " + deadlineFormat,
                style = TextStyles.TextSmall1,
                color = MainGray1,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            Text(
                text = formatAmountOrMessage(price.toString()) + "원",
                style = TextStyles.TextBold2
            )
        }
    }
}
