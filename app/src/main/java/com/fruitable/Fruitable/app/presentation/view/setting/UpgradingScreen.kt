package com.fruitable.Fruitable.app.presentation.view.setting

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fruitable.Fruitable.app.presentation.component.FruitableTitle
import com.fruitable.Fruitable.ui.theme.MainGreen1
import com.fruitable.Fruitable.ui.theme.TextStyles

@Composable
fun UpgradingScreen(
    navController: NavController
){
    FruitableTitle(
        title = "판매자 되기",
        subtitle = "푸릇에이블의 판매자가 되어보세요 !"
    ){
        Text(
            text = "판매자 되기 안내",
            style = TextStyles.TextBold6
        )
        Spacer(modifier = Modifier.height(16.dp))
        NoticeText()
        Text(
            text = "emailauthentication@naver.com",
            style = TextStyles.TextBasic3,
            modifier = Modifier.padding(vertical = 30.dp)
        )
        Text(
            text = "최대한 빠른 시일 내에 처리해드리겠습니다.\n" +
                    "항상 푸릇에이블을 이용해주셔서 감사합니다.",
            style = TextStyles.TextSmall2
        )
    }
}

@Composable
fun NoticeText(){
    Column {
        Text(
            text = "판매자가 되면 판매글 작성이 가능합니다.\n\n" +
                    "판매자 인증을 원하신다면 인증을 위해",
            style = TextStyles.TextSmall2
        )
        Row {
            Text(
                text = "사업자등록증",
                style = TextStyles.TextBold1,
                color = MainGreen1
            )
            Text(
                text = "과 ",
                style = TextStyles.TextSmall2
            )
            Text(
                text = "통신판매업 신고서",
                style = TextStyles.TextBold1,
                color = MainGreen1
            )
            Text(
                text = "를",
                style = TextStyles.TextSmall2
            )
        }
        Text(
            text = "아래 이메일로 보내주세요 !",
            style = TextStyles.TextSmall2
        )
    }
}