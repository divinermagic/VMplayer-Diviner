package a.itcast.mobileplayer95.fragment.Mvpage;

import java.util.List;

import a.itcast.mobileplayer95.bean.VideoBean;

/**
 * 作者：Magic on 2017/9/22 21:07
 * 邮箱：bonian1852@163.com
 */

public interface MvChildMvp {

    interface Presenter{
        void loadData(String area, int size, int offset);
    }

    interface View{
        void setData(List<VideoBean> videos);
        void onError(int code, Exception e);

    }
}
