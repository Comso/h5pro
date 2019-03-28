package com.uama.app.ui.list

import android.Manifest
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.blankj.utilcode.util.PhoneUtils
import com.cosmo.common.base.BaseActivity
import com.cosmo.common.extension.COMMON_RECODE
import com.cosmo.common.extension.goActForResult
import com.cosmo.common.extension.linearLayoutManager
import com.cosmo.common.matisse.GifSizeFilter
import com.cosmo.common.matisse.Glide4Engine
import com.cosmo.common.matisse.ImagePicker
import com.cosmo.common.permission.PermissionResultListener
import com.cosmo.common.permission.PermissionUtils
import com.uama.app.R
import com.uama.app.ui.list.adapter.NameListAdapter
import com.uuzuche.lib_zxing.activity.CaptureActivity
import com.uuzuche.lib_zxing.activity.CodeUtils
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.filter.Filter
import com.zhihu.matisse.internal.entity.CaptureStrategy
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
                0->{}
                //通信录
                1->{}
                //拨打电话
                2->{
                    MaterialDialog(mContext).show {
                        title(text = "提示")
                        message(text = "确定拨打10086?")
                        positiveButton {
                           it.dismiss()
                            PhoneUtils.dial("10086")
                        }
                        negativeButton {
                            it.dismiss()
                        }
                    }

                }
                //发送短信
                3->{
                    PhoneUtils.sendSms("10086","非常感谢您推荐的活动！")
                }
                //扫一扫
                4->{
                    PermissionUtils.checkPermission(mContext as BaseActivity, PermissionResultListener {
                        goActForResult(CaptureActivity::class)
                    }
                            ,Manifest.permission.CAMERA
                            ,Manifest.permission.READ_EXTERNAL_STORAGE
                            ,Manifest.permission.READ_EXTERNAL_STORAGE)
                }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == COMMON_RECODE){
            data?.let {
               val bundle:Bundle? = it.extras
                bundle?.let {
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        val result = bundle.getString(CodeUtils.RESULT_STRING)
                        Toast.makeText(mContext, "解析结果:$result", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(mContext, "解析二维码失败", Toast.LENGTH_LONG).show();
                    }
                }

            }
        }
    }
}