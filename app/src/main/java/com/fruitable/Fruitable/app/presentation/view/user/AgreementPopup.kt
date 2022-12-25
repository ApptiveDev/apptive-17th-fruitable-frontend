package com.fruitable.Fruitable.app.presentation.view.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.fruitable.Fruitable.R
import com.fruitable.Fruitable.app.presentation.component.FruitableButton
import com.fruitable.Fruitable.app.presentation.component.FruitableDivider
import com.fruitable.Fruitable.app.presentation.data.agreeTitle
import com.fruitable.Fruitable.app.presentation.data.infoAgree
import com.fruitable.Fruitable.ui.theme.MainGreen1
import com.fruitable.Fruitable.ui.theme.TextStyles

@Composable
fun AgreementPopUp(
    isOpen: Boolean = false,
    category: Boolean = false,
    onDismiss: () -> Unit = {},
    onAgree: () -> Unit = {}
) {
    if (isOpen) {
        Popup {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.spacedBy(30.dp),
                horizontalAlignment = CenterHorizontally
            ) {
                item {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.cancel_thin),
                            contentDescription = "cancel",
                            modifier = Modifier
                                .padding(0.dp, 48.dp, 30.dp, 0.dp)
                                .clickable(onClick = onDismiss)
                                .size(30.dp)
                                .align(End)
                        )
                    }
                }
                item { FruitableDivider() }
                item {
                    Text(
                        text = if (category) "이용약관" else "개인정보 수집 · 이용 동의서",
                        style = TextStyles.TextHeavyBold2,
                    )
                }
                item { if (category) UtilPopUp() else InfoPopUp() }
                item {
                    FruitableButton(
                        text = "동의하기",
                        color = MainGreen1,
                        textColor = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 50.dp)
                            .height(44.dp),
                        onClick = {
                            onDismiss()
                            onAgree()
                        },
                        cornerShape = 0.dp,
                    )
                }
            }
        }
    }
}
@Composable
fun UtilPopUp(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.padding(start = 30.dp, end = 25.dp),
    ) {
        agreeTitle.forEach {
            Box(
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = it.key,
                    style = TextStyles.TextBold2,
                    modifier = Modifier.align(Center),
                    textAlign = TextAlign.Center
                )
            }
            it.value.forEach {
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                            append(it.key)
                        }
                        append(it.value)
                    },
                    style = TextStyles.TextBasic2
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
@Composable
fun InfoPopUp(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.padding(start = 30.dp, end = 25.dp),
    ) {
        infoAgree.forEach {
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                        append(it.key)
                    }
                    append(it.value)
                },
                style = TextStyles.TextBasic2
            )
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}