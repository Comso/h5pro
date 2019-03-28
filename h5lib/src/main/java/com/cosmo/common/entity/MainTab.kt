package com.cosmo.common.entity

import com.flyco.tablayout.listener.CustomTabEntity

/**
 *Author:ruchao.jiang
 *Created: 2019/3/20 14:17
 *Email:ruchao.jiang@uama.com.cn
 */
class MainTab (private val selecte:Int,private val unselecte:Int,private val title:String): CustomTabEntity {
    override fun getTabUnselectedIcon(): Int=unselecte
    override fun getTabSelectedIcon(): Int=selecte
    override fun getTabTitle(): String =title
}