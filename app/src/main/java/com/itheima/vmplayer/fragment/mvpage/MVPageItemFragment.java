package com.itheima.vmplayer.fragment.mvpage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.vmplayer.R;
import com.itheima.vmplayer.adapter.MvItemPageAdapter;
import com.itheima.vmplayer.bean.VideoBean;
import com.itheima.vmplayer.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wschun on 2016/10/1.
 */

public class MVPageItemFragment extends BaseFragment implements MVPageitemContract.View {

    @Bind(R.id.rv_mvitem)
    RecyclerView rvMvitem;
    @Bind(R.id.srl_fresh)
    SwipeRefreshLayout srlFresh;
    private String code;
    private List<VideoBean> videoBeanList;
    private MvItemPageAdapter mvItemPageAdapter;

    public static MVPageItemFragment getInstance(String code) {
        MVPageItemFragment mvPageItemFragment = new MVPageItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("code", code);
        mvPageItemFragment.setArguments(bundle);
        return mvPageItemFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        code = bundle.getString("code", "");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_mv_item, container, false);
            ButterKnife.bind(this, rootView);
            videoBeanList = new ArrayList<>();
            obserView(640, 360);
            initView();
            new MVPageItemPresenter(this);
            presenter.getData(offset, SIZE, code);
        }
        return rootView;
    }

    private void initView() {
        rvMvitem.setLayoutManager(new LinearLayoutManager(getActivity()));
        mvItemPageAdapter = new MvItemPageAdapter(videoBeanList, getActivity(), screenWidth, screenHeight);
        rvMvitem.setAdapter(mvItemPageAdapter);

        srlFresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh = true;
                presenter.getData(0, SIZE, code);
            }
        });

        rvMvitem.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if ((lastVisibleItemPosition + 1) == mvItemPageAdapter.getItemCount() && hasmore) {
                    presenter.getData(offset, SIZE, code);
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
        ButterKnife.unbind(this);
    }

    @Override
    public void setData(List<VideoBean> videoBeanLists) {
        if (refresh) {
            videoBeanList.clear();
            refresh = false;
        }
        if (videoBeanLists.size() > 0)
            hasmore = true;
        else
            hasmore = false;
//          hasmore=(videoBeanLists.size()!=0);

        offset += videoBeanLists.size();
        videoBeanList.addAll(videoBeanLists);
        mvItemPageAdapter.notifyDataSetChanged();
        if (srlFresh != null)
            srlFresh.setRefreshing(false);

    }

    @Override
    public void setError(String msg) {

    }

    private MVPageitemContract.Presenter presenter;

    @Override
    public void setPresenter(MVPageitemContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
