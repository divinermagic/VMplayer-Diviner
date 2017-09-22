package a.itcast.mobileplayer95.fragment.Mvpage;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import a.itcast.mobileplayer95.BaseFragment;
import a.itcast.mobileplayer95.R;
import a.itcast.mobileplayer95.bean.VideoBean;
import a.itcast.mobileplayer95.utils.LogUtils;
import butterknife.Bind;


/**
 * 作者：Magic on 2017/9/22 20:43
 * 邮箱：bonian1852@163.com
 */

public class MvChildFragment extends BaseFragment implements MvChildMvp.View {

    private static final String TAG = "MvChildFragment";

    //[1]
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.refresh)
    SwipeRefreshLayout refresh;

    //[2] 定义 子界面的 code 代码 [JSON里的code]
    private String code;

    private MvChildMvp.Presenter presenter;

    //[3]
    public static MvChildFragment newInstance(String code){
        //[5]
        Bundle args = new Bundle();
        args.putString("code",code);

        //[4]
        MvChildFragment fragment = new MvChildFragment();
        //fragment 需要在初始化时 获取参数
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        //[6] 获取要显示的地区类型
        Bundle arguments = getArguments();
        String code = arguments.getString("code");
        //LogUtils.e(TAG,"MvChildFragment.initView,code:"+code);

        //使用Presenter加载数据
        presenter = new MvChildPresenter(this);
        presenter.loadData(code,SIZE,offset);
    }

    @Override
    public void setData(List<VideoBean> videos) {
        LogUtils.e(TAG,"MvChildFragment.setData,videos:"+videos.size());
    }

    @Override
    public void onError(int code, Exception e) {
        Toast.makeText(getContext(), "MvChildFragment错误码为:"+code, Toast.LENGTH_SHORT).show();
    }
}
