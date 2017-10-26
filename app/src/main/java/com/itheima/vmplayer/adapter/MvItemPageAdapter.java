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
import com.itheima.vmplayer.activity.MVDetailActivity;
import com.itheima.vmplayer.bean.VideoBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wschun on 2016/10/2.
 */

public class MvItemPageAdapter extends RecyclerView.Adapter<MvItemPageAdapter.MyViewHolder> {
    private List<VideoBean> videoBeanList;
    private Activity activity;
    private int mWidth,mHeight;
    private LayoutInflater layoutInflater;

    public MvItemPageAdapter(List<VideoBean> videoBeanList, Activity activity, int mWidth, int mHeight) {
        this.videoBeanList = videoBeanList;
        this.activity = activity;
        this.mWidth = mWidth;
        this.mHeight = mHeight;
        layoutInflater=LayoutInflater.from(activity);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_mvitem, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final VideoBean videoBean = videoBeanList.get(position);
        ViewGroup.LayoutParams layoutParams = holder.ivPostimg.getLayoutParams();
        layoutParams.width=mWidth;
        layoutParams.height=mHeight;
        holder.ivPostimg.setLayoutParams(layoutParams);
        holder.viewbg.setLayoutParams(layoutParams);
        holder.name.setText(videoBean.getTitle());
        holder.author.setText(videoBean.getDescription());
        Glide.with(activity).load(videoBean.getAlbumImg()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.ivPostimg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(activity, MVDetailActivity.class);
                intent.putExtra("id", videoBean.getId());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoBeanList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_postimg)
        ImageView ivPostimg;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.author)
        TextView author;
        @Bind(R.id.viewbgs)
        View viewbg;
        @Bind(R.id.play_count)
        TextView playCount;
        @Bind(R.id.rl_item_rootView)
        RelativeLayout rlItemRootView;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
