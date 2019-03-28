package com.cosmo.common.base

import androidx.viewpager.widget.ViewPager
import com.cosmo.common.R
import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener

/**
 *Author:ruchao.jiang
 *Created: 2019/3/19 15:04
 *Email:ruchao.jiang@uama.com.cn
 */
abstract class BaseMainActivity :BaseActivity() {
    lateinit var commonTab: CommonTabLayout
    lateinit var mainPager: ViewPager

    override fun setLayout(): Int = R.layout.activity_base_main
    override fun setBarTitle(): String =""
    override fun start() {
        commonTab = findViewById(R.id.commonTab)
        mainPager = findViewById(R.id.mainPager)
        commonTab.setTabData(getTabs())
        commonTab.setOnTabSelectListener(object :OnTabSelectListener{
            override fun onTabSelect(position: Int) {
                mainPager.currentItem = position
            }
            override fun onTabReselect(position: Int)=Unit
        })
    }
    abstract fun getTabs():ArrayList<CustomTabEntity>
}