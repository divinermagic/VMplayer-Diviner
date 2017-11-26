package a.itcast.mobileplayer95.adapter;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.widget.CursorAdapter;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import a.itcast.mobileplayer95.R;
import a.itcast.mobileplayer95.utils.Util;
import butterknife.BindView;
import butterknife.ButterKnife;


public class VBangAdapter extends CursorAdapter {

    public VBangAdapter(Context context, Cursor c) {
        super(context, c);
    }

    public VBangAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public VBangAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }


    @Override
    /**
     *  创建新的条目View 并且 初始化 ViewHolder
     */
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = View.inflate(context, R.layout.item_music, null);

        ViewHolder holder = new ViewHolder(view);

        view.setTag(holder);

        return view;
    }

    @Override
    /**
     * 填充条目内容
     */
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder holder = (ViewHolder) view.getTag();
        String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));

        //标题 提取下  把后缀名给去掉「.mp3」等等
        title = Util.formatName(title);

        holder.tvTitle.setText(title);
        //艺术家
        holder.tvArtist.setText(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
        //大小
        long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
        // TODO: 2017/10/17 Formatter.formatFileSize(context,size) 直接格式化文本大小
        holder.tvSize.setText(Formatter.formatFileSize(context,size));

    }

    static class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_artist)
        TextView tvArtist;
        @BindView(R.id.tv_size)
        TextView tvSize;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
