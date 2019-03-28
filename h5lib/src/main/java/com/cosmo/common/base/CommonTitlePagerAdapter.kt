package com.cosmo.common.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Author:ruchao.jiang
 * Created: 2019/3/19 16:44
 * Email:ruchao.jiang@uama.com.cn
 */
 class CommonTitlePagerAdapter(
        fm: FragmentManager
        , private val mFragments: MutableList<Fragment>
        ,private val mTitles: Array<String>) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int=mFragments.size
    override fun getPageTitle(position: Int): CharSequence? =mTitles[position]
    override fun getItem(position: Int): Fragment=mFragments[position]
}
