package com.uama.app.ui

import androidx.fragment.app.Fragment
import com.cosmo.common.base.BaseSlidingActivity

/**
 *Author:ruchao.jiang
 *Created: 2019/3/20 15:18
 *Email:ruchao.jiang@uama.com.cn
 */
class SlidingActivity :BaseSlidingActivity() {
    override fun getTitles() = mTitles
    private val mTitles = arrayOf("热门", "iOS", "Android", "前端", "后端", "设计", "工具资源")
    override fun getFragments(): MutableList<Fragment> {
        val fragments:MutableList<Fragment> = mutableListOf()
        mTitles.forEach {
            fragments.add(Fragment())
        }
       return fragments
    }

}