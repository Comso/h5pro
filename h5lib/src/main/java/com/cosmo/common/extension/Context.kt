package com.cosmo.common.extension

import android.content.Context
import com.cosmo.common.utils.ToastUtil

fun Context.dimensionPixel(id: Int) = resources.getDimensionPixelSize(id)

val Context.widthPixels get() = resources.displayMetrics.widthPixels

fun Context.showToast(text:String){
    ToastUtil.show(this.applicationContext,text)
}


