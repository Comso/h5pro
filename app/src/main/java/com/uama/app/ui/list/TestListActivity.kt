package com.uama.app.ui.list

import com.cosmo.common.base.BaseActivity
import com.cosmo.common.extension.MainEvent
import com.cosmo.common.extension.linearLayoutManager
import com.cosmo.common.extension.showToast
import com.cosmo.common.extension.visible
import com.cosmo.common.rx.RxBus
import com.uama.app.R
import com.uama.app.ui.list.adapter.NameListAdapter
import kotlinx.android.synthetic.main.activity_list.*


/**
 *Author:ruchao.jiang
 *Created: 2019/3/12 13:30
 *Email:ruchao.jiang@uama.com.cn
 */
class TestListActivity :BaseActivity() {
    private var position:Int = 0
    private val adapter by lazy { NameListAdapter() }
    private val list:MutableList<String> = mutableListOf()
    override fun setLayout(): Int = R.layout.activity_list
    override fun setBarTitle(): String ="ListActivity"
    override fun start() {
        for (index in 1..20){
            position++
            list.add("Cosmo is $position")
        }

        recyclerView.linearLayoutManager()
        adapter.setNewData(list)
        recyclerView.adapter = adapter
        refreshView.isEnabled = true
        adapter.setEmptyView(R.layout.list_empty,recyclerView)
        refreshView.setOnRefreshListener {
            refreshView.refreshComplete()
    }

        adapter.setOnItemClickListener { _, _, position ->
            showToast("click : ${list[position]}")
            RxBus.getInstance().send(MainEvent())
            holderView.visible()
        }


        adapter.setOnLoadMoreListener({
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
        adapter.disableLoadMoreIfNotFullPage()

    }
}