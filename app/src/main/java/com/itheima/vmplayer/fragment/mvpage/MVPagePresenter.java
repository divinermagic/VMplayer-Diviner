package com.itheima.vmplayer.fragment.mvpage;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.itheima.vmplayer.bean.AreaBean;
import com.itheima.vmplayer.http.OkHttpManager;
import com.itheima.vmplayer.http.StringCallBack;
import com.itheima.vmplayer.util.URLProviderUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;

/**
 * Created by wschun on 2016/10/1.
 */

public class MVPagePresenter implements MVPageContract.Presenter {

    private MVPageContract.View view;

    public MVPagePresenter(MVPageContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void getData(int offest, int size) {
        OkHttpManager.getOkHttpManager().asyncGet(URLProviderUtil.getMVareaUrl(), null, new StringCallBack() {
            @Override
            public void onError(Call call, Exception e) {
                view.setError(e.getLocalizedMessage());
            }

            @Override
            public void onResponse(String response) {
                JsonParser jsonParser=new JsonParser();
                try {
                    JsonElement jsonElement = jsonParser.parse(response);
                    List<AreaBean> areaBeanArrayList=new ArrayList<AreaBean>();
                    JsonArray asJsonArray = jsonElement.getAsJsonArray();
                    Iterator<JsonElement> iterator = asJsonArray.iterator();

                    while (iterator.hasNext()){
                        JsonElement element = iterator.next();
                        AreaBean areaBean = new Gson().fromJson(element, AreaBean.class);
                        areaBeanArrayList.add(areaBean);
                    }
                    view.setData(areaBeanArrayList);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    view.setError(e.getLocalizedMessage());
                }

            }
        });
    }
}
