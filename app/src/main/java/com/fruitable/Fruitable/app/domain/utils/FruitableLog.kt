package com.fruitable.Fruitable.app.domain.utils

import android.util.Log

fun String.log(header:String = "FruitableLog"){
    Log.d(header, this)
}