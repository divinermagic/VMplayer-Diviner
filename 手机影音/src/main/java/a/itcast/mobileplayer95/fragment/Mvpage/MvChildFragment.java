package a.itcast.mobileplayer95.fragment.Mvpage;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import a.itcast.mobileplayer95.BaseFragment;
import a.itcast.mobileplayer95.R;
import a.itcast.mobileplayer95.adapter.MvChildAdapter;
import a.itcast.mobileplayer95.bean.VideoBean;
import a.itcast.mobileplayer95.utils.LogUtils;
import butterknife.BindView;


/**
 * 作者：Magic on 2017/9/22 20:43
 * 邮箱：bonian1852@163.com
 */

public class MvChildFragment extends BaseFragment implements MvChildMvp.View {

    private static final String TAG = "MvChildFragment";
    private boolean isRefresh;

    //[1]
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    //[2] 定义 子界面的 code 代码 [JSON里的code]
    private  String code;

    private MvChildMvp.Presenter presenter;

    private List<VideoBean> videoList;

    private MvChildAdapter mvChildAdapter;

    // TODO: 2017/9/21 上拉刷新 hasMore 没有更多数据了的标志位 等于 true 表示有更多数据 false 表示没有更多数据
    private boolean hasMore = true;

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

        //初始化 列表
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        videoList = new ArrayList<>();
        mvChildAdapter = new MvChildAdapter(videoList);
        recyclerView.setAdapter(mvChildAdapter);

        //下拉刷新界面监听
        refresh.setOnRefreshListener(new OnMvRefreshListener());
        //上来加载
        recyclerView.addOnScrollListener(new OnMvScrollListener());

    }

    @Override
    public void setData(List<VideoBean> videos) {
        LogUtils.e(TAG,"MvChildFragment.setData,videos:"+videos.size());

        //针对下拉刷新的处理
        if (isRefresh){
            videoList.clear();
            isRefresh = false;
            refresh.setRefreshing(false);
        }

        //上拉刷新 计算下一页的起始位置
        offset += videos.size();
        //如果返回的数据不等于请求的大小,就说明没有下一页了 现在因为服务器出了BUG,所以改成>=10个
        hasMore = videos.size() >= SIZE;

        //填充数据 并notify Data Set Changed 通知数据集变化
        videoList.addAll(videos);
        mvChildAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(int code, Exception e) {
        Toast.makeText(getContext(), "MvChildFragment错误码为:"+code, Toast.LENGTH_SHORT).show();
    }


    //上来加载的监听
    private class OnMvScrollListener extends RecyclerView.OnScrollListener {
        @Override
        /**
         * 当滚动状态发生变化的时候被调用
         *  newState 为0:说明是 静止状态
         *           为1:说明是 开始滚动
         *           为2:说明是 惯性滚动
         */
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            //获取当前可见的最后一个条目
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();//查找最后一个可见条目的位置
            //当用户松手时发现已经是在最后一项,则获取下一页数据
            if (newState == 0 && lastVisibleItemPosition == videoList.size()-1&&hasMore){
                presenter.loadData(code,SIZE,offset);
            }
        }
    }

    //下拉刷新界面监听
    private class OnMvRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            isRefresh = true;
            offset = 0;
            presenter.loadData(code,SIZE,offset);
        }
    }
}
