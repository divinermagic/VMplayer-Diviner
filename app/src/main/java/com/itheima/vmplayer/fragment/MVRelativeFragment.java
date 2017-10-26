package com.itheima.vmplayer.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.vmplayer.R;
import com.itheima.vmplayer.adapter.RelativeMvRecycleAdapter;
import com.itheima.vmplayer.bean.MVDetailBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Wang
 * Date  2016/9/6.
 * Email 1198190260@qq.com
 */
public class MVRelativeFragment extends Fragment {


    @Bind(R.id.rv_recycleview)
    RecyclerView rvRecycleview;
    private MVDetailBean mvDetailBean;
    private View rootView;
    private RelativeMvRecycleAdapter relativeMvRecycleAdapter;

    public static MVRelativeFragment newInstance(MVDetailBean mvDetailBean) {
        MVRelativeFragment mvRelativeFragment = new MVRelativeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("mvDetailBean", mvDetailBean);
        mvRelativeFragment.setArguments(bundle);
        return mvRelativeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvDetailBean = getArguments().getParcelable("mvDetailBean");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(R.layout.fragment_relative, container, false);
        ButterKnife.bind(this, rootView);
        initData();
        return rootView;
    }

    private void initData() {
        relativeMvRecycleAdapter = new RelativeMvRecycleAdapter(getActivity(), mvDetailBean.getRelatedVideos());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvRecycleview.setAdapter(relativeMvRecycleAdapter);
        rvRecycleview.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
