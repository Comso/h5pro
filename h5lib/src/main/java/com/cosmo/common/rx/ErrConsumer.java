package com.cosmo.common.rx;

import android.content.Context;
import cn.com.uama.retrofitmanager.bean.BaseResp;
import cn.com.uama.retrofitmanager.rx.ErrorConsumer;
import com.cosmo.common.refresh.CommonRefreshView;

/**
 * Author:ruchao.jiang
 * Created: 2019/3/1 13:13
 * Email:ruchao.jiang@uama.com.cn
 */
public class ErrConsumer extends ErrorConsumer {
    Context context;
    CommonRefreshView commonRefreshView;
    public ErrConsumer(){}

    public ErrConsumer(Context context){
        this.context = context;
    }

    public ErrConsumer(Context context,CommonRefreshView commonRefreshView){
        this.context = context;
        this.commonRefreshView = commonRefreshView;
    }



    @Override
    public void onError(BaseResp baseResp) {
        if (commonRefreshView !=null) commonRefreshView.refreshComplete();

    }
}
