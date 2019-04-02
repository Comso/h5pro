package com.cosmo.common.matisse

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.net.Uri
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cosmo.common.BuildConfig
import com.cosmo.common.R
import com.cosmo.common.base.BaseActivity
import com.cosmo.common.permission.PermissionResultListener
import com.cosmo.common.permission.PermissionUtils
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.filter.Filter
import com.zhihu.matisse.internal.entity.CaptureStrategy
import com.zhihu.matisse.listener.OnCheckedListener
import com.zhihu.matisse.listener.OnSelectedListener

/**
 * Author:ruchao.jiang
 * Created: 2019/3/7 16:11
 * Email:ruchao.jiang@uama.com.cn
 */
object ImagePicker {
    const val REQUEST_CODE_CHOOSE = 10800
    fun pick(context: Context,maxNumber:Int = 9,enableCapture:Boolean = true) {
        PermissionUtils.checkPermission(context as BaseActivity, PermissionResultListener {
            Matisse
                .from(context)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.WEBP), false)
                .countable(true)
                .capture(enableCapture)
                .captureStrategy(CaptureStrategy(true, "com.uama.app.fileprovider","release"))
                .maxSelectable(maxNumber)
                .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(context.resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(Glide4Engine())    // for glide-V4
                .forResult(REQUEST_CODE_CHOOSE)

        }, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
    }



    fun pick(fragment: Fragment) {
        PermissionUtils.checkPermission(fragment, PermissionResultListener {
            Matisse
                .from(fragment)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.WEBP), false)
                .countable(true)
                .capture(true)
                .captureStrategy(CaptureStrategy(true, "com.uama.app.fileprovider","release"))
                .maxSelectable(9)
                .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(fragment.context!!.resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(Glide4Engine())    // for glide-V4
                .forResult(REQUEST_CODE_CHOOSE)

        },Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
    }

}
