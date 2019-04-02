package com.uama.app.entity

import java.io.Serializable

/**
 *Author:ruchao.jiang
 *Created: 2019/3/29 14:46
 *Email:ruchao.jiang@uama.com.cn
 */

data class DialBean(var phone:String?,var text:String?)

data class ScanBean(var content:String?)

data class PickBean(var type:Int?,var maxCount:Int?,var cbName:String?)

data class UploadPicture(var tempFilePaths:MutableList<String>)

data class PreViewBean(var currentIndex:Int?,var picList:MutableList<String>?): Serializable