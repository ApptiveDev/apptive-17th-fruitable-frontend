package com.fruitable.Fruitable.app.domain.utils

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.*

fun Modifier.addFocusCleaner(focusManager: FocusManager, doOnClear: () -> Unit = {}): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            doOnClear()
            focusManager.clearFocus()
        })
    }
}

fun String.dateFormat(): Long {
    val dateFormat = SimpleDateFormat("yyyy.MM.dd")
    val endDate = dateFormat.parse(this)?.time
    val today = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time.time
    return (endDate?.minus(today))?.div((24 * 60 * 60 * 1000)) ?: 0
}

fun Long.timerFormat(): String {
    val decimalFormat = DecimalFormat("00")
    val hour = (this/1000) / 60
    val minute = (this/1000) % 60
    return decimalFormat.format(hour) + ":" + decimalFormat.format(minute)
}

const val sampleUrl = "https://media.istockphoto.com/id/1357365823/vector/default-image-icon-vector-missing-picture-page-for-website-design-or-mobile-app-no-photo.jpg?s=612x612&w=0&k=20&c=PM_optEhHBTZkuJQLlCjLz-v3zzxp-1mpNQZsdjrbns="
const val ORDER_PHONE = 0
const val ORDER_URL = 1

const val EMAIL_INPUT = 0
const val EMAIL_INPUT_SUCCESS = 1
const val EMAIL_SEND_CERTIFICATION = 2
const val EMAIL_CERTIFICATION_INPUT = 3
const val EMAIL_CERTIFICATION_INPUT_SUCCESS = 4