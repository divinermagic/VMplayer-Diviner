package com.itheima.vmplayer.fragment.yuedanpage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.itheima.vmplayer.R;
import com.itheima.vmplayer.adapter.YueDanFragmentAdapter;
import com.itheima.vmplayer.bean.YueDanBean;
import com.itheima.vmplayer.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wschun on 2016/8/31.
 */
public class YueDanFragment extends BaseFragment implements YueDanFragmentContract.View {

    private Unbinder unbinder;


    @BindView(R.id.rv_recycleview)
    RecyclerView rvRecycleview;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    private YueDanFragmentContract.Presenter presenter;
    private List<YueDanBean.PlayListsBean> playListsBeanArrayList;
    private YueDanFragmentAdapter yueDanFragmentAdapter;
    private MaterialDialog.Builder builder;
    private MaterialDialog materialDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_yuedan_page, container, false);
            ButterKnife.bind(this, rootView);
            obserView(640, 360);
            int w = screenWidth;
            int h = screenHeight;
            playListsBeanArrayList = new ArrayList<>();
            initView();
            new YueDanFragmentPresenter(this);
            presenter.getData(offset, SIZE);
        }

        unbinder = ButterKnife.bind(this,rootView);
        return rootView;
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        yueDanFragmentAdapter = new YueDanFragmentAdapter(playListsBeanArrayList, getActivity(), screenWidth, screenHeight);
        rvRecycleview.setAdapter(yueDanFragmentAdapter);
        showLoging();
        rvRecycleview.setLayoutManager(linearLayoutManager);
        srlRefresh.setRefreshing(true);
        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh = true;
                presenter.getData(0, SIZE);
            }
        });

        rvRecycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (
                        (lastVisibleItemPosition + 1) == yueDanFragmentAdapter.getItemCount() &&
                                hasmore) {
                    srlRefresh.setRefreshing(true);
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
    public void setData(List<YueDanBean.PlayListsBean> data) {
        if (data == null) return;

        if (refresh) {
            refresh = false;
            playListsBeanArrayList.clear();
        }

        if (data.size() > 0) {
            hasmore = true;
        } else {
            hasmore = false;
        }
        offset += data.size();
        if (srlRefresh != null)
            srlRefresh.setRefreshing(false);
        playListsBeanArrayList.addAll(data);
        yueDanFragmentAdapter.notifyDataSetChanged();
        dismiss();
    }

    @Override
    public void setError(String error) {
        srlRefresh.setRefreshing(false);
        if (refresh) {
            refresh = false;
            Toast.makeText(getActivity(), "刷新失败", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
        }
        dismiss();
    }

    @Override
    public void setPresenter(YueDanFragmentContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
