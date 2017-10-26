package com.itheima.vmplayer.http;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mr.Wang
 * Date  2016/9/3.
 * Email 1198190260@qq.com
 */
public class OkHttpManager {

    private static OkHttpManager okHttpManager;
    private final OkHttpClient okHttpClient;
    private final Handler handler;

    public static OkHttpManager getOkHttpManager(){

        if (okHttpManager==null){

            synchronized (OkHttpManager.class){

                if (okHttpManager==null){
                    okHttpManager=new OkHttpManager();
                }
            }
        }
        return okHttpManager;
    }


    private OkHttpManager(){
        okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
        okHttpClient.newBuilder().readTimeout(10,TimeUnit.SECONDS);
        okHttpClient.newBuilder().writeTimeout(10,TimeUnit.SECONDS);

        handler = new Handler(Looper.getMainLooper());
    }

    public void asyncGet(String url, Object tag, final CallBack callback){
        Request request = new Request.Builder().url(url).tag(tag).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailResultCallBack(call,e,callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Object object=null;
                try {
                    object=callback.parseNetworkResponse(response);
                    sendSuccessfulCallBack(object,callback);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void sendSuccessfulCallBack(final Object object, final CallBack callback) {
        if (callback==null)return;

        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(object);
                callback.onAfter();
            }
        });

    }


    private void sendFailResultCallBack(final Call call, final IOException e, final CallBack callback) {
        if (callback==null) return;
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onAfter();
                callback.onError(call,e);
            }
        });
    }

}
