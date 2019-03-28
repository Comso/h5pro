package com.cosmo.common.extension

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by liwei on 2018/6/29 18:59
 * Email: liwei@uama.com.cn
 * Description: Calendar 相关的 extension functinos
 */
fun Calendar.format(pattern: String): String =
        SimpleDateFormat(pattern, Locale.getDefault()).format(time)

inline fun Calendar.format(pattern: String, body: Calendar.() -> Unit): String {
    body()
    return format(pattern)
}
