package com.itheima.vmplayer.fragment.vbangpage;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.CursorAdapter;

/**
 * Created by wschun on 2016/9/30.
 */

public class VbangPresenter implements VbangContract.Prestener {

    private VbangContract.View view;

    public VbangPresenter(VbangContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void getData(int offest, int size) {

    }

    @Override
    public void query(Context content, CursorAdapter cursorAdapter) {


        //节目的数据怎么来呢，我们手机上很多多媒体的数据，比如说，图片，视频，音乐呢
        //都是以数据库的方式进行存储，系统内部又通过内容提供者暴露给第三方调用者，那介于这样一个道理呢
        //我们就要通过内容能提供者进行数据的访问。
        //ContentResolver.底层就是封装了对数据库的增删改查
        Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection={
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST
        };
        //查询的路径
        //查询的列，需要的字段
        //查询条件，
        //查询条件的参数
        //排序
//        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
//        Util.printCursor(cursor);

        MyQueryHelp myQueryHelp = new MyQueryHelp(content.getContentResolver(),view);
        myQueryHelp.startQuery(0,cursorAdapter,uri,projection,null,null,null);
    }
}
