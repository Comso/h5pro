package com.cosmo.common.extension

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by liwei on 2018/7/2 11:02
 * Email: liwei@uama.com.cn
 * Description: RecyclerView 相关的 extension functions
 */
//inline fun RecyclerView.addOnItemClickListener(crossinline listener: (Int) -> Unit) {
//    addOnItemTouchListener(object : OnItemClickListener() {
//        override fun SimpleOnItemClick(adapter: BaseQuickAdapter<*>?, view: View?, position: Int) {
//            listener.invoke(position)
//        }
//    })
//}

fun RecyclerView.linearLayoutManager(){
    layoutManager =  LinearLayoutManager(this.context)
}

fun RecyclerView.gridLayoutManager(spanCount:Int){
    layoutManager =  GridLayoutManager(this.context,spanCount)
}
