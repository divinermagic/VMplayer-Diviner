package a.itcast.mobileplayer95.fragment.yuedanpage;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import a.itcast.mobileplayer95.BaseFragment;
import a.itcast.mobileplayer95.adapter.YueDanAdapter;
import a.itcast.mobileplayer95.bean.YueDanBean;
import a.itcast.mobileplayer95.utils.LogUtils;
import butterknife.BindView;

/**
 * 作者：Magic on 2017/9/21 14:52
 * 邮箱：bonian1852@163.com
 */

public class YueDanFragment extends BaseFragment implements YueDanMvp.View {

    private static final String TAG = "YueDanFragment";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    private static YueDanMvp.Presenter presenter;

    private List<YueDanBean.PlayListsBean> list;
    private YueDanAdapter yueDanadapter;

    private boolean isRefresh;

    // TODO: 2017/9/21 上拉刷新 hasMore 没有更多数据了的标志位 等于 true 表示有更多数据 false 表示没有更多数据
    private boolean hasMore = true;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {

        //使用Presenter 加载数据
        presenter = new YueDanPresenter(this);
        presenter.loadData(offset,SIZE);
        //初始化 RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();
        yueDanadapter = new YueDanAdapter(list);
        recyclerView.setAdapter(yueDanadapter);

        //上拉刷新
        recyclerView.addOnScrollListener(new OnYueDanScrollListener());

        //下拉刷新
        refresh.setOnRefreshListener(new OnYueDanRefreshListener());

    }


    @Override
    public void setData(List<YueDanBean.PlayListsBean> playLists) {
        LogUtils.e(TAG,"YueDanFragment.setData,playLists="+playLists.size());
        //针对下拉刷新的处理
        if (isRefresh){
            list.clear();
            isRefresh = false;
            refresh.setRefreshing(false);
        }

        //上拉刷新 计算下一页的起始位置
        offset += playLists.size();
        //如果返回的数据不等于请求的大小,就说明没有下一页了
        hasMore = playLists.size() == SIZE;

        list.addAll(playLists);
        yueDanadapter.notifyDataSetChanged();
    }

    @Override
    public void setError(int code, Exception e) {
        Toast.makeText(getActivity(), "请求数据发生错误,代码为:"+code, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void dismisLoading() {
        dismissLoadingDialog();
    }

    private  class OnYueDanScrollListener extends RecyclerView.OnScrollListener {
        @Override
        /**
         * 当滚动状态发生变化的时候被调用
         *  newState 为0:说明是 静止状态
         *           为1:说明是 开始滚动
         *           为2:说明是 惯性滚动
         */
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();//查找最后一个可见条目的位置

            //如果状态变为静止 并且是列表的最后一个 则准备加载下一页数据
            //[hasMore]:标志位 如果没有更多数据了 那么就不去加载了 [转成成员变量] true 表示有更多数据 false 表示没有更多数据
            if (newState == 0 && lastVisibleItemPosition == list.size() -1 && hasMore){
                presenter.loadData(offset,SIZE);

            }
        }
    }

    private  class OnYueDanRefreshListener implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            isRefresh = true;
            offset = 0;
            presenter.loadData(offset,SIZE);
        }
    }
}
