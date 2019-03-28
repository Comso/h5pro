package com.uama.app.ui

import androidx.fragment.app.Fragment
import com.cosmo.common.base.BaseSegmentActivity
import com.cosmo.common.base.CommonPagerAdapter

/**
 *Author:ruchao.jiang
 *Created: 2019/3/19 16:17
 *Email:ruchao.jiang@uama.com.cn
 */
class SegmentActivity:BaseSegmentActivity() {
    private val list: MutableList<Fragment> = mutableListOf()
    override fun getTabs(): Array<String> = arrayOf("Tab1","Tab2","Tab3")
    override fun getFragments(): MutableList<Fragment> {
        for (index in 0..2)list.add(Fragment())
        return list
    }


}