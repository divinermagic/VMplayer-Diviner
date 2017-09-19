package com.itheima.vmplayer.fragment.yuedanpage;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.itheima.vmplayer.bean.YueDanBean;
import com.itheima.vmplayer.http.OkHttpManager;
import com.itheima.vmplayer.http.StringCallBack;
import com.itheima.vmplayer.util.URLProviderUtil;


import okhttp3.Call;

/**
 * Created by Mr.Wang
 * Date  2016/9/5.
 * Email 1198190260@qq.com
 */
public class YueDanFragmentPresenter implements YueDanFragmentContract.Presenter {

    private static final String TAG ="YueDanFragmentPresenter" ;
    YueDanFragmentContract.View viewRoot;


    public YueDanFragmentPresenter(YueDanFragmentContract.View viewRoot) {
        this.viewRoot = viewRoot;
        viewRoot.setPresenter(this);
    }

    @Override
    public void getData(int offset, int size) {
        Log.i(TAG, "getData: "+ URLProviderUtil.getMainPageYueDanUrl(offset, size));
        OkHttpManager.getOkHttpManager().asyncGet(URLProviderUtil.getMainPageYueDanUrl(offset, size), viewRoot, new StringCallBack() {
            @Override
            public void onError(Call call, Exception e) {
                viewRoot.setError(e.getLocalizedMessage());
            }

            @Override
            public void onResponse(String response) {
              if (response!=null){
                  try {
                      YueDanBean yueDanBean = new Gson().fromJson(response, YueDanBean.class);
                      viewRoot.setData(yueDanBean.getPlayLists());
                  } catch (JsonSyntaxException e) {
                      e.printStackTrace();
                      viewRoot.setError(e.getLocalizedMessage());
                  }
              }else {
                  viewRoot.setError("");
              }
            }
        });
    }
}
