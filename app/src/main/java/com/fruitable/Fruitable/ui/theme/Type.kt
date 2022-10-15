package com.fruitable.Fruitable.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.fruitable.Fruitable.R

private val Roboto = FontFamily(
    Font(R.font.robotoblack, FontWeight.Black), // 900
    Font(R.font.robotobold, FontWeight.Bold), // 700
    Font(R.font.robotolight, FontWeight.Light), //300
    Font(R.font.robotomedium, FontWeight.Medium), // 500
    Font(R.font.robotoregular, FontWeight.Normal), // 400
    Font(R.font.robotothin, FontWeight.Thin) // 100
)

object TextStyles{
    val TextProfile1 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 18.75.sp
    )
    val TextProfile2 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 14.06.sp
    )
    val TextBasic1 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 16.41.sp
    )
    val TextBasic2 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 18.75.sp
    )
    val TextDetailProfile1 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        lineHeight = 16.sp
    )
    val TextDetailTitle1 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.W700,
        fontSize = 22.sp,
        lineHeight = 26.sp
    )
    val TextDetailTitle2 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.W600,
        fontSize = 17.sp,
        lineHeight = 19.sp
    )
}
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)