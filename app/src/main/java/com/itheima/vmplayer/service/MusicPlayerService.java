package com.itheima.vmplayer.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.itheima.vmplayer.bean.MusicBean;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by wschun on 2016/10/1.
 */

public class MusicPlayerService extends Service {

    private MusicProxy musicProxy;
    private List<MusicBean> musicBeanList;
    private int position;
    private MediaPlayer mediaPlayer;

    public static final int ORDER=1;
    public static final int RANDOM=2;
    public static final int SINGLE=3;
    public static  int currenMode=ORDER;
    private SharedPreferences preferences;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return musicProxy;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        musicProxy = new MusicProxy();
        preferences = getSharedPreferences("mode_config", Context.MODE_PRIVATE);
        currenMode=preferences.getInt("mode",ORDER);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            musicBeanList = (List<MusicBean>) intent.getSerializableExtra("musicList");
            position = intent.getIntExtra("position", -1);
        }
        return super.onStartCommand(intent, flags, startId);
    }


    public class MusicProxy extends Binder {

        public void play() {
            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            }

            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(musicBeanList.get(position).path);
                mediaPlayer.setOnPreparedListener(new MyOnPreparedListener());
                mediaPlayer.setOnCompletionListener(new MyOnCompletionListener());
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        public long getCurrentPosition() {
            if (mediaPlayer != null)
                return mediaPlayer.getCurrentPosition();
            return 0;
        }

        public long getDuration() {
            if (mediaPlayer != null)
                return mediaPlayer.getDuration();
            return 0;
        }

        public void seekTo(int progress) {
            if (mediaPlayer!=null){
                mediaPlayer.seekTo(progress);
            }
        }

        public void togglePlay() {
            if (mediaPlayer!=null){
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }else {
                    mediaPlayer.start();
                }
            }
        }

        public boolean isPlaying() {
            return mediaPlayer!=null && mediaPlayer.isPlaying();
        }

        public void stop() {
            if (mediaPlayer!=null){
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer=null;
            }
        }
        public void playPre() {
            if (position>0){
                position--;
                play();
            }
        }

        public boolean isFrist() {
            return position==0;
        }

        public boolean isLast() {
            return position==musicBeanList.size()-1;
        }

        public void playNext() {
            if (position<musicBeanList.size()-1){
                position++;
                play();
            }
        }

        public void start() {
            if (mediaPlayer!=null){
                mediaPlayer.start();
                sendStartPlay();
            }
        }

        public void switchPlayMode() {
            switch (currenMode){
                case ORDER:
                    currenMode=RANDOM;
                    break;
                case RANDOM:
                    currenMode=SINGLE;
                    break;
                case SINGLE:
                    currenMode=ORDER;
                    break;
            }
            SharedPreferences.Editor edit = preferences.edit();
            edit.putInt("mode",currenMode);
            edit.commit();

        }

        private class MyOnCompletionListener implements MediaPlayer.OnCompletionListener {
            @Override
            public void onCompletion(MediaPlayer mp) {
                sendPlayComplete();
                playByMode();
            }


        }
    }

    private void playByMode() {
        switch (currenMode){
            case ORDER:
                if (position==musicBeanList.size()-1){
                    position=0;
                    musicProxy.play();

                }else {
                    position++;
                    musicProxy.play();
                }
                break;
            case RANDOM:
                position=new Random().nextInt(musicBeanList.size());
                musicProxy.play();
                break;
            case SINGLE:
                musicProxy.start();
                break;
        }
    }


    private void sendPlayComplete() {
        Intent mIntent = new Intent();
        mIntent.setAction("music_complete");
        sendBroadcast(mIntent);
    }

    private class MyOnPreparedListener implements MediaPlayer.OnPreparedListener {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mediaPlayer.start();
            sendStartPlay();
        }
    }

    private void sendStartPlay() {
        Intent mIntent = new Intent();
        mIntent.setAction("music_startplay");
        mIntent.putExtra("MusicBean", musicBeanList.get(position));
        sendBroadcast(mIntent);
    }

}
