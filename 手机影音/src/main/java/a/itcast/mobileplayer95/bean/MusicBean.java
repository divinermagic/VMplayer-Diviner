package a.itcast.mobileplayer95.bean;

import android.database.Cursor;
import android.provider.MediaStore;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wschun on 2016/8/23.
 */
public class MusicBean implements Serializable {
    public String title;
    public String path;
//    public int duration;
    public long size;
    public String artist;

    public static MusicBean fromCursor(Cursor cursor){
        MusicBean musicBean=new MusicBean();
        String name=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
        musicBean.title=name;
        musicBean.path=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
//        musicBean.duration=cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
        musicBean.size=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
        musicBean.artist=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
        return  musicBean;
    }

    public static ArrayList<MusicBean> listFronCursor(Cursor cursor){

        ArrayList<MusicBean> beanArrayList = new ArrayList<>();

        //解析之前要把 cursor 移动到第一行之前 「第一行是:0」「之前是:-1」
       cursor.moveToPosition(-1);

        //遍历Curson里面的所有行，并挨个的解析
        while (cursor.moveToNext()){
            MusicBean musicBean = fromCursor(cursor);
            beanArrayList.add(musicBean);
        }
        return beanArrayList;
    }

    @Override
    public String toString() {
        return "MusicBean{" +
                "title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", size=" + size +
                ", artist='" + artist + '\'' +
                '}';
    }
}
