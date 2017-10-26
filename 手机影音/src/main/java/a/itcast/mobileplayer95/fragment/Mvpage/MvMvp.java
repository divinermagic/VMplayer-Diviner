package a.itcast.mobileplayer95.fragment.Mvpage;

import java.util.List;

import a.itcast.mobileplayer95.bean.AreaBean;

/**
 * 作者：Magic on 2017/9/22 18:13
 * 邮箱：bonian1852@163.com
 */

public interface MvMvp {

    interface  Presenter{
        void loadData();
    }

    interface View{
        void setData(List<AreaBean> areaBeen);
        void onError(int code, Exception e);
        void showLoading();
        void dismissLoading();
    }
}
