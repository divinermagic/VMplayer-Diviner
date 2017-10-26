package com.itheima.vmplayer.http;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Mr.Wang
 * Date  2016/9/3.
 * Email 1198190260@qq.com
 */
public abstract class CallBack<T> {

    public void onAfter(){

    }

    public abstract T parseNetworkResponse(Response response) throws Exception;
    public abstract void onError(Call call,Exception e);
    public abstract void onResponse(T response);

}
