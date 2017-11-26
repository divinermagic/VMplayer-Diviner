package a.itcast.mobileplayer95.adapter;

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
import a.itcast.mobileplayer95.R;
import a.itcast.mobileplayer95.bean.VideoBean;
import a.itcast.mobileplayer95.utils.Util;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Magic on 2017/9/22 22:30
 * 邮箱：bonian1852@163.com
 */

public class MvChildAdapter extends RecyclerView.Adapter<MvChildAdapter.MyViewHolder> {

    private List<VideoBean> videos;

    public MvChildAdapter(List<VideoBean> videos) {
        this.videos = videos;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_postimg)
        ImageView ivPostimg;
        @BindView(R.id.viewbgs)
        View viewbgs;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.author)
        TextView author;
        @BindView(R.id.play_count)
        TextView playCount;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            //修改图片大小
            Point point = Util.computeImgSize(240,135,itemView.getContext());
            ivPostimg.getLayoutParams().width = point.x;
            ivPostimg.getLayoutParams().height = point.y;
            ivPostimg.requestLayout();

            //修改覆盖图 大小
            viewbgs.getLayoutParams().width = point.x;
            viewbgs.getLayoutParams().height = point.y;
            viewbgs.requestLayout();

            // TODO: 2017/10/17 注册当前条目的点击监听
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //获取被点击条目的数据
                    VideoBean videoBean = videos.get(MyViewHolder.this.getAdapterPosition());

                    //跳转到播放界面
                    Intent intent = new Intent(view.getContext(), PlayerActivity.class);

                    intent.putExtra("url",videoBean.getUrl());
                    intent.putExtra("title",videoBean.getTitle());

                    view.getContext().startActivity(intent);

                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.fragment_mvitem, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //获取当前条目的数据
        VideoBean videoBean = videos.get(position);
        //填充文字
        holder.name.setText(videoBean.getTitle());
        holder.author.setText(videoBean.getArtistName());
        holder.playCount.setText(videoBean.getDescription());
        //填充图片
        Glide.with(holder.itemView.getContext()).load(videoBean.getPosterPic()).into(holder.ivPostimg);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

}
