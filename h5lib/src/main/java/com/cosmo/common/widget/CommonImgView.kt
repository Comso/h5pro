package com.cosmo.common.widget

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import com.facebook.common.util.UriUtil
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Author:ruchao.jiang
 * Created: 2019/2/26 15:12
 * Email:ruchao.jiang@uama.com.cn
 */
class CommonImgView : SimpleDraweeView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)


    fun loadImg(url:String?){
        //if (!url.isNullOrEmpty()) setImageURI(Uri.parse(url))
    }

    /**
     * 加载图片
     * @param res 本地图片资源
     */
    fun loadResImg(res: Int) {
        val uri = Uri.Builder()
            .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
            .path(res.toString())
            .build()
        setImageURI(uri)
       // super.setImageURI(uri)
    }


    /**
     * 加载图片
     * 本地资源文件
     */
    fun loadLocalFileImg(filePath: String?) {
        filePath?.let {
            val uri = Uri.Builder()
                .scheme(UriUtil.LOCAL_FILE_SCHEME)
                .path(filePath)
                .build()
            super.setImageURI(uri)
        }
    }
}
