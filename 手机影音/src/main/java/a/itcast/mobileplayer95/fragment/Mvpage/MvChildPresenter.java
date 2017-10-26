package a.itcast.mobileplayer95.fragment.Mvpage;

import a.itcast.mobileplayer95.bean.MvListBean;
import a.itcast.mobileplayer95.http.BaseCallBack;
import a.itcast.mobileplayer95.http.HttpManager;
import a.itcast.mobileplayer95.utils.LogUtils;
import a.itcast.mobileplayer95.utils.URLProviderUtil;


/**
 * 作者：Magic on 2017/9/22 21:10
 * 邮箱：bonian1852@163.com
 */

public class MvChildPresenter implements MvChildMvp.Presenter {


    private static final String TAG = "MvChildPresenter";
    // TODO: 2017/9/22 写这两个的意思是:我们的Presenter有了 我们就需要一个View来加载数据
    private MvChildMvp.View view;

    public MvChildPresenter(MvChildMvp.View view) {
        this.view = view;
    }

    @Override
    public void loadData(String area, int size, int offset) {


        String url = URLProviderUtil.getMVListUrl(area,offset,size);
        LogUtils.e(TAG,"MvChildPresenter.loadData,url="+url);
        HttpManager.getInstance().get(url, new BaseCallBack<MvListBean>() {
            @Override
            public void onFailure(int code, Exception e) {
                view.onError(code,e);
            }

            @Override
            public void onSuccess(MvListBean mvListBean) {
                view.setData(mvListBean.getVideos());
            }


        });
    }
}
