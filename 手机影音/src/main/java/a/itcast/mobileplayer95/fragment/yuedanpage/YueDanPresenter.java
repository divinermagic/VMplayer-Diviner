package a.itcast.mobileplayer95.fragment.yuedanpage;

import a.itcast.mobileplayer95.bean.YueDanBean;
import a.itcast.mobileplayer95.http.BaseCallBack;
import a.itcast.mobileplayer95.http.HttpManager;
import a.itcast.mobileplayer95.utils.LogUtils;
import a.itcast.mobileplayer95.utils.URLProviderUtil;

/**
 * 作者：Magic on 2017/9/21 15:03
 * 邮箱：bonian1852@163.com
 */

public class YueDanPresenter implements YueDanMvp.Presenter {

    private static final String TAG = "YueDanPresenter";

    YueDanMvp.View view;

    public YueDanPresenter(YueDanMvp.View view) {
        this.view = view;
    }

    @Override
    public void loadData(int offset, int size) {

        String url = URLProviderUtil.getMainPageYueDanUrl(offset,size);

        LogUtils.e(TAG,"YueDanPresenter.loadData,url:"+url);

        HttpManager.getInstance().get(url, new BaseCallBack<YueDanBean>() {
            @Override
            public void onFailure(int code, Exception e) {
                view.setError(code,e);
            }

            @Override
            public void onSuccess(YueDanBean yueDanBean) {
                view.setData(yueDanBean.getPlayLists());
            }


        });
    }
}
