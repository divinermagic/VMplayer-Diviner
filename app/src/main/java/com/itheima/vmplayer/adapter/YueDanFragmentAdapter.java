package com.itheima.vmplayer.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.itheima.vmplayer.R;
import com.itheima.vmplayer.activity.YueDanDetailActivity;
import com.itheima.vmplayer.bean.YueDanBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mr.Wang
 * Date  2016/9/5.
 * Email 1198190260@qq.com
 */
public class YueDanFragmentAdapter extends RecyclerView.Adapter<YueDanFragmentAdapter.YueDanViewHolder> {
    private List<YueDanBean.PlayListsBean> playListsBeen;
    private Activity activity;
    private RelativeLayout.LayoutParams layoutParams;
    private int mWidth,mHeight;

    public YueDanFragmentAdapter(List<YueDanBean.PlayListsBean> playListsBeen, Activity activity, int mWidth, int mHeight) {
        this.playListsBeen = playListsBeen;
        this.activity = activity;
        this.mWidth=mWidth;
        this.mHeight=mHeight;


    }

    @Override
    public YueDanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_yuedan, parent, false);
        return new YueDanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(YueDanViewHolder holder, int position) {
        final YueDanBean.PlayListsBean playListsBean = playListsBeen.get(position);
        ViewGroup.LayoutParams layoutParams = holder.ivPostimg.getLayoutParams();
        layoutParams.width=mWidth;
        layoutParams.height=mHeight;
        holder.itemView.setLayoutParams(layoutParams);
        holder.ivPostimg.setLayoutParams(layoutParams);
        holder.title.setText(playListsBean.getTitle());
        holder.author.setText(playListsBean.getCreator().getNickName());
        holder.playCount.setText("收录高清MV"+playListsBean.getVideoCount()+"首");
        Glide.with(activity).load(playListsBean.getThumbnailPic()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.ivPostimg);
        Glide.with(activity).load(playListsBean.getCreator().getLargeAvatar()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.civImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(activity, YueDanDetailActivity.class);
                intent.putExtra("id",playListsBean.getId());
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return playListsBeen.size();
    }

    class YueDanViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_postimg)
        ImageView ivPostimg;
        @BindView(R.id.civ_img)
        CircleImageView civImg;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.author)
        TextView author;
        @BindView(R.id.play_count)
        TextView playCount;

        public YueDanViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
