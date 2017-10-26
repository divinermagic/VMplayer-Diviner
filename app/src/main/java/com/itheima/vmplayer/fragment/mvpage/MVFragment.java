package com.itheima.vmplayer.fragment.mvpage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.vmplayer.R;
import com.itheima.vmplayer.adapter.MyViewPageAdapter;
import com.itheima.vmplayer.bean.AreaBean;
import com.itheima.vmplayer.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wschun on 2016/9/28.
 */

public class MVFragment extends BaseFragment implements MVPageContract.View {
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPage)
    ViewPager viewPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_mv, container, false);
            ButterKnife.bind(this, rootView);
            new MVPagePresenter(this);
            presenter.getData(offset,SIZE);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    private List<Fragment> fragmentList=new ArrayList<>();
    @Override
    public void setData(List<AreaBean> areaBeanArrayList) {
        for (int i = 0; i < areaBeanArrayList.size(); i++) {
               fragmentList.add(MVPageItemFragment.getInstance(areaBeanArrayList.get(i).code));
        }
        initView(areaBeanArrayList);
        dismiss();
    }

    private void initView(List<AreaBean> areaBeanArrayList) {
        MyViewPageAdapter viewPageAdapter = new MyViewPageAdapter(getFragmentManager(), fragmentList, areaBeanArrayList);
        viewPage.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPage);
        showLoging();

    }

    @Override
    public void setError(String msg) {
        dismiss();
    }
    private  MVPageContract.Presenter presenter;
    @Override
    public void setPresenter(MVPageContract.Presenter presenter) {
         this.presenter=presenter;
    }
}
