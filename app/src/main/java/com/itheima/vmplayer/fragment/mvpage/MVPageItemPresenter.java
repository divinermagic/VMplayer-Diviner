package com.itheima.vmplayer.fragment.mvpage;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.itheima.vmplayer.bean.MvListBean;
import com.itheima.vmplayer.http.OkHttpManager;
import com.itheima.vmplayer.http.StringCallBack;
import com.itheima.vmplayer.util.URLProviderUtil;

import okhttp3.Call;

/**
 * Created by wschun on 2016/10/1.
 */

public class MVPageItemPresenter implements MVPageitemContract.Presenter {

    private static final String TAG ="MVPageItemPresenter" ;
    private MVPageitemContract.View view;

    public MVPageItemPresenter(MVPageitemContract.View view) {
        this.view = view;
        view.setPresenter(this);
     }

    @Override
    public void getData(int offest, int size) {

    }

    @Override
    public void getData(int moffest, int size, String areaCode) {
        Log.i(TAG, "getData: "+URLProviderUtil.getMVListUrl(areaCode, moffest, size));
        OkHttpManager.getOkHttpManager().asyncGet(URLProviderUtil.getMVListUrl(areaCode, moffest, size), null, new StringCallBack() {
            @Override
            public void onError(Call call, Exception e) {
                view.setError(e.getLocalizedMessage());
            }

            @Override
            public void onResponse(String response) {
                if (response!=null){
                    try {
                        MvListBean mvListBean = new Gson().fromJson(response, MvListBean.class);
                        view.setData(mvListBean.getVideos());
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        view.setError(e.getLocalizedMessage());
                    }

                }else {
                    view.setError("");
                }
            }
        });
    }
}
