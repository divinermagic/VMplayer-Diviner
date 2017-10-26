package com.itheima.vmplayer.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.itheima.vmplayer.activity.WebActivity;
import com.itheima.vmplayer.activity.YueDanDetailActivity;
import com.itheima.vmplayer.bean.VideoBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wschun on 2016/10/1.
 */

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.MyViewholder> {
    private List<VideoBean>  videoBeanList;
    private Activity activity;
    private int mWidth,mHeight;
    private LayoutInflater layoutInflater;

    public HomePageAdapter(List<VideoBean> videoBeanList, Activity activity, int mWidth, int mHeight) {
        this.videoBeanList = videoBeanList;
        this.activity = activity;
        this.mWidth = mWidth;
        this.mHeight = mHeight;
        layoutInflater=LayoutInflater.from(activity);
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_item, parent, false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {
        final VideoBean videoBean = videoBeanList.get(position);
        ViewGroup.LayoutParams layoutParams = holder.ivContentimg.getLayoutParams();
        layoutParams.width=mWidth;
        layoutParams.height=mHeight;
        holder.viewBg.setLayoutParams(layoutParams);
        holder.tvTitle.setText(videoBean.getTitle());
        holder.tvDescription.setText(videoBean.getDescription());
        Glide.with(activity).load(videoBean.getPosterPic()).diskCacheStrategy(DiskCacheStrategy.ALL).override(mWidth,mHeight).into(holder.ivContentimg);
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent=new Intent();
                switch (tag){
                    case 0:
                    case 4:
                    case 10:
                        mIntent.setClass(activity, WebActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("url",videoBean.getUrl());
                        mIntent.putExtras(bundle);
                        break;
                    case 1:
                    case 5:
                    case 7:
                        mIntent.setClass(activity, MVDetailActivity.class);
                        mIntent.putExtra("id",videoBean.getId());
                        break;

                    case 2:
                    case 3:
                        mIntent.setClass(activity, YueDanDetailActivity.class);
                        mIntent.putExtra("id",videoBean.getId());
                        break;

                }
                activity.startActivity(mIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return videoBeanList.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_item_logo)
        ImageView ivItemLogo;
        @Bind(R.id.iv_contentimg)
        ImageView ivContentimg;
        @Bind(R.id.iv_type)
        ImageView ivType;
        @Bind(R.id.viewbg)
         View viewBg;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_description)
        TextView tvDescription;
        @Bind(R.id.rl_item_rootView)
        RelativeLayout rlItemRootView;

        public MyViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
