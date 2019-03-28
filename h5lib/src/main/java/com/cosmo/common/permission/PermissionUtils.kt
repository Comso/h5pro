package com.cosmo.common.permission

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.yanzhenjie.permission.Action
import com.yanzhenjie.permission.AndPermission

/**
 * Author:ruchao.jiang
 * Created: 2019/3/7 16:41
 * Email:ruchao.jiang@uama.com.cn
 */
object PermissionUtils {

    fun checkPermission(context: Activity, listener: PermissionResultListener?, vararg args: String) {
        AndPermission
            .with(context)
            .runtime()
            .permission(*args)
            .onGranted { listener?.onGranted() }
            .onDenied {
                rejectPermission(context)
            }
            .start()
    }


    fun checkPermission(context: Fragment, listener: PermissionResultListener?, vararg args: String) {
        AndPermission
            .with(context)
            .runtime()
            .permission(*args)
            .onGranted { listener?.onGranted() }
            .onDenied {
                rejectPermission(context)
            }
            .start()
    }


    private fun rejectPermission(context: Context) {
        MaterialDialog(context).show {
            title(text = "提示")
            message(text = "拒绝权限导致不能使用")
            positiveButton {
                SysPermissionSettings.enterPermissionSettingPage(context)
            }
            negativeButton {
                it.dismiss()
            }
        }
    }


    private fun rejectPermission(context: Fragment) {
        MaterialDialog(context.context!!).show {
            title(text = "提示")
            message(text = "拒绝权限导致不能使用")
            positiveButton {
                SysPermissionSettings.enterPermissionSettingPage(context.context!!)
            }
            negativeButton {
                it.dismiss()
            }
        }
    }
}


