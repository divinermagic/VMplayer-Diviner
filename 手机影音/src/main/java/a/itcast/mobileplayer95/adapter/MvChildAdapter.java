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
 * 作者：Magic on 2017/9/22 22:30
 * 邮箱：bonian1852@163.com
 */

public class MvChildAdapter extends RecyclerView.Adapter<MvChildAdapter.MyViewHolder> {

    private List<VideoBean> videos;

    public MvChildAdapter(List<VideoBean> videos) {
        this.videos = videos;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_postimg)
        ImageView ivPostimg;
        @Bind(R.id.viewbgs)
        View viewbgs;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.author)
        TextView author;
        @Bind(R.id.play_count)
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
