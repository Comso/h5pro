package com.uama.app.ui.list

import android.util.Log
import com.blankj.utilcode.util.PermissionUtils
import com.cosmo.common.base.BaseActivity
import com.cosmo.common.extension.MainEvent
import com.cosmo.common.extension.linearLayoutManager
import com.cosmo.common.extension.showToast
import com.cosmo.common.extension.visible
import com.cosmo.common.rx.RxBus
import com.uama.app.R
import com.uama.app.ui.list.adapter.NameListAdapter
import com.uama.app.utils.H5RouteUtils
import com.uama.app.utils.getPhoneNumber
import kotlinx.android.synthetic.main.activity_list.*


/**
 *Author:ruchao.jiang
 *Created: 2019/3/12 13:30
 *Email:ruchao.jiang@uama.com.cn
 */
class TestListActivity :BaseActivity() {
    //private var position:Int = 0
    private val adapter by lazy { NameListAdapter() }
    private val list:MutableList<String> = mutableListOf()
    override fun setLayout(): Int = R.layout.activity_list
    override fun setBarTitle(): String ="H5ListActivity"
    private val mTitles = arrayOf("网络状态", "通信录", "拨打电话", "发送短信", "扫一扫")
    override fun start() {
        for (index in mTitles){
            list.add(index)
        }

        recyclerView.linearLayoutManager()
        adapter.setNewData(list)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { _, _, position ->
            when(position){
                //网络状态
                0->{
                    val data=H5RouteUtils._app_getNetstatus()
                    Log.i("test","状态-》"+data.netType)
                }
                //通信录
                1->{
                    PermissionUtils.getPermissions()
                    val data=H5RouteUtils._app_getPhonebook(application)
                    data.forEach {phoneBook->
                        Log.i("test","phone-》"+phoneBook.phoneList.getPhoneNumber())
                        Log.i("test","name-》"+phoneBook.name)
                    }
                }
                //拨打电话
                2->{}
                //发送短信
                3->{}
                //扫一扫
                4->{}
            }
        }

//        refreshView.isEnabled = true
//        adapter.setEmptyView(R.layout.list_empty,recyclerView)
//        refreshView.setOnRefreshListener {
//            refreshView.refreshComplete()
//    }

//        adapter.setOnItemClickListener { _, _, position ->
//            showToast("click : ${list[position]}")
//            RxBus.getInstance().send(MainEvent())
//            holderView.visible()
//        }


       /* adapter.setOnLoadMoreListener({
            if (position >= 100){
                adapter.loadMoreEnd()
            }else{
                for (index in 1..20){
                    position++
                    list.add("Cosmo is $position")
                }
                adapter.notifyDataSetChanged()
                adapter.loadMoreComplete()
            }

        },recyclerView)
        adapter.disableLoadMoreIfNotFullPage()*/

    }
}