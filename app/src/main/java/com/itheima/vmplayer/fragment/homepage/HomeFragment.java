package com.itheima.vmplayer.fragment.homepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.vmplayer.R;
import com.itheima.vmplayer.adapter.HomePageAdapter;
import com.itheima.vmplayer.bean.VideoBean;
import com.itheima.vmplayer.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wschun on 2016/9/28.
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {

    private Unbinder unbinder;

    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.srf_fresh)
    SwipeRefreshLayout srfFresh;
    private List<VideoBean> videoBeanlist;
    private HomePageAdapter homePageAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
            ButterKnife.bind(this, rootView);
            videoBeanlist = new ArrayList<>();
            obserView(540, 640);
            initView();
            new HomePresenter(this);
            presenter.getData(offset, SIZE);
        }
        unbinder = ButterKnife.bind(this,rootView);
        return rootView;
    }

    private void initView() {
        rvHome.setLayoutManager(new LinearLayoutManager(getActivity()));
        homePageAdapter = new HomePageAdapter(videoBeanlist, getActivity(), screenHeight, screenWidth);
        rvHome.setAdapter(homePageAdapter);
        showLoging();
        srfFresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh = true;
                showLoging();
                presenter.getData(0, SIZE);
            }
        });

        rvHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if ((lastVisibleItemPosition == (homePageAdapter.getItemCount() - 1)) && hasmore) {

                    presenter.getData(offset, SIZE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setData(List<VideoBean> lists) {
        if (refresh) {
            videoBeanlist.clear();
        }

//        if (lists.size()==0){
//            hasmore=false;
//        }else
//           hasmore=true;
        hasmore = (lists.size() != 0);

        offset += lists.size();
        videoBeanlist.addAll(lists);
        homePageAdapter.notifyDataSetChanged();
        dismiss();
        if (srfFresh != null)
            srfFresh.setRefreshing(false);

    }

    @Override
    public void setError(String msg) {
        dismiss();
    }

    private HomeContract.Presenter presenter;

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
