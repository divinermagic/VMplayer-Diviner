package com.itheima.vmplayer.fragment.mvpage;

import com.itheima.vmplayer.BasePresenter;
import com.itheima.vmplayer.BaseView;
import com.itheima.vmplayer.bean.AreaBean;

import java.util.List;

/**
 * Created by wschun on 2016/10/1.
 */

public interface MVPageContract {
    interface Presenter extends BasePresenter{}

    interface View extends BaseView<Presenter>{
        void setData(List<AreaBean> areaBeanArrayList);
        void setError(String msg);

    }
}
