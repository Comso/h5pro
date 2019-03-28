package com.uama.app.utils

import java.lang.StringBuilder

data class NetStatus(var netType:Int,var downLoadSpeed:Long =0L ,var uploadSpeed:Long = 0L)

data class PhoneBook(var name:String?,var phoneList:MutableList<String>?)

fun MutableList<String>?.getPhoneNumber():String{
    val sb = StringBuilder()
    if(!this.isNullOrEmpty()){
        forEach {
            sb.append(it )
        }
    }
    return sb.toString()
}