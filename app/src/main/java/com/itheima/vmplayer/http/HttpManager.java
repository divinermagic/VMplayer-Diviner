package com.itheima.vmplayer.http;

import okhttp3.OkHttpClient;

/**
 * Created by wschun on 2016/9/30.
 */

public class HttpManager {

    public static HttpManager httpManager;

    public static HttpManager getHttpManager(){
        if (httpManager==null){
            synchronized (HttpManager.class){
                if (httpManager==null){
                    httpManager=new HttpManager();
                }
            }
        }
        return httpManager;
    }


    private HttpManager(){
        OkHttpClient okHttpClient=new OkHttpClient();
    }

}
