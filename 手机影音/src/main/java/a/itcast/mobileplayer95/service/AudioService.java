package a.itcast.mobileplayer95.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;

import a.itcast.mobileplayer95.bean.MusicBean;
import a.itcast.mobileplayer95.utils.LogUtils;

/**
 * Created by divinermagic on 2017/10/27.
 */

public class AudioService extends Service {

    private static final String TAG = "AudioService";

    private AudioBinder mAudioBInder;
    private ArrayList<MusicBean> beanArrayList;
    private int position;

    @Override
    public void onCreate() {
        super.onCreate();
        mAudioBInder = new AudioBinder();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        beanArrayList = (ArrayList<MusicBean>) intent.getSerializableExtra("data");
        // FIXME: 2017/10/22 getIntent().getIntExtra("position",-1);「-1」:代表着没有获取到数据
        position = intent.getIntExtra("position", -1);

        //做个健壮性检查「可用性检查」 下面这三个都表示 数据不存在了
        if (beanArrayList == null || beanArrayList.size() == 0 || position == -1) {
            return null;
        }

        //播放选中的歌曲
        mAudioBInder.playItem();

        return mAudioBInder;
    }

    //写一个类 继承 Binder 「Binder：已经实现了IBinder了」
    public class AudioBinder extends Binder {

        private class OnAudioPreparedListener implements MediaPlayer.OnPreparedListener {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                //资源准备完成，开始播放
                mediaPlayer.start();

                // 获取当前正在播放的歌曲
                MusicBean musicBean = beanArrayList.get(position);

                // 通知Activity更新界面 「Service里发广播，Activity需要接收广播」「在Activity.initView的时候接收广播」
                // TODO: 2017/10/27 com.ithiema.audio_prepared:广播的action一般用自家公司的包名来处理
                Intent intent = new Intent("com.ithiema.audio_prepared");

                intent.putExtra("musicBean",musicBean);

                sendBroadcast(intent);

            }
        }

        private MediaPlayer mediaPlayer;

        //播放当前 position 指定的歌曲
        private void playItem() {
            //获取当前要播放的歌曲
            MusicBean musicBean = beanArrayList.get(position);
            LogUtils.e(TAG, "AudioService.onBind,musicBean:" + musicBean);

            //「模拟*用系统的播放器」播放音乐
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(musicBean.path);
                // TODO: 2017/10/22  mediaPlayer.prepare();表示要加载音乐到内存里面去
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new OnAudioPreparedListener());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        //如果当前正在播放歌曲，则暂停；否则就开启播放
        public void switchPauseStatus() {
            if (mediaPlayer.isPlaying()) {
                //正在播放，需要暂停
                mediaPlayer.pause();
            } else {
                //暂停状态，恢复播放
                mediaPlayer.start();
            }
        }

        //返回「true」说明是播放状态
        public boolean isPlaying() {
            return mediaPlayer.isPlaying();
        }

        //停止播放,释放歌曲资源
        public void stoped() {
            mediaPlayer.stop();
        }


    }

}
