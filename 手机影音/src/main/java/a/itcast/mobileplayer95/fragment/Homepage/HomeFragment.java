package a.itcast.mobileplayer95.fragment.Homepage;

import android.widget.TextView;

import java.util.List;

import a.itcast.mobileplayer95.BaseFragment;
import a.itcast.mobileplayer95.R;
import a.itcast.mobileplayer95.bean.VideoBean;
import a.itcast.mobileplayer95.utils.LogUtils;

/**
 * 作者：Magic on 2017/9/6 09:24
 * 邮箱：bonian1852@163.com
 */

public class HomeFragment extends BaseFragment implements HomeMvp.View{

    private static final String TAG = "HomeFragment";
    private HomeMvp.Presenter presenter;


    public HomeFragment() {
        LogUtils.e(TAG,"HomeFragment.HomeFragment,");
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        TextView tv_text = (TextView) rootView.findViewById(R.id.tv_text);
        //获取初始化参数
        tv_text.setText("这是首页的Fragment界面");

        // TODO: 2017/9/19 创建Presenter [不能直接用HomePresenter]
        // TODO: 2017/9/19 HomeMvp.Presenter presenter = new HomePresenter(this);并转成成员变量 [Ctrl+Alt+F]
        LogUtils.e(TAG,"HomeFragment.initView,创建Presenter,并请求数据");
        presenter = new HomePresenter(this);
        presenter.loadData(0,10);
        
    }

    @Override
    public void setData(List<VideoBean> videoBeen) {
        LogUtils.e(TAG,"HomeFragment.setData,videoBeen="+videoBeen.size());
    }

    @Override
    public void onError(int code, Exception e) {

    }
}
