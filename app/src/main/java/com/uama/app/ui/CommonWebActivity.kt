package com.uama.app.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.webkit.ValueCallback
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.PhoneUtils
import com.cosmo.common.base.BaseActivity
import com.cosmo.common.extension.COMMON_RECODE
import com.cosmo.common.extension.toJsonStringByGson
import com.cosmo.common.matisse.ImagePicker
import com.cosmo.common.permission.PermissionResultListener
import com.cosmo.common.permission.PermissionUtils
import com.google.gson.Gson
import com.tencent.smtt.sdk.WebSettings
import com.uama.app.R
import com.uama.app.entity.DialBean
import com.uama.app.entity.PickBean
import com.uama.app.entity.PreViewBean
import com.uama.app.entity.ScanBean
import com.uama.app.utils.H5RouteUtils
import com.uama.app.utils.H5RouteUtils.Companion.fileToBase64
import com.uama.weight.uama_webview.*
import com.uuzuche.lib_zxing.activity.CaptureActivity
import com.uuzuche.lib_zxing.activity.CodeUtils
import java.io.File


/**
 * Author:ruchao.jiang
 * Created: 2019/3/28 19:53
 * Email:ruchao.jiang@uama.com.cn
 */
class CommonWebActivity : BaseActivity() {

    private var webView: BridgeWebView? = null
    override fun setLayout(): Int =R.layout.activity_web
    override fun setBarTitle(): String = "Webview"



    override fun start() {
        mContext = this
        webView = findViewById(R.id.webView)
        initWebview(this, webView!!)
        webView?.loadUrl("file:///android_asset/test.html")
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

        private var mFunction:CallBackFunction? = null
        fun initWebview(context: Context, webView: BridgeWebView) {
            val settings = webView.settings
            settings.allowFileAccess = true
            settings.domStorageEnabled = true//允许DCOM
            settings.cacheMode = WebSettings.LOAD_NO_CACHE
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                settings.mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
            val webViewClient = BridgeWebViewClient(context, webView)
            webView.webViewClient = webViewClient
            webView.webChromeClient = BridgeWebChromeClient(object : BridgeWebChromeClient.FileChooserCallback {
                override fun showFileChooserUris(valueCallback: ValueCallback<Array<Uri>>) {

                }

                override fun showFileChooserUri(valueCallback: ValueCallback<Uri>) {

                }
            })

            webViewClient.registWebClientListener(object : BridgeWebViewClient.WebClientListener {
                override fun setLoadFail() {

                }

                override fun pageLoadFinished() {

                }

                override fun webviewImageClick(list: List<String>, position: Int) {

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

            webView.registerHandler("_app_selectPics"){data, function ->
                val path= Environment.getExternalStorageDirectory().absolutePath + File.separator + "lvman/crop/1535697426066.png"
                val file = FileUtils.getFileByPath(path)

                function?.onCallBack(Gson().toJson(fileToBase64(file)))
            }


            // 扫一扫
            webView.registerHandler("_app_scan") { data, function ->
                mFunction = function
                PermissionUtils.checkPermission(context as BaseActivity, PermissionResultListener {
                    val intent = Intent(context, CaptureActivity::class.java)
                    context.startActivityForResult(intent, COMMON_RECODE)
                }
                        ,Manifest.permission.CAMERA
                        ,Manifest.permission.READ_EXTERNAL_STORAGE
                        ,Manifest.permission.READ_EXTERNAL_STORAGE)
            }


            //发短信
            webView.registerHandler("_app_sendSMG", object :BridgeHandler{
                override fun handler(data: String?, call: CallBackFunction?) {
                    data?.let {
                        val bean: DialBean? = Gson().fromJson(it,DialBean::class.java)
                        PhoneUtils.sendSms(bean?.phone?:"", bean?.text?:"")
                    }
                   //
                }
            })


            //选择图片
            webView.registerHandler("_app_selectPics") { data, call ->
                data?.let {
                    val bean: PickBean? = Gson().fromJson(it,PickBean::class.java)
                    bean?.let {
                        mFunction = call
                        ImagePicker.pick(context)

                    }
                }
            }


            //图片预览
            webView.registerHandler("_app_previewPic", object :BridgeHandler{
                override fun handler(data: String?, call: CallBackFunction?) {
                    data?.let {
                        val bean: PreViewBean = Gson().fromJson(it,PreViewBean::class.java)
                        val intent = Intent(context,ImagePreViewActvity::class.java)
                        intent.putExtra("bean",bean)
                        context.startActivity(intent)
                    }
                }
            })


            //webView.registerHandler("_app_getNetstatus",hand)


            // 网络状态
            webView.registerHandler("_app_getNetstatus",object :BridgeHandler{
                override fun handler(data: String?, call: CallBackFunction?) {
                    val dat = H5RouteUtils._app_getNetstatus()
                    val callBa = Gson().toJson(dat)
                    call?.onCallBack(callBa)
                }
            })

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == COMMON_RECODE) {
            data?.let {
                val bundle: Bundle? = it.extras
                bundle?.let {
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        val result:String? = bundle.getString(CodeUtils.RESULT_STRING)
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




