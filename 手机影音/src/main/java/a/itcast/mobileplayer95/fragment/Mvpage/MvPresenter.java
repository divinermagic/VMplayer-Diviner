package a.itcast.mobileplayer95.fragment.Mvpage;

import java.util.List;

import a.itcast.mobileplayer95.bean.AreaBean;
import a.itcast.mobileplayer95.http.BaseCallBack;
import a.itcast.mobileplayer95.http.HttpManager;
import a.itcast.mobileplayer95.utils.URLProviderUtil;

/**
 * 作者：Magic on 2017/9/22 18:15
 * 邮箱：bonian1852@163.com
 */

public class MvPresenter implements MvMvp.Presenter {

    MvMvp.View view;//这个View 不是凭空生成出来的 是通过构造方法出来的

    public MvPresenter(MvMvp.View view) {
        this.view = view;
    }

    @Override
    public void loadData() {

        String url = URLProviderUtil.getMVareaUrl();//[getMVareaUrl]:获取MV区域Url
        // TODO: 2017/9/22 在HttpManager.getInstance().get(url地址,new BaseCallBack<需要一个泛型>)
        HttpManager.getInstance().get(url, new BaseCallBack<List<AreaBean>>() {
            @Override
            public void onFailure(int code, Exception e) {
                view.onError(code,e);
            }

            @Override
            public void onSuccess(List<AreaBean> areaBeen) {
                view.setData(areaBeen);
            }
        });
    }
}
