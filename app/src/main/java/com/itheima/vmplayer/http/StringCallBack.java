package com.itheima.vmplayer.http;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Mr.Wang
 * Date  2016/9/3.
 * Email 1198190260@qq.com
 */
public abstract class StringCallBack extends CallBack<String> {

    @Override
    public String parseNetworkResponse(Response response) throws IOException {
        return  response.body().string();
    }


}
