package com.fruitable.Fruitable.app.presentation.component._view

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fruitable.Fruitable.R

@Composable
fun ResourceImage(
    @DrawableRes resId: Int = R.drawable.logo,
    boxModifier: Modifier = Modifier,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    isVisible: Boolean = true,
    size: Dp = 35.dp
) {
    Box(
        modifier = boxModifier,
        contentAlignment = contentAlignment
    ) {
        if (isVisible) {
            Image(
                painter = painterResource(resId),
                contentDescription = stringResource(id = resId),
                modifier = modifier.size(size)
            )
        }
    }
}