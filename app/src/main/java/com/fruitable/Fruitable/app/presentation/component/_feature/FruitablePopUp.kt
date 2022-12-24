package com.fruitable.Fruitable.app.presentation.component._feature

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.fruitable.Fruitable.ui.theme.MainGreen1
import com.fruitable.Fruitable.ui.theme.TextStyles

@Composable
fun FruitablePopUp(
    text: String = "",
    cancelText: String = "닫기",
    confirmText: String = "홈으로",
    cancel: () -> Unit = {},
    confirm: () -> Unit = {},
    isOpen: Boolean = false
){
    var openDialog = isOpen
    if (openDialog) {
        Dialog(
            onDismissRequest = { openDialog = false },
            properties = DialogProperties()
        ){
            Column(
                modifier = Modifier
                    .size(300.dp, 125.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(White),
            ){
                Text(
                    text = text,
                    style = TextStyles.signTitle2,
                    color = Black,
                    modifier = Modifier.padding(30.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(
                        text = cancelText,
                        style = TextStyles.popUpText,
                        color = Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f).clickable(onClick = cancel)
                    )
                    Text(
                        text = confirmText,
                        style = TextStyles.popUpText,
                        color = MainGreen1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f).clickable(onClick = confirm)
                    )
                }
            }
        }
    }
}