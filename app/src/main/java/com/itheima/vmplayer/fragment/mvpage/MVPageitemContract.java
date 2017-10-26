package com.itheima.vmplayer.fragment.mvpage;

import com.itheima.vmplayer.BasePresenter;
import com.itheima.vmplayer.BaseView;
import com.itheima.vmplayer.bean.VideoBean;

import java.util.List;

/**
 * Created by wschun on 2016/10/1.
 */

public interface MVPageitemContract {
    interface Presenter extends BasePresenter{

      void   getData(int moffest, int size, String areaCode);
    }

    interface View extends BaseView<Presenter>{

        void setData(List<VideoBean> videoBeanList);
        void setError(String msg);

    }
}
