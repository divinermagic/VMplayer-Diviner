package a.itcast.mobileplayer95.adapter;

import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import a.itcast.mobileplayer95.R;
import a.itcast.mobileplayer95.bean.VideoBean;
import a.itcast.mobileplayer95.utils.Util;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：Magic on 2017/9/21 08:17
 * 邮箱：bonian1852@163.com
 */

// TODO: 2017/9/21 RecyclerView.Adapter 是必须要接收一个泛型的 这个泛型就是 Adapter.ViewHolder 而且要 extends RecyclerView.ViewHolder
// TODO: 2017/9/21 我这里用的是自定义的 HomeAdapter.MyViewHolder
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_item_logo)
        ImageView ivItemLogo;
        @Bind(R.id.iv_contentimg)
        ImageView ivContentimg;
        @Bind(R.id.viewbg)
        View viewbg;
        @Bind(R.id.iv_type)
        ImageView ivType;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_description)
        TextView tvDescription;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            // TODO: 2017/9/21 初始化图片大小 计算规则:要显示的高度:要显示的宽度(屏幕宽度)=图片的原始高度:图片的原始宽度 [未知的是:要显示的高度]
            // TODO: 2017/9/21 [要显示的高度] 计算规则:要显示的高度 = 图片的原始高度:图片的原始宽度 * 要显示的宽度(屏幕宽度
            // TODO: 2017/9/21 import android.graphics.Point;
            Point point = Util.computeImgSize(640,540,itemView.getContext());
            ivContentimg.getLayoutParams().width = point.x;
            ivContentimg.getLayoutParams().height = point.y;
            ivContentimg.requestLayout();
            // TODO: 2017/9/21 viewbg:给所有的条目最上面添加个灰色的阴影 让字体看的更清晰
            viewbg.getLayoutParams().width = point.x;
            viewbg.getLayoutParams().height = point.y;
            viewbg.requestLayout();;

        }
    }

    // TODO: 2017/9/21 主界面需要接收一个列的集合的数据 这个不是凭空冒出来的 只能是我们用构造方法传进来的
    private List<VideoBean> videoBeen;

    // TODO: 2017/9/21 这个不是凭空冒出来的 只能是我们用构造方法传进来的
    public HomeAdapter(List<VideoBean> videoBeen) {
        this.videoBeen = videoBeen;
    }

    @Override
    // TODO: 2017/9/21 HomeAdapter.onCreateViewHolder 创建新的 ViewHolder 只在没有itemView的时候被调用 相当于系统给我们做了个复用处理
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // TODO: 2017/9/21  HomeAdapter.onCreateViewHolder 填充条目内容
        View itemView = View.inflate(parent.getContext(), R.layout.homepage_item, null);
        // TODO: 2017/9/21 return new MyViewHolder(itemView) -- [MyViewHolder]:这里面要用的话 ViewHolder 常规的功能 所有的条目给引用起来
        // TODO: 2017/9/21  记得用ButterKnife 生成下 homepage_item 里的所有布局 并生成ButterKnife里的ViewHolder 把生成的条目 剪切到自己的ViewHolder里
        return new MyViewHolder(itemView);
    }

    @Override
    // TODO: 2017/9/21 HomeAdapter.onBindViewHolder 为 ViewHolder 绑定数据 每一次都会执行 holder:不管是新建的还是复用的 只要拿到holder这里面都是不为空的
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // TODO: 2017/9/21 获取到当前条目的数据
        VideoBean videoBean = videoBeen.get(position);

        // TODO: 2017/9/21 填充内容
        holder.tvTitle.setText(videoBean.getTitle());//[Title]:标题
        holder.tvDescription.setText(videoBean.getDescription());//[Description]:简介


        // TODO: 2017/9/21 Glide 加载图片 compile 'com.github.bumptech.glide:glide:3.7.0'
        // TODO: 2017/9/21 Glide 加载图片 但直接这样做的话 图片加载出来有大有小 需要加载出来界面的时候 把图片固定下 在ButterKnife.bind(this, itemView)下
        // TODO: 2017/9/21 1.上下文 是为了获取data目录,2.图片的路径 3.ImageView 就自动加载起来了 然后图片显示的时候 要重新计算图片大小
        Glide.with(holder.itemView.getContext()).load(videoBean.getPosterPic()).into(holder.ivContentimg);
    }

    @Override
    // TODO: 2017/9/21 HomeAdapter.getItemCount 获取项目的数量 
    public int getItemCount() {
        return videoBeen.size();
    }

}
