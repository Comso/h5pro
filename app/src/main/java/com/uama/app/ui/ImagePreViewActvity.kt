package com.uama.app.ui

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.cosmo.common.base.BaseActivity
import com.cosmo.common.base.CommonPagerAdapter
import com.uama.app.R
import com.uama.app.entity.PreViewBean
import kotlinx.android.synthetic.main.activity_image_preview.*

/**
 *Author:ruchao.jiang
 *Created: 2019/4/1 14:54
 *Email:ruchao.jiang@uama.com.cn
 */
class ImagePreViewActvity : BaseActivity(){
    val fragmentList:MutableList<Fragment> = mutableListOf()
    override fun setLayout(): Int = R.layout.activity_image_preview
    override fun setBarTitle(): String=""
    override fun start() {
        val bean = intent.getSerializableExtra("bean") as PreViewBean?
        bean?.let {
            val pics:MutableList<String>? = it.picList
            pics?.forEach {
                fragmentList.add(ImageFragment.getInstance(it))
            }

            val adapter = CommonPagerAdapter(supportFragmentManager,fragmentList)
            pager.adapter = adapter
            titleBar.title = "${it.currentIndex?.plus(1)} / ${fragmentList.size}"

            pager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
                override fun onPageScrollStateChanged(state: Int)=Unit
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) =Unit
                override fun onPageSelected(position: Int) {
                    titleBar.title = "${position+1} / ${fragmentList.size}"
                }
            })
        }
    }
}