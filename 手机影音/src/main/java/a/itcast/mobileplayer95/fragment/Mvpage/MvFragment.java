package a.itcast.mobileplayer95.fragment.Mvpage;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import a.itcast.mobileplayer95.BaseFragment;
import a.itcast.mobileplayer95.R;
import a.itcast.mobileplayer95.adapter.MvPageAdapter;
import a.itcast.mobileplayer95.bean.AreaBean;
import butterknife.BindView;

/**
 * 作者：Magic on 2017/9/22 18:08
 * 邮箱：bonian1852@163.com
 */

public class MvFragment extends BaseFragment implements MvMvp.View {

    private static final String TAG = "MvFragment";

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tablayout)
    TabLayout tablayout;


    private MvMvp.Presenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mv;
    }

    @Override
    protected void initView() {
        presenter = new MvPresenter(this);
        // TODO: 2017/9/22 loadData();没有参数的时候,是因为MvPresenter里面的请求地址中的url自己本身没带参数
        presenter.loadData();

    }


    @Override
    public void setData(List<AreaBean> areaBeen) {
       // LogUtils.e(TAG, "MvFragment.setData,areaBeen:=" + areaBeen.size());
        List<Fragment> fragmentList = new ArrayList<>();
        // TODO: 2017/9/22 [2].标题的文字
        List<String> titleList = new ArrayList<>();

        for (AreaBean areaBean : areaBeen) {
            fragmentList.add(MvChildFragment.newInstance(areaBean.getCode()));
            // TODO: 2017/9/22 [3]. 遍历取出 titleList 里的name
            titleList.add(areaBean.getName());
        }
        // TODO: 2017/9/22 [alt]+[cmd]+V 生成一个局部变量 生成的在代码的上面
        MvPageAdapter mvPageAdapter = new MvPageAdapter(getFragmentManager(), fragmentList,titleList);
        viewpager.setAdapter(mvPageAdapter);
        // TODO: 2017/9/22 [1].将TabLayout和ViewPager相关联 并且是在viewPager设置Adapter后才能用
        tablayout.setupWithViewPager(viewpager);
        // TODO: 2017/9/22 [4].怎么把文字显示到 tablayout 上 在MvPageAdapter里面有一个方法 getPageTitle()

    }

    @Override
    public void onError(int code, Exception e) {
        Toast.makeText(getContext(), "请求数据发生错误,代码为:" + code, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void dismissLoading() {
        dismissLoadingDialog();
    }


}
