package a.itcast.mobileplayer95.fragment.Homepage;

import java.util.List;

import a.itcast.mobileplayer95.bean.VideoBean;

/**
 * 作者：Magic on 2017/9/19 17:24
 * 邮箱：bonian1852@163.com
 */

public interface HomeMvp  {

    // TODO: 2017/9/19 interface Presenter{ [Presenter层接口] 用来加载数据的
    interface Presenter{
        void loadData(int offset, int size);
    }

    // TODO: 2017/9/19 interface View{ [View层接口] 用于获取到数据来加载界面的回调
    interface View{
        void setData(List<VideoBean> videoBeen);//请求到数据就SetData
        void onError(int code, Exception e);//没请求到数据就OnError就爆个异常出来
        //﻿数据加载对话框
        void showLoading();
        void dismisLoading();
    }
}
