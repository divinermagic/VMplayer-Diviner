package a.itcast.mobileplayer95.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import a.itcast.mobileplayer95.Activity.PlayerActivity;
import a.itcast.mobileplayer95.Activity.WebViewActivity;
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

        // TODO: 2017/9/23 当前条目的类型
        int tag;

        public MyViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            // TODO: 2017/9/21 初始化图片大小 计算规则:要显示的高度:要显示的宽度(屏幕宽度)=图片的原始高度:图片的原始宽度 [未知的是:要显示的高度]
            // TODO: 2017/9/21 [要显示的高度] 计算规则:要显示的高度 = 图片的原始高度:图片的原始宽度 * 要显示的宽度(屏幕宽度
            // TODO: 2017/9/21 import android.graphics.Point;
            Point point = Util.computeImgSize(640, 540, itemView.getContext());
            ivContentimg.getLayoutParams().width = point.x;
            ivContentimg.getLayoutParams().height = point.y;
            ivContentimg.requestLayout();
            // TODO: 2017/9/21 viewbg:给所有的条目最上面添加个灰色的阴影 让字体看的更清晰
            viewbg.getLayoutParams().width = point.x;
            viewbg.getLayoutParams().height = point.y;
            viewbg.requestLayout();

            // TODO: 2017/9/23 RecylerView 本身是没有条目点击监听的 (这是优势而不是缺点)
            /*
                但是我们的recyclerView是设置了我们自定义的HomeAdapter的
                自定义的HomeAdapter里面有itemView
                我们直接给itemView设置点击监听就可以了
             */
            // TODO: 2017/9/23 给RecylerView.setAdapter中的itemView注册监听 就相当于给给RecylerView设置了点击监听
            itemView.setOnClickListener(new OnMvClickListener());

            // TODO: 2017/9/23 这一块代码 说明 给RecylerView中 可以给任意一个控件注册点击监听 而不像在ListView里会冲突
//            ivType.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(itemView.getContext(), "好不好"+MyViewHolder.this.getAdapterPosition(), Toast.LENGTH_SHORT).show();
//                }
//            });

        }

        // TODO: 2017/9/23 给RecylerView.setAdapter中的itemView注册监听 就相当于给给RecylerView设置了点击监听
        private class OnMvClickListener implements View.OnClickListener {
            /*
             * 这一块相当于提取了一个监听对象 这个监听对象在我每一个MyViewHolder对象里面
             * 被新建一个监听并注册到我们的[ItemView]条目上去
             */
            @Override
            //MyViewHolder是onClick的外部类
            public void onClick(View v) {

                Context context = itemView.getContext();

                // TODO: 2017/9/23 获取当前被点击条目的数据
                VideoBean videoBean = videoBeen.get(MyViewHolder.this.getAdapterPosition());

                // TODO: 2017/9/23 Toast获取当前被点击的条目
                // Toast.makeText(itemView.getContext(), "嘿嘿嘿"+MyViewHolder.this.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                // Toast.makeText(itemView.getContext(), getAdapterPosition() + "tag:" + tag, Toast.LENGTH_SHORT).show();
                // TODO: 2017/9/23 转到当前界面的响应界面
                Intent mIntent = new Intent();
                // TODO: 2017/9/23 因为此处的tag找不到,但是因为这个onclick是在MyViewHolder里面写的
                // TODO: 2017/9/23 所以我们可以在MyViewHolder里面直接定义个 int tag; 并且在onBindViewHolder中的 holder.tag = tag;
                switch (tag) {
                    case 0:
                    case 4:
                    case 10:
                        // TODO: 2017/9/23 [itemView.getContext()]为什么在内部类里面调用itemView? 因为内部类可以访问外部类,
                        mIntent = new Intent(context, WebViewActivity.class);//打开WebViewActivity
                        mIntent.putExtra("url",videoBean.getUrl());
                        itemView.getContext().startActivity(mIntent);
                        break;
                    case 1:
                    case 5:
                    case 7:
                    case 2:
                    case 3:
                        mIntent = new Intent(context, PlayerActivity.class);
                        mIntent.putExtra("url",videoBean.getUrl());
                        mIntent.putExtra("title",videoBean.getTitle());
                        context.startActivity(mIntent);
                        break;
//                    case 2:
//                    case 3:
//                        break;

                }
            }
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

        final int tag;
        String type = videoBean.getType();
        if ("ACTIVITY".equalsIgnoreCase(type)) {//打开页面
            tag = 0;
            holder.ivType.setImageResource(R.drawable.home_page_activity);
        } else if ("VIDEO".equalsIgnoreCase(type)) {//首播，点击进去显示MV描述，相关MV
            tag = 1;
            holder.ivType.setImageResource(R.drawable.home_page_video);
        } else if ("WEEK_MAIN_STAR".equalsIgnoreCase(type)) {//(悦单)点击进去跟显示悦单详情一样
            tag = 2;
            holder.ivType.setImageResource(R.drawable.home_page_star);
        } else if ("PLAYLIST".equalsIgnoreCase(type)) {//(悦单)点击进去跟显示悦单详情一样
            tag = 3;
            holder.ivType.setImageResource(R.drawable.home_page_playlist);
        } else if ("AD".equalsIgnoreCase(type)) {
            tag = 4;
            holder.ivType.setImageResource(R.drawable.home_page_ad);
        } else if ("PROGRAM".equalsIgnoreCase(type)) {//跳到MV详情
            tag = 5;
            holder.ivType.setImageResource(R.drawable.home_page_program);
        } else if ("bulletin".equalsIgnoreCase(type)) {
            tag = 6;
            holder.ivType.setImageResource(R.drawable.home_page_bulletin);
        } else if ("fanart".equalsIgnoreCase(type)) {
            tag = 7;
            holder.ivType.setImageResource(R.drawable.home_page_fanart);
        } else if ("live".equalsIgnoreCase(type)) {
            tag = 8;
            holder.ivType.setImageResource(R.drawable.home_page_live);
        } else if ("LIVENEW".equalsIgnoreCase(type) || ("LIVENEWLIST".equals(type))) {
            tag = 9;
            holder.ivType.setImageResource(R.drawable.home_page_live_new);
        } else if ("INVENTORY".equalsIgnoreCase(videoBean.getType())) {//打开页面
            tag = 10;
            holder.ivType.setImageResource(R.drawable.home_page_project);
        } else {
            tag = -100;
            holder.ivType.setImageResource(0);
        }

        // TODO: 2017/9/23 holder.tag = tag; 更新当前条目的类型
        holder.tag = tag;

    }

    @Override
    // TODO: 2017/9/21 HomeAdapter.getItemCount 获取项目的数量 
    public int getItemCount() {
        return videoBeen.size();
    }

}
