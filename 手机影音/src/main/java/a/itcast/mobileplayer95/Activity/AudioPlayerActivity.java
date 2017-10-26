package a.itcast.mobileplayer95.Activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

import a.itcast.mobileplayer95.R;
import a.itcast.mobileplayer95.bean.MusicBean;
import a.itcast.mobileplayer95.utils.LogUtils;

public class AudioPlayerActivity extends AppCompatActivity {

    private static final String TAG = "AudioPlayerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);

        initView();
        initData();

    }

    private void initView() {

    }

    private void initData() {

        ArrayList<MusicBean> beanArrayList = (ArrayList<MusicBean>) getIntent().getSerializableExtra("data");
        // FIXME: 2017/10/22 getIntent().getIntExtra("position",-1);「-1」:代表着没有获取到数据
        int position = getIntent().getIntExtra("position",-1);
        //做个健壮性检查「可用性检查」 下面这三个都表示 数据不存在了
        if (beanArrayList==null||beanArrayList.size()==0||position==-1){
            return;
        }
        //获取当前要播放的歌曲
        MusicBean musicBean = beanArrayList.get(position);
        LogUtils.e(TAG,"AudioPlayerActivity.initData,musicBean:"+musicBean);

        //「模拟*用系统的播放器」播放音乐
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(musicBean.path);
            // TODO: 2017/10/22  mediaPlayer.prepare();表示要加载音乐到内存里面去
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
