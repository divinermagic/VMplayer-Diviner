package a.itcast.mobileplayer95.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import a.itcast.mobileplayer95.bean.MusicBean;
import a.itcast.mobileplayer95.utils.LogUtils;


public class AudioService extends Service {

    private static final String TAG = "AudioService";

    //常量快捷键 const
    public static final int PLAYMODE_ALL = 0;

    public static final int PLAYMODE_SINGLE = 1;

    public static final int PLAYMODE_RANDOM = 2;

    private int mPlayMode = PLAYMODE_ALL;

    private AudioBinder mAudioBInder;
    private ArrayList<MusicBean> beanArrayList;
    private int position;

    @Override
    public void onCreate() {
        super.onCreate();
        mAudioBInder = new AudioBinder();

        mPlayMode = getSharedPreferences("config",MODE_PRIVATE).getInt("playmode",PLAYMODE_ALL);
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

    /**
     * 写一个类 继承 Binder 「Binder：已经实现了IBinder了」
     */
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

                intent.putExtra("musicBean", musicBean);

                sendBroadcast(intent);

            }
        }

        private class OnAudioCompletionListener implements MediaPlayer.OnCompletionListener {
            @Override
            /**
             * 歌曲播放结束
             */
            public void onCompletion(MediaPlayer mediaPlayer) {
                audioPlayNext();
            }
        }

        private MediaPlayer mediaPlayer;

        /**
         * 播放当前 position 指定的歌曲
         */
        private void playItem() {
            //获取当前要播放的歌曲
            MusicBean musicBean = beanArrayList.get(position);
            LogUtils.e(TAG, "AudioService.onBind,musicBean:" + musicBean);

            //「模拟*用系统的播放器」播放音乐
            try {
                if (mediaPlayer != null) {
                    mediaPlayer.reset();
                } else {
                    mediaPlayer = new MediaPlayer();
                }
                mediaPlayer.setDataSource(musicBean.path);
                // TODO: 2017/10/22  mediaPlayer.prepare();表示要加载音乐到内存里面去
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new OnAudioPreparedListener());

                mediaPlayer.setOnCompletionListener(new OnAudioCompletionListener());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        /**
         * 如果当前正在播放歌曲，则暂停；否则就开启播放
         */
        public void switchPauseStatus() {
            if (mediaPlayer.isPlaying()) {
                //正在播放，需要暂停
                mediaPlayer.pause();
            } else {
                //暂停状态，恢复播放
                mediaPlayer.start();
            }
        }

        /**
         * 返回「true」说明是播放状态
         *
         * @return true 说明是播放状态
         */
        public boolean isPlaying() {
            return mediaPlayer.isPlaying();
        }

        /**
         * 停止播放,释放歌曲资源
         */
        public void stoped() {
            mediaPlayer.stop();
        }

        /**
         * 返回 音乐的总时长  getDuration:总长度
         *
         * @return 音乐的总时长
         */
        public int getDuration() {
            return mediaPlayer.getDuration();
        }

        /**
         * 返回 音乐的播放进度  CurrentPosition:当前位置
         *
         * @return 音乐的播放进度
         */
        public int getPosition() {
            return mediaPlayer.getCurrentPosition();
        }

        /**
         * 跳转到指定毫秒处 播放
         *
         * @param msec
         */
        public void seekTo(int msec) {
            mediaPlayer.seekTo(msec);
        }

        /**
         * 播放上一首歌曲
         */
        public void playPre() {
            if (position != 0) {
                position--;
                playItem();
            } else {
                Toast.makeText(AudioService.this, "已经是第一首了", Toast.LENGTH_SHORT).show();
            }

        }

        /**
         * 播放下一首歌曲
         */
        public void playNext() {
            if (position != beanArrayList.size() - 1) {
                position++;
                playItem();
            } else {
                Toast.makeText(AudioService.this, "已经是最后一首了", Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * 切换下 播放模式
         * // TODO: 2017/11/21 按照 顺序播放 -- 单曲循环 -- 随机播放 依次切换
         */
        public void switchPlayMode() {
            switch (mPlayMode) {
                case PLAYMODE_ALL:
                    mPlayMode = PLAYMODE_SINGLE;
                    break;
                case PLAYMODE_SINGLE:
                    mPlayMode = PLAYMODE_RANDOM;
                    break;
                case PLAYMODE_RANDOM:
                    mPlayMode = PLAYMODE_ALL;
                    break;
                default:
                    break;
            }
            Log.e(TAG, "switchPlayMode: 当前播放模式:" + mPlayMode);

            //保存播放顺序到配置文件
            SharedPreferences config = getSharedPreferences("config", MODE_PRIVATE);
            SharedPreferences.Editor edit = config.edit();
            edit.putInt("playmode",mPlayMode);
            edit.commit();

        }

        /**
         * 返回当前正在使用的播放模式
         *
         * @return 返回当前正在使用的播放模式
         */
        public int getPlayMode() {
            return mPlayMode;
        }


        /**
         * 根据播放模式 自动播放下一首歌
         */
        private void audioPlayNext() {
            switch (mPlayMode) {
                case PLAYMODE_ALL:
                    if (position != beanArrayList.size()-1) {
                        position++;
                    } else {
                        position = 0;
                    }
                    playItem();
                    break;
                case PLAYMODE_SINGLE:
                    playItem();
                    break;
                case PLAYMODE_RANDOM:
                    position = new Random().nextInt(beanArrayList.size());
                    playItem();
                    break;
                default:
                    break;
            }
            //播放当前选中的歌曲
            playItem();
        }


    }

}
