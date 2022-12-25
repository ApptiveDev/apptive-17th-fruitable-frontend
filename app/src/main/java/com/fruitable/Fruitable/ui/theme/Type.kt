package com.fruitable.Fruitable.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
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
    /**
     * Regular(Normal)
     */
    val TextSmall1 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 14.06.sp
    )
    val TextSmall2 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 16.41.sp,
        color = Color.Black
    )
    val TextSmall3 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 19.sp,
        color = Color.Black
    )

    /**
     * Medium
     */
    val TextBasic1 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 14.06.sp
    )
    val TextBasic2 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        color = Color.Black
    )
    val TextBasic3 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 19.sp,
        color = Color.Black
    )
    /**
     * SemiBold
     */
    val TextBold1 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        color = Color.Black
    )
    val TextBold2 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 18.75.sp,
        color = Color.Black
    )
    val TextBold3 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        lineHeight = 19.sp
    )
    val TextBold4 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 28.13.sp,
    )
    val TextBold5 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 23.sp,
        color = Color.Black
    )
    val TextBold6 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 23.sp,
        color = Color.Black
    )
    /**
     * Bold
     */
    val TextHeavyBold = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 26.sp
    )
    val TextHeavyBold2 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 26.sp,
        color = Color.Black
    )
}
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)