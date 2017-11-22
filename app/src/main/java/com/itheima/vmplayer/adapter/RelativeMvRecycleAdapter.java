package com.itheima.vmplayer.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itheima.vmplayer.R;
import com.itheima.vmplayer.activity.MVDetailActivity;
import com.itheima.vmplayer.bean.MVDetailBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.Wang
 * Date  2016/9/6.
 * Email 1198190260@qq.com
 */
public class RelativeMvRecycleAdapter extends RecyclerView.Adapter<RelativeMvRecycleAdapter.RelativeViewHolder> {

    private Activity activity;
    private List<MVDetailBean.RelatedVideosBean> relatedVideosBeen;

    public RelativeMvRecycleAdapter(Activity activity, List<MVDetailBean.RelatedVideosBean> relatedVideosBeen) {
        this.activity = activity;
        this.relatedVideosBeen = relatedVideosBeen;
    }

    @Override
    public RelativeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.relative_item, parent, false);
        return new RelativeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RelativeViewHolder holder, int position) {
        final MVDetailBean.RelatedVideosBean relatedVideosBean = relatedVideosBeen.get(position);
        Glide.with(activity).load(relatedVideosBean.getPosterPic()).placeholder(R.drawable.empty_logo).centerCrop().into(holder.ivPostimg);
        holder.tvTitle.setText(relatedVideosBean.getTitle());
        holder.tvCount.setText("播放次数:"+relatedVideosBean.getTotalViews());
        holder.tvArtistName.setText(relatedVideosBean.getArtistName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(activity, MVDetailActivity.class);
                intent.putExtra("id", relatedVideosBean.getId());
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return relatedVideosBeen.size();
    }

    class RelativeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_postimg)
        ImageView ivPostimg;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_artist_name)
        TextView tvArtistName;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.rl_item_root)
        LinearLayout rlItemRoot;
        public RelativeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
