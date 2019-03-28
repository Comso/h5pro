package com.uama.app.ui

import android.content.Intent
import com.cosmo.common.base.BaseActivity
import com.cosmo.common.matisse.ImagePicker
import com.uama.app.R
import com.zhihu.matisse.Matisse
import kotlinx.android.synthetic.main.activity_upload.*


/**
 *Author:ruchao.jiang
 *Created: 2019/3/12 13:30
 *Email:ruchao.jiang@uama.com.cn
 */
class OssUploadActivity :BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_upload
    override fun setBarTitle(): String ="OSSUpLoad"
    private val pathList:MutableList<String> = mutableListOf()
    override fun start() {
        commonImageView.setOnClickListener {
            ImagePicker.pick(mContext)
        }
        /*uploadBtn.setOnClickListener {
            RealOssUpload().upLoad("cosmo", pathList,object : UploadListener {
                override fun onError(msg: String) {
                    Toast.makeText(mContext,"onError",Toast.LENGTH_LONG).show()

                }

                override fun onSuccess(mutableList: MutableList<String>) {

                }
            })

        }*/

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            ImagePicker.REQUEST_CODE_CHOOSE -> {
                data?.let {
                    val list:List<String> = Matisse.obtainPathResult(data)
                    if (!list.isNullOrEmpty()){
                        pathList.addAll(list)
                        commonImageView.loadLocalFileImg(list[0])
                    }
                }
            }
        }

    }
}