package com.uama.app.ui.list.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.uama.app.R

/**
 *Author:ruchao.jiang
 *Created: 2019/3/12 13:47
 *Email:ruchao.jiang@uama.com.cn
 */


class NameListAdapter : BaseQuickAdapter<String,BaseViewHolder>(R.layout.activity_item, null) {
    override fun convert(holder: BaseViewHolder?, bean: String?) {
        holder?.let {
            with(it){
                setText(R.id.showName,bean?:"")
            }
        }

    }
}