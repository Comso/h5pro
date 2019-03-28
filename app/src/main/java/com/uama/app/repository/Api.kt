package com.uama.app.repository

import cn.com.uama.retrofitmanager.RetrofitManager
import cn.com.uama.retrofitmanager.bean.BaseResp
import cn.com.uama.retrofitmanager.bean.SimpleResp
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *Author:ruchao.jiang
 *Created: 2019/3/1 10:40
 *Email:ruchao.jiang@uama.com.cn
 */


val api: Api = RetrofitManager.createService(Api::class.java)

interface Api {
    /**
     * 获取接app版本号
     */
    @GET("getAppVersion")
    fun getVersion(@Query("mtype") mtype: String): Observable<SimpleResp<BaseResp>>

}

