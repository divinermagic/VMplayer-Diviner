package a.itcast.mobileplayer95.fragment.vbangpage;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Audio.Media;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import a.itcast.mobileplayer95.Activity.AudioPlayerActivity;
import a.itcast.mobileplayer95.BaseFragment;
import a.itcast.mobileplayer95.R;
import a.itcast.mobileplayer95.adapter.VBangAdapter;
import a.itcast.mobileplayer95.bean.MusicBean;
import a.itcast.mobileplayer95.db.MyAsyncQueryHandler;
import butterknife.Bind;

public class VbangFragment extends BaseFragment {

    private static final String TAG = "VbangFragment";

    @Bind(R.id.listView)
    ListView listView;

    private VBangAdapter vBangAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_vbang;
    }

    @Override
    protected void initView() {

        //显示列表
        vBangAdapter = new VBangAdapter(getContext(), null);
        listView.setAdapter(vBangAdapter);

        //给ListView设置点击事件
        listView.setOnItemClickListener(new OnAudioItemClickListener());

        //查询音乐数据  getContentResolver() 「获取内容解析器」
        ContentResolver resolver = getActivity().getContentResolver();
        //「INTERNAL_CONTENT_URI」 内部内容URI  「EXTERNAL_CONTENT_URI」外部内容URI
        // Cursor cursor = resolver.query(Media.EXTERNAL_CONTENT_URI, new String[]{Media.DATA, Media.SIZE, Media.DISPLAY_NAME, Media.ARTIST}, null, null, null);
        Uri uri = Media.EXTERNAL_CONTENT_URI;

        String[] projection = {Media._ID/*"uid as _id"*/,Media.DATA, Media.SIZE, Media.DISPLAY_NAME, Media.ARTIST};

//        Cursor cursor = resolver.query(uri, projection, null, null, null);
//        Util.printCursor(cursor);

        //异步查询数据库
        MyAsyncQueryHandler asyncQueryHandler = new MyAsyncQueryHandler(resolver);
        // token 用于区分不同类型的数据查询
        // cookie 要使用 cursor 数据的对象
        asyncQueryHandler.startQuery(0, vBangAdapter,uri,projection,null,null,null);
    }

    // TODO: 2017/10/22 音频条目上点击监听器
    private class OnAudioItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            // 这里要传整个播放界面 谁有整个播放界面呢？「Cursor」有
            Cursor cursor = (Cursor) vBangAdapter.getItem(position);

            ArrayList<MusicBean> beanArrayList = MusicBean.listFronCursor(cursor);

            //LogUtils.e(TAG,"OnAudioItemClickListener.onItemClick,beanArrayList="+beanArrayList);
            // FIXME: 2017/10/22 Intent 里面只能传ArrayList 而不能传 List
            Intent intent = new Intent(getContext(), AudioPlayerActivity.class);
            intent.putExtra("data",beanArrayList);//传送列表
            intent.putExtra("position",position);//选中的条目
            startActivity(intent);

        }
    }
}
