package com.uama.app.ui

import android.graphics.Color
import android.view.View
import com.cosmo.common.base.BaseMainActivity
import com.cosmo.common.entity.MainTab
import com.cosmo.common.extension.*
import com.flyco.tablayout.listener.CustomTabEntity
import com.hjq.bar.OnTitleBarListener
import com.uama.app.R
import com.uama.app.ui.list.TestListActivity

class MainActivity : BaseMainActivity() {
   private  val iconsSelected:Array<Int> = arrayOf(R.mipmap.tab_main_check,
            R.mipmap.tab_service_check,R.mipmap.tab_find_check,R.mipmap.tab_mine_check)
   private  val iconsNormal:Array<Int> = arrayOf(R.mipmap.tab_main_uncheck,
            R.mipmap.tab_service_uncheck,R.mipmap.tab_find_uncheck,R.mipmap.tab_mine_unckeck)
   private  val tabString:MutableList<String> = mutableListOf("首页","服务","发现","我的")
    override fun getTabs(): ArrayList<CustomTabEntity> {
        val tabs = ArrayList<CustomTabEntity>()
        for (index in 0 until tabString.size){
            val tab = MainTab(iconsSelected[index],iconsNormal[index],tabString[index])
            tabs.add(tab)
        }
        return tabs
    }


    override fun setBarTitle(): String="MainApp"
    override fun start() {
        super.start()
        setBar()
        handleEvent()
        commonTab.textSelectColor = Color.RED
        commonTab.textUnselectColor = Color.BLACK
        goAct(CommonWebActivity::class)
        finish()
    }

    private fun setBar(){
        titleBar.title = "Center"
        titleBar.setLeftTitle("Return")
        titleBar.setRightTitle("Right Baby")
        titleBar.onTitleBarListener = object :OnTitleBarListener{
            override fun onRightClick(v: View?) {
                goAct(TestListActivity::class)
            }

            override fun onTitleClick(v: View?) {
                // goAct(OssUploadActivity::class)
                goAct(SegmentActivity::class)

            }

            override fun onLeftClick(v: View?) {
                goAct(SlidingActivity::class)
                //showToast("onLeftClick")
            }
        }
    }


  /*  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ImagePicker.REQUEST_CODE_CHOOSE -> {
                data?.let {
                    list = Matisse.obtainPathResult(data)
                    val sb = StringBuilder()
                    list.forEach {
                        sb.append(it)
                        sb.append("\n")
                    }
                    commonImageView.loadLocalFileImg(list[0])
                }
            }
        }
    }*/

    private fun handleEvent(){
        compositeDisposable?.addDisposable(MainEvent::class,object : EventResultListener<MainEvent> {
            override fun onEvent(result: MainEvent) {
                titleBar.setLeftTitle("HI BABY MainEvent")
            }
        })
    }


}
