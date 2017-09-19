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


import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Wang
 * Date  2016/9/4.
 * Email 1198190260@qq.com
 */
public class MVRecycleViewAdapter extends RecyclerView.Adapter<MVRecycleViewAdapter.MvViewHolder> {

    private ArrayList<VideoBean> videoBeanArrayList=new ArrayList<>();
    private Activity activity;
    private RelativeLayout.LayoutParams layoutParams;

    public MVRecycleViewAdapter(ArrayList<VideoBean> videoBeanArrayList, Activity activity, int mWidth, int mHeight) {
        this.videoBeanArrayList = videoBeanArrayList;
        this.activity = activity;
        layoutParams = new RelativeLayout.LayoutParams(mWidth, mHeight);

    }

    @Override
    public MvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_mvitem, parent, false);
        return new MvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MvViewHolder holder, int position) {
        final VideoBean videoBean = videoBeanArrayList.get(position);
        holder.rlItemRootView.setLayoutParams(layoutParams);
        holder.name.setText(videoBean.getTitle());
        holder.author.setText(videoBean.getDescription());
        if (videoBean.isAd()){
            Glide.with(activity).load(videoBean.getThumbnailPic()).diskCacheStrategy(DiskCacheStrategy.ALL).
                    into(holder.ivPostimg);
            holder.playCount.setText("");
        }else {
            Glide.with(activity).load(videoBean.getAlbumImg()).diskCacheStrategy(DiskCacheStrategy.ALL).
                    into(holder.ivPostimg);
            holder.playCount.setText("播放次数"+videoBean.getTotalViews());
        }
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
        return videoBeanArrayList.size();
    }

    class MvViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_postimg)
        ImageView ivPostimg;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.author)
        TextView author;
        @Bind(R.id.play_count)
        TextView playCount;
        @Bind(R.id.rl_item_rootView)
        RelativeLayout rlItemRootView;
        public MvViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
