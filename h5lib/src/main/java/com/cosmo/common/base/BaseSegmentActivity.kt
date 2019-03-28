package com.cosmo.common.base

import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.cosmo.common.R
import com.flyco.tablayout.SegmentTabLayout
import com.flyco.tablayout.listener.OnTabSelectListener

/**
 *Author:ruchao.jiang
 *Created: 2019/3/19 15:04
 *Email:ruchao.jiang@uama.com.cn
 */
abstract class BaseSegmentActivity :BaseActivity() {
    lateinit var segment: SegmentTabLayout
    lateinit var segmentRoot: LinearLayout
    lateinit var adapter: CommonPagerAdapter
    lateinit var segPager: ViewPager


    override fun setLayout(): Int = R.layout.activity_base_segment
    override fun setBarTitle(): String =""
    override fun start() {
        segment = findViewById(R.id.segment)
        segmentRoot = findViewById(R.id.segmentRoot)
        segPager = findViewById(R.id.segPager)
        segment.setTabData(getTabs())
        adapter = CommonPagerAdapter(supportFragmentManager,getFragments())
        segPager.adapter = adapter

        segPager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) =Unit
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) =Unit
            override fun onPageSelected(position: Int) {
                segment.currentTab = position
            }
        })

        segment.setOnTabSelectListener(object :OnTabSelectListener{
            override fun onTabSelect(position: Int) {
                segPager.currentItem = position
            }
            override fun onTabReselect(position: Int)=Unit
        })
    }
    abstract fun getTabs():Array<String>
    abstract fun getFragments():MutableList<Fragment>
}