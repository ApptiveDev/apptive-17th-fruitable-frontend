package com.fruitable.Fruitable.app.data.storage

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CookieStorage @Inject constructor(@ApplicationContext val context: Context) {
    val Cookie = context.getSharedPreferences("cookie", Context.MODE_PRIVATE)
    fun setCookie(key: String, value: String) {
        Cookie.edit().putString(key, value).apply()
    }
    fun getCookie(key: String): String {
        return Cookie.getString(key, "").toString()
    }
}