package com.cosmo.common.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.cosmo.common.R
import com.cosmo.common.extension.gone
import com.cosmo.common.refresh.CommonRefreshView
import com.cosmo.common.widget.PlaceHolderView
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import io.reactivex.disposables.CompositeDisposable

/**
 * Author:ruchao.jiang
 * Created: 2019/3/5 10:26
 * Email:ruchao.jiang@uama.com.cn
 */
abstract class BaseActivity : AppCompatActivity() {
    lateinit var titleBar: TitleBar
    lateinit var refreshView: CommonRefreshView
    lateinit var rootGroup: FrameLayout
    lateinit var holderView: PlaceHolderView
    lateinit var mContext:Context
    var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        mContext = this
        titleBar = findViewById(R.id.titleBar)
        rootGroup = findViewById(R.id.rootGroup)
        refreshView = findViewById(R.id.refresh)
        holderView = findViewById(R.id.holderView)
        compositeDisposable = CompositeDisposable()
        LayoutInflater.from(this).inflate(setLayout(), rootGroup, true)
        titleBar.onTitleBarListener = ToolbarClick(this)
        titleBar.title = setBarTitle()
        refreshView.isEnabled = false
        holderView.gone()
        start()
    }

    protected abstract fun setLayout(): Int
    protected abstract fun setBarTitle(): String
    protected abstract fun start()

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.let {
            if (!it.isDisposed)it.dispose()
        }
    }



    internal inner class ToolbarClick(var activity: Activity) : OnTitleBarListener {
        override fun onLeftClick(v: View) =activity.finish()
        override fun onTitleClick(v: View) {}
        override fun onRightClick(v: View) {}
    }
}
