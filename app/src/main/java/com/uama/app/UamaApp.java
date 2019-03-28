package com.uama.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;


import com.facebook.drawee.backends.pipeline.Fresco;
import com.uama.app.repository.RetrofitInterceptor;


import java.util.Collections;
import java.util.List;

import javax.net.ssl.X509TrustManager;

import cn.com.uama.retrofitmanager.ApiStatusInterceptor;
import cn.com.uama.retrofitmanager.OkHttpConfiguration;
import cn.com.uama.retrofitmanager.RetrofitProvider;
import cn.com.uama.retrofitmanager.SimpleOkHttpConfiguration;
import okhttp3.Interceptor;

/**
 * Author:ruchao.jiang
 * Created: 2019/2/27 15:11
 * Email:ruchao.jiang@uama.com.cn
 */
public class UamaApp extends Application implements Application.ActivityLifecycleCallbacks, RetrofitProvider {
    public static Application mContext;
    String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Fresco.initialize(this);
/*
        OSSCredentialProvider credentialProvider = new OSSCustomSignerCredentialProvider() {
            @Override
            public String signContent(String content) {
                // 您需要在这里依照OSS规定的签名算法，实现加签一串字符内容，并把得到的签名传拼接上AccessKeyId后返回
                // 一般实现是，将字符内容post到您的业务服务器，然后返回签名
                // 如果因为某种原因加签失败，描述error信息后，返回nil
                // 以下是用本地算法进行的演示
                return OSSUtils.sign("LTAIAubF6GXRgmHC","Ki094wdAUcmAW9QZ7jpLQxBi8DrKbP",content);
            }
        };

        OssProvider.Companion.getInstance().init("jams",credentialProvider,this,endpoint);*/
    }


    @Override
    public String provideBaseUrl() {
        return "www.baidu.com";
    }

    @Override
    public OkHttpConfiguration provideOkHttpConfig() {
        return new SimpleOkHttpConfiguration() {
            @Override
            public List<Interceptor> interceptors() {
                // 自定义拦截器列表
                return Collections.<Interceptor>singletonList(new RetrofitInterceptor(mContext));
            }

            @Override
            public int readTimeoutSeconds() {
                // 自定义读取超时时间，单位为秒。默认30秒。
                return super.readTimeoutSeconds();
            }

            @Override
            public int writeTimeoutSeconds() {
                // 默认写入超时时间，单位为秒。默认30秒。
                return super.writeTimeoutSeconds();
            }

            @Override
            public int connectTimeoutSeconds() {
                // 默认连接超时时间，单位为秒。默认30秒。
                return super.connectTimeoutSeconds();
            }

            @Override
            public X509TrustManager trustManager() {
                // 配置 HTTPS 的证书
                return null;
            }
        };
    }

    @Override
    public ApiStatusInterceptor provideApiStatusInterceptor() {
        return null;
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
