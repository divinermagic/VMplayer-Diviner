package a.itcast.mobileplayer95.fragment.Homepage;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import a.itcast.mobileplayer95.BaseFragment;
import a.itcast.mobileplayer95.R;
import a.itcast.mobileplayer95.adapter.HomeAdapter;
import a.itcast.mobileplayer95.bean.VideoBean;
import a.itcast.mobileplayer95.utils.LogUtils;
import butterknife.Bind;

/**
 * 作者：Magic on 2017/9/6 09:24
 * 邮箱：bonian1852@163.com
 */

public class HomeFragment extends BaseFragment implements HomeMvp.View {

    private static final String TAG = "HomeFragment";


    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    // TODO: 2017/9/21 SwipeRefreshLayout 下拉刷新控件
    @Bind(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    private HomeMvp.Presenter presenter;

    private List<VideoBean> videoBeen;
    private HomeAdapter homeAdapter;

    //是否在刷新
    private boolean isReefresh;

    // TODO: 2017/9/21 上拉刷新 hasMore 没有更多数据了的标志位 等于 true 表示有更多数据 false 表示没有更多数据
    private boolean hasMore = true;


    public HomeFragment() {
        LogUtils.e(TAG, "HomeFragment.HomeFragment,");
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        // TODO: 2017/9/19 创建Presenter [不能直接用HomePresenter]
        // TODO: 2017/9/19 HomeMvp.Presenter presenter = new HomePresenter(this);并转成成员变量 [Ctrl+Alt+F]
        LogUtils.e(TAG, "HomeFragment.initView,创建Presenter,并请求数据");
        presenter = new HomePresenter(this);
        // TODO: 2017/9/21 把从第一个条目[起始位置]  10个为一页 改成个常量 在BaseFragment里面修改
        presenter.loadData(offset, SIZE);

        // TODO: 2017/9/19 填充 recyclerView 列表
        LinearLayoutManager layout = new LinearLayoutManager(getContext());//线性布局的管理器
        // GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);//九宫格布局管理器
        //StaggeredGridLayoutManager staggeredGridLayoutManager =
        //new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);//瀑布流布局管理器
        layout.setOrientation(LinearLayoutManager.VERTICAL);//垂直形式的列表
        recyclerView.setLayoutManager(layout);

        // TODO: 2017/9/21 设置 RecyclerView 的 Adapter  HomeAdapter 放在adapter包下
        videoBeen = new ArrayList<>();
        // TODO: 2017/9/21 videoBeen [alt]+[cmd]+V 生成一个成员变量 生成的在代码的上面 [alt]+[cmd]+F 生成的在最上面
        // TODO: 2017/9/21  new HomeAdapter() 把它也提取出一个成员变量
        // TODO: 2017/9/21  private List<VideoBean> videoBeen;和private HomeAdapter adapter;[alt]+[cmd]+F 提取成最上面的成员变量
        homeAdapter = new HomeAdapter(videoBeen);
        recyclerView.setAdapter(homeAdapter);

        // TODO: 2017/9/21 上拉刷新 给RecyclerView添加个滚动状态  转换匿名内部类 为 内部类 并提取出去 Refactor/convert Anonymous to Inner...
        recyclerView.addOnScrollListener(new OnMainScrollListener());
        // TODO: 2017/9/21 下拉刷新控件的监听
        // TODO: 2017/9/21 转换匿名内部类 为 内部类 Refactor/convert Anonymous to Inner... 需要鼠标移动到OnRefreshListener上
        refreshLayout.setOnRefreshListener(new OnMainRefreshListener());
    }

    @Override
    public void setData(List<VideoBean> videoBeen) {
        LogUtils.e(TAG, "HomeFragment.setData,videoBeen=" + videoBeen.size());

        // TODO: 2017/9/21 意思是下拉刷新已经结束了 状态清除掉了
        if (isReefresh) {
            // TODO: 2017/9/21 下拉刷新需要清除原有的数据
            this.videoBeen.clear();
            isReefresh = false;
            // TODO: 2017/9/21 刷新结束 把图标隐藏掉
            refreshLayout.setRefreshing(false);
        }

        // TODO: 2017/9/21 当上拉刷新成功了 才能修改offset的值 新的数据加载后 那offset就应该变成把新的数据大小 加 上去
        offset += videoBeen.size();
        // TODO: 2017/9/21 hasMore = videoBeen.size() == SIZE; 等于 SIZE 就是true 就是有更多数据 不等于 SIZE 就是没有更多数据
        hasMore = videoBeen.size() == SIZE;//如果返回的数据不等于请求的大小,就说明没有下一页了

        // TODO: 2017/9/21 当获取到数据的时候,把这个新的集合添加到我们原先的数据里面去
        this.videoBeen.addAll(videoBeen);
        homeAdapter.notifyDataSetChanged();//[notify Data Set Changed]:通知数据集变化
    }

    @Override
    public void onError(int code, Exception e) {

    }

    //显示对话框
    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    //隐藏对话框
    @Override
    public void dismisLoading() {
        dismissLoadingDialog();
    }

    // TODO: 2017/9/21 下拉刷新控件的监听
    // TODO: 2017/9/21 转换匿名内部类 为 内部类 Refactor/convert Anonymous to Inner... 需要鼠标移动到OnRefreshListener上
    private class OnMainRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            offset = 0;
            presenter.loadData(offset, SIZE);
            // TODO: 2017/9/21 isRefresh 定义为成员变量 一旦等于true 就是在刷新 需要在setData里面做判断
            isReefresh = true;
        }
    }

    // TODO: 2017/9/21 给RecyclerView添加个滚动状态
    private class OnMainScrollListener extends RecyclerView.OnScrollListener {
        @Override
        /**
         * 当滚动状态发生变化的时候被调用
         *  newState 为0:说明是 静止状态
         *           为1:说明是 开始滚动
         *           为2:说明是 惯性滚动
         */
        // TODO: 2017/9/21 RecyclerView.OnScrollListener.onScrollStateChanged  当滚动状态发生变化的时候被调用
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            LogUtils.e(TAG, "OnMainScrollListener.onScrollStateChanged,newState=" + newState);

            // TODO: 2017/9/21 onScrollStateChanged 获取当前可见的最后一个条目位置 只有线性布局才可以
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();//查找最后一个可见条目的位置

            //如果状态变为静止 并且是列表的最后一个 则准备加载下一页数据
            //[hasMore]:标志位 如果没有更多数据了 那么就不去加载了 [转成成员变量] true 表示有更多数据 false 表示没有更多数据
            if (newState == 0 && lastVisibleItemPosition == videoBeen.size() - 1 && hasMore) {
                presenter.loadData(offset, SIZE);

            }
        }

        @Override
        // TODO: 2017/9/21 RecyclerView.OnScrollListener.onScrolled 不断的获取当前滚动位置时
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            LogUtils.e(TAG, "OnMainScrollListener.onScrolled,");
        }
    }
}
