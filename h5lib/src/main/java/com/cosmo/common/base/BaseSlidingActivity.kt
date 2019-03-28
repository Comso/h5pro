package com.cosmo.common.base

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.cosmo.common.R
import com.flyco.tablayout.SlidingTabLayout
import com.flyco.tablayout.listener.OnTabSelectListener

/**
 *Author:ruchao.jiang
 *Created: 2019/3/19 15:04
 *Email:ruchao.jiang@uama.com.cn
 */
abstract class BaseSlidingActivity :BaseActivity() {
    lateinit var pager: ViewPager
    lateinit var slidingTab: SlidingTabLayout

    override fun setLayout(): Int = R.layout.activity_base_sliding
    override fun setBarTitle(): String =""
    override fun start() {
        pager = findViewById(R.id.pager)
        slidingTab = findViewById(R.id.slidingTab)
        pager.adapter = CommonTitlePagerAdapter(supportFragmentManager,getFragments(),getTitles())
        slidingTab.setViewPager(pager,getTitles())
        slidingTab.setOnTabSelectListener(object :OnTabSelectListener{
            override fun onTabSelect(position: Int) {
                pager.currentItem = position
            }
            override fun onTabReselect(position: Int)=Unit
        })
    }

    abstract fun getTitles():Array<String>
    abstract fun getFragments():MutableList<Fragment>

}