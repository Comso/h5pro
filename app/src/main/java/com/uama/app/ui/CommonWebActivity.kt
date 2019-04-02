package com.uama.app.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.ValueCallback
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.blankj.utilcode.util.PhoneUtils
import com.cosmo.common.base.BaseActivity
import com.cosmo.common.extension.COMMON_RECODE
import com.cosmo.common.extension.toJsonStringByGson
import com.cosmo.common.matisse.ImagePicker
import com.cosmo.common.matisse.ImagePicker.REQUEST_CODE_CHOOSE
import com.cosmo.common.permission.PermissionResultListener
import com.cosmo.common.permission.PermissionUtils
import com.google.gson.Gson
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.uama.app.R
import com.uama.app.entity.*
import com.uama.app.utils.H5RouteUtils
import com.uama.weight.uama_webview.*
import com.uuzuche.lib_zxing.activity.CaptureActivity
import com.uuzuche.lib_zxing.activity.CodeUtils
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.internal.utils.PathUtils
import java.io.File
import java.io.FileInputStream

/**
 * Author:ruchao.jiang
 * Created: 2019/3/28 19:53
 * Email:ruchao.jiang@uama.com.cn
 */
class CommonWebActivity : BaseActivity() {

    private var webView: BridgeWebView? = null
    override fun setLayout(): Int = R.layout.activity_web
    override fun setBarTitle(): String = "Webview"


    override fun start() {
        mContext = this
        webView = findViewById(R.id.webView)
        initWebview(this, webView!!)
//        webView?.loadUrl("file:///android_asset/test.html")
        webView?.loadUrl("http://192.168.10.39:8081/#/pages/hybrid/index")
    }


    override fun onDestroy() {

        if (webView != null) {
            try {
                webView!!.onPause()
                webView!!.removeAllViews()
                webView!!.destroy()
                webView = null
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        super.onDestroy()
    }

    companion object {
        fun getWebResourceResponse(url: String = ""): WebResourceResponse?{
            val webResourceResponse = WebResourceResponse()
            webResourceResponse.encoding = "gzip"
            webResourceResponse.mimeType = "image/png"
            val file = File(getUrlByHtmlPath(url))
            if(!file.exists())return null
            val fileStream = FileInputStream(file)
            webResourceResponse.data = fileStream
            return webResourceResponse
        }


        private var mFunction: CallBackFunction? = null
        private var choosePicFunc:CallBackFunction?=null
        fun initWebview(context: Context, webView: BridgeWebView) {
            val settings = webView.settings
            settings.allowFileAccess = true
            settings.domStorageEnabled = true//允许DCOM
            settings.cacheMode = WebSettings.LOAD_NO_CACHE
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                settings.mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }

            val webViewClient = object : BridgeWebViewClient(context, webView) {
                override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
                    if (request.url?.scheme?.contains("lmimgs") == true) {
                        return getWebResourceResponse(request.url.path?:"")
                    }
                    return null
                }

                override fun shouldInterceptRequest(view: WebView, urlStr: String): WebResourceResponse? {
                    if (urlStr.contains(PrefixUrl)) {
                        return getWebResourceResponse(urlStr)
                    }
                    return null
                }
            }
            webView.webViewClient = webViewClient
            webView.webChromeClient = BridgeWebChromeClient(object : BridgeWebChromeClient.FileChooserCallback {
                override fun showFileChooserUris(valueCallback: ValueCallback<Array<Uri>>) {

                }

                override fun showFileChooserUri(valueCallback: ValueCallback<Uri>) {

                }
            })
            webView.webChromeClient = BridgeWebChromeClient(object : BridgeWebChromeClient.FileChooserCallback {
                override fun showFileChooserUris(valueCallback: ValueCallback<Array<Uri>>) {

                }

                override fun showFileChooserUri(valueCallback: ValueCallback<Uri>) {

                }
            })

            // 拨打电话
            webView.registerHandler("_app_tel") { data, function ->
                MaterialDialog(context).show {
                    title(text = "提示")
                    message(text = "确定拨打$data?")
                    positiveButton {
                        it.dismiss()
                        PhoneUtils.dial(data)
                    }
                    negativeButton {
                        it.dismiss()
                    }
                }
            }

            // 扫一扫
            webView.registerHandler("_app_scan") { data, function ->
                mFunction = function
                PermissionUtils.checkPermission(context as BaseActivity, PermissionResultListener {
                    val intent = Intent(context, CaptureActivity::class.java)
                    context.startActivityForResult(intent, COMMON_RECODE)
                }
                        , Manifest.permission.CAMERA
                        , Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.READ_EXTERNAL_STORAGE)
            }


            //发短信
            webView.registerHandler("_app_sendSMG", object : BridgeHandler {
                override fun handler(data: String?, call: CallBackFunction?) {
                    data?.let {
                        val bean: DialBean? = Gson().fromJson(it, DialBean::class.java)
                        PhoneUtils.sendSms(bean?.phone ?: "", bean?.text ?: "")
                    }
                    //
                }
            })


            //选择图片:此处存在只拍照，需要修改逻辑
            webView.registerHandler("chooseImage") { data, call ->
                data?.let {
                    val bean: PickBean? = Gson().fromJson(it, PickBean::class.java)
                    bean?.let {
                        choosePicFunc = call
                        ImagePicker.pick(context,bean.maxCount?:0,when(bean.type){
                            0->true
                            2->false
                            else->false
                        })
                    }
                }
            }


            //图片预览
            webView.registerHandler("_app_previewPic") { data, call ->
                data?.let {
                    val bean: PreViewBean = Gson().fromJson(it, PreViewBean::class.java)
                    val intent = Intent(context, ImagePreViewActvity::class.java)
                    intent.putExtra("bean", bean)
                    context.startActivity(intent)
                }
            }


            //webView.registerHandler("_app_getNetstatus",hand)


            // 网络状态
            webView.registerHandler("_app_getNetstatus", object : BridgeHandler {
                override fun handler(data: String?, call: CallBackFunction?) {
                    val dat = H5RouteUtils._app_getNetstatus()
                    val callBa = Gson().toJson(dat)
                    call?.onCallBack(callBa)
                }
            })

        }
        const val PrefixUrl = "lmimgs://"
        fun getHtmlPathByUrl(url:String)= PrefixUrl+url

        fun getUrlByHtmlPath(path:String) = path.replace(PrefixUrl,"")

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_CHOOSE -> {
                data?.let {
                    val selectList = Matisse.obtainResult(data)
                    val pathList = selectList.map {uri->
                        getHtmlPathByUrl(PathUtils.getPath(this@CommonWebActivity,uri))
                    }.toMutableList()
                    choosePicFunc?.onCallBack(UploadPicture(pathList).toJsonStringByGson())
                }
            }
            COMMON_RECODE -> {
                data?.let {
                    val bundle: Bundle? = it.extras
                    bundle?.let {
                        if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                            val result: String? = bundle.getString(CodeUtils.RESULT_STRING)
                            val scanBean = ScanBean(result)
                            mFunction?.onCallBack(scanBean.toJsonStringByGson())
                        } else {
                            Toast.makeText(mContext, "解析二维码失败", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            }
        }
    }

}




