package com.uama.app.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.ValueCallback

import com.tencent.smtt.sdk.DownloadListener
import com.tencent.smtt.sdk.WebSettings
import com.uama.app.R
import com.uama.weight.uama_webview.BridgeHandler
import com.uama.weight.uama_webview.BridgeWebChromeClient
import com.uama.weight.uama_webview.BridgeWebView
import com.uama.weight.uama_webview.BridgeWebViewClient
import com.uama.weight.uama_webview.CallBackFunction
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.blankj.utilcode.util.PhoneUtils
import com.blankj.utilcode.util.ToastUtils
import com.cosmo.common.base.BaseActivity
import com.cosmo.common.extension.COMMON_RECODE
import com.cosmo.common.extension.goActForResult
import com.cosmo.common.permission.PermissionResultListener
import com.cosmo.common.permission.PermissionUtils
import com.google.gson.Gson
import com.uama.app.utils.H5RouteUtils
import com.uuzuche.lib_zxing.activity.CaptureActivity

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
        webView?.loadUrl("http://192.168.10.39:9930/test.html")
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




            // 扫一扫
            webView.registerHandler("_app_scan") { data, function ->
                PermissionUtils.checkPermission(context as BaseActivity, PermissionResultListener {
                    val intent = Intent(context, CaptureActivity::class.java)
                    context.startActivityForResult(intent, COMMON_RECODE)
                }
                        ,Manifest.permission.CAMERA
                        ,Manifest.permission.READ_EXTERNAL_STORAGE
                        ,Manifest.permission.READ_EXTERNAL_STORAGE)
            }



           /* webView.registerHandler("_app_getNetstatus", object :BridgeHandler{
                override fun handler(data: String?, call: CallBackFunction?) {
                    val dat = H5RouteUtils._app_getNetstatus()
                    val callBa = Gson().toJson(dat)
                    call?.onCallBack(callBa)
                }
            })*/


            //webView.registerHandler("_app_getNetstatus",hand)


            // 网络状态
            /*webView.registerHandler("_app_getNetstatus") { data, function ->
                val dat = H5RouteUtils._app_getNetstatus()
                val callBa = Gson().toJson(dat)
                 function.onCallBack(callBa)
                 function.onCallBack(callBa)
                 ToastUtils.showShort(data.netType.toString())
            }*/

        }
    }
}


