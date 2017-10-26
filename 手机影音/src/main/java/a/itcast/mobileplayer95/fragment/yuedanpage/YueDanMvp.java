package a.itcast.mobileplayer95.fragment.yuedanpage;

import java.util.List;

import a.itcast.mobileplayer95.bean.YueDanBean;

/**
 * 作者：Magic on 2017/9/21 15:01
 * 邮箱：bonian1852@163.com
 */

public interface YueDanMvp {

    interface Presenter{
        void loadData(int offset,int size);
    }

    interface View{
        void setData(List<YueDanBean.PlayListsBean> playLists);
        void setError(int code, Exception e);
        //数据加载对话框
        void showLoading();
        void dismisLoading();
    }

}
