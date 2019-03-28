package com.uama.app.ui

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

/**
 * Author:ruchao.jiang
 * Created: 2019/3/28 19:53
 * Email:ruchao.jiang@uama.com.cn
 */
class CommonWebActivity : AppCompatActivity() {

    private var webView: BridgeWebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        init()
    }

    fun init() {
        webView = findViewById(R.id.webView)
        initWebview(this, webView!!)
        webView!!.loadUrl("http://192.168.10.39:9930/test.html")
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

            // 跳转方法
            webView.registerHandler("_app_tel") { data, function -> }


            // 注册外部桥
            webView.registerHandler("_app_third_party_jump") { data, function -> }

            webView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
                if (url != null && url.startsWith("http://"))
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
        }

        fun registerJumper(context: Context, webView: BridgeWebView, isHomeFragment: Boolean) {
            // 跳转方法
            webView.registerHandler("_app_page_jump") { data, function -> }

            // 注册外部桥
            webView.registerHandler("_app_third_party_jump") { data, function -> }

            webView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
                if (url != null && url.startsWith("http://"))
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
        }
    }

}
