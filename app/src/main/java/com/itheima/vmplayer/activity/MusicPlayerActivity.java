package com.itheima.vmplayer.activity;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.vmplayer.R;
import com.itheima.vmplayer.bean.MusicBean;
import com.itheima.vmplayer.lyric.Lyrics;
import com.itheima.vmplayer.service.MusicPlayerService;
import com.itheima.vmplayer.util.Util;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MusicPlayerActivity extends AppCompatActivity {


    private static final String TAG ="MusicPlayerActivity" ;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_music_name)
    TextView tvMusicName;
    @Bind(R.id.tv_artist_name)
    TextView tvArtistName;


    @Bind(R.id.activity_music_player)
    LinearLayout activityMusicPlayer;
    @Bind(R.id.iv_music_anim)
    ImageView ivMusicAnim;
    @Bind(R.id.lyricsView)
    Lyrics lyricsView;
    @Bind(R.id.tv_play_time)
    TextView tvPlayTime;
    @Bind(R.id.sb_music)
    SeekBar sbMusic;
    @Bind(R.id.btn_playmode)
    ImageView btnPlaymode;
    @Bind(R.id.btn_music_pre)
    ImageView btnMusicPre;
    @Bind(R.id.btn_music_play)
    ImageView btnMusicPlay;
    @Bind(R.id.btn_music_next)
    ImageView btnMusicNext;
    private MusicPlayerService.MusicProxy musicProxy;
    private MusicBroadCast musicBroadCast;
    private AnimationDrawable background;
    private MusicServiceConnection musicServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        ButterKnife.bind(this);
        initView();
        /**
         * 注册广播
         */
        registerReceivers();

        List<MusicBean> musicBeanList = (List<MusicBean>) getIntent().getSerializableExtra("musicList");
        int position = getIntent().getIntExtra("position", -1);

        Intent mIntent = new Intent(MusicPlayerActivity.this, MusicPlayerService.class);
        mIntent.putExtra("position", position);
        mIntent.putExtra("musicList", (Serializable) musicBeanList);
        musicServiceConnection = new MusicServiceConnection();
        startService(mIntent);
        bindService(mIntent, musicServiceConnection, Service.BIND_AUTO_CREATE);
    }

    private void initView() {
        background = (AnimationDrawable) ivMusicAnim.getBackground();
        sbMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    musicProxy.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void registerReceivers() {
        musicBroadCast = new MusicBroadCast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("music_startplay");
        intentFilter.addAction("music_complete");
        registerReceiver(musicBroadCast, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(musicBroadCast);
        unbindService(musicServiceConnection);
    }

    class MusicBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("music_startplay".equals(intent.getAction())) {
                MusicBean musicBean = (MusicBean) intent.getSerializableExtra("MusicBean");
                setMusciView(musicBean);
            } else if ("music_complete".equals(intent.getAction())) {
                setPlayComplete();
            }
        }
    }

    private void setPlayComplete() {
        btnMusicPlay.setBackgroundResource(R.drawable.selector_btn_audio_pause);
        background.stop();
        handler.removeMessages(UPDATETIMEANDSB);

    }

    private static final int UPDATETIMEANDSB = 1;
    private static final int SCROLLLYRIC =2 ;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATETIMEANDSB:
                    updateTimeAndSB();
                    break;
                case SCROLLLYRIC:
                    startScrollLyric();
                    break;
            }
        }
    };

    private void setMusciView(MusicBean musicBean) {

        tvMusicName.setText(Util.formatName(musicBean.title));
        tvArtistName.setText(musicBean.artist);
        btnMusicPlay.setBackgroundResource(R.drawable.selector_btn_audio_play);
        background.start();
        sbMusic.setMax((int) musicProxy.getDuration());
        tvPlayTime.setText(Util.formatDuration(musicProxy.getCurrentPosition()) + "/" + Util.formatDuration(musicProxy.getDuration()));
        updateTimeAndSB();
        updatePlayModeIcon();
        File file= new File(Environment.getExternalStorageDirectory()+"/test/audio/"+Util.formatName(musicBean.title)+".lrc");
//        Log.i(TAG, "setMusciView: "+Environment.getExternalStorageDirectory()+"/test/audio/"+Util.formatName(musicBean.title)+".lrc");
        lyricsView.setLyric(file);
        startScrollLyric();

    }

    private void startScrollLyric() {
       lyricsView.scroll(musicProxy.getCurrentPosition(),musicProxy.getDuration());
        handler.sendEmptyMessage(SCROLLLYRIC);
    }

    private void updateTimeAndSB() {

        tvPlayTime.setText(Util.formatDuration(musicProxy.getCurrentPosition()) + "/" + Util.formatDuration(musicProxy.getDuration()));
        sbMusic.setProgress((int) musicProxy.getCurrentPosition());
        handler.sendEmptyMessageDelayed(UPDATETIMEANDSB, 200);

    }


    @OnClick({R.id.btn_playmode, R.id.btn_music_pre, R.id.btn_music_play, R.id.btn_music_next, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_playmode:
                musicProxy.switchPlayMode();
                updatePlayModeIcon();
                break;
            case R.id.iv_back:
                musicProxy.stop();
                finish();
                break;
            case R.id.btn_music_pre:
                if (musicProxy.isFrist()) {
                    Toast.makeText(MusicPlayerActivity.this, "当前已经是第一首歌曲", Toast.LENGTH_SHORT).show();
                } else
                    musicProxy.playPre();
                break;
            case R.id.btn_music_play:
                musicProxy.togglePlay();
                if (musicProxy.isPlaying()) {
                    btnMusicPlay.setBackgroundResource(R.drawable.selector_btn_audio_play);
                    background.start();
                } else {

                    btnMusicPlay.setBackgroundResource(R.drawable.selector_btn_audio_pause);
                    background.stop();
                }
                break;
            case R.id.btn_music_next:
                if (musicProxy.isLast()) {
                    Toast.makeText(MusicPlayerActivity.this, "当前已经是最后一首歌曲", Toast.LENGTH_SHORT).show();
                } else
                    musicProxy.playNext();
                break;
        }
    }

    private void updatePlayModeIcon() {
        switch (MusicPlayerService.currenMode){
            case MusicPlayerService.ORDER:
                btnPlaymode.setBackgroundResource(R.drawable.selector_btn_playmode_order);
                break;
            case MusicPlayerService.RANDOM:
                btnPlaymode.setBackgroundResource(R.drawable.selector_btn_playmode_random);
                break;
            case MusicPlayerService.SINGLE:
                btnPlaymode.setBackgroundResource(R.drawable.selector_btn_playmode_single);
                break;

        }
    }

    class MusicServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicProxy = (MusicPlayerService.MusicProxy) service;
            musicProxy.play();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
