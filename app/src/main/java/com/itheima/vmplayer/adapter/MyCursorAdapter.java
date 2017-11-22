package com.itheima.vmplayer.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.vmplayer.R;
import com.itheima.vmplayer.bean.MusicBean;
import com.itheima.vmplayer.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wschun on 2016/9/30.
 */

public class MyCursorAdapter extends CursorAdapter {

    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_music, null);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = ViewHolder.getViewHolder(view);
        MusicBean musicBean = MusicBean.fromCursor(cursor);
        viewHolder.tvTitle.setText(Util.formatName(musicBean.title));
        viewHolder.tvDuration.setText(musicBean.artist);
        viewHolder.tvSize.setText(Formatter.formatFileSize(context,musicBean.size));
    }

    static class ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_duration)
        TextView tvDuration;
        @BindView(R.id.tv_size)
        TextView tvSize;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public static ViewHolder getViewHolder(View view){
            ViewHolder viewHolder= (ViewHolder) view.getTag();
            if (viewHolder==null){
                viewHolder=new ViewHolder(view);
                view.setTag(viewHolder);
            }
            return viewHolder;
        }
    }
}
