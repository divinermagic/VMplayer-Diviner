package a.itcast.mobileplayer95.fragment.Homepage;

import java.util.List;

import a.itcast.mobileplayer95.bean.VideoBean;
import a.itcast.mobileplayer95.http.BaseCallBack;
import a.itcast.mobileplayer95.http.HttpManager;
import a.itcast.mobileplayer95.utils.LogUtils;
import a.itcast.mobileplayer95.utils.URLProviderUtil;

/**
 * 作者：Magic on 2017/9/19 17:31
 * 邮箱：bonian1852@163.com
 */

public class HomePresenter implements HomeMvp.Presenter {

    private static final String TAG = "HomePresenter";
    HomeMvp.View view;

    public HomePresenter(HomeMvp.View view) {
        this.view = view;
    }


    @Override
    public void loadData(int offset, int size) {
        LogUtils.e(TAG,"HomePresenter.loadData,开始加载数据");
        // TODO: 2017/9/19 获得主界面的URL
        String url = URLProviderUtil.getMainPageUrl(offset,size);
        // TODO: 2017/9/19 请求数据
        HttpManager.getInstance().get(url, new BaseCallBack<List<VideoBean>>() {
            @Override
            public void onFailure(int code, Exception e) {
                view.onError(code,e);
            }

            @Override
            public void onSuccess(List<VideoBean> videoBeen) {
                // TODO: 2017/9/19 得到数据后 要填充到View里面 [HomeMvp.View view;]
                // TODO: 2017/9/19 如果成功
                LogUtils.e(TAG,"HomePresenter.onSuccess,成功获取到数据");
                view.setData(videoBeen);

            }

        });


    }
}
