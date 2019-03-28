package com.cosmo.common.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Author:ruchao.jiang
 * Created: 2019/3/11 14:07
 * Email:ruchao.jiang@uama.com.cn
 */
public class ToastUtil {
    static Toast toast = null;
    public static void cancel(){
        if(toast!=null){
            toast.cancel();
        }
    }

    public static void show(Context context, String info) {
        if(info==null || info.trim().length()<1){
            return;
        }
        if (null == toast) {
            toast = Toast.makeText(context, info, Toast.LENGTH_SHORT);

        } else {
            toast.setText(info);
        }
        toast.show();
    }

}
