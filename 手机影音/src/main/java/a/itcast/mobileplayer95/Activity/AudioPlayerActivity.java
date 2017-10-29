package a.itcast.mobileplayer95.Activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import a.itcast.mobileplayer95.R;
import a.itcast.mobileplayer95.bean.MusicBean;
import a.itcast.mobileplayer95.service.AudioService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AudioPlayerActivity extends AppCompatActivity {

    private static final String TAG = "AudioPlayerActivity";
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_artist)
    TextView tvArtist;
    @BindView(R.id.tv_position)
    TextView tvPosition;
    @BindView(R.id.sk_position)
    SeekBar skPosition;
    @BindView(R.id.iv_playmode)
    ImageView ivPlaymode;
    @BindView(R.id.iv_pre)
    ImageView ivPre;
    @BindView(R.id.iv_pause)
    ImageView ivPause;
    @BindView(R.id.iv_next)
    ImageView ivNext;
    @BindView(R.id.iv_list)
    ImageView ivList;
    @BindView(R.id.iv_wave)
    ImageView ivWave;


    // TODO: 2017/10/27 ServiceConnection 服务连接
    private ServiceConnection conn;
    private AudioService.AudioBinder audioBinder;
    private AudioReceiver audioreceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);
        ButterKnife.bind(this);

        initView();
        initData();

    }

    private void initView() {
        // TODO: 2017/10/27 IntentFilter:意图过滤器 是在清单文件中使用的「用来注册各种action,data,category的」 
        // TODO: 2017/10/27 registerReceiver:注册接收
        IntentFilter filter = new IntentFilter("com.ithiema.audio_prepared");
        audioreceiver = new AudioReceiver();
        registerReceiver(audioreceiver, filter);//广播有初始化，就必须要有注销
    }

    private void initData() {
        // TODO: 2017/10/27  AudioPlayerActivity.initData() 开启播放服务
        // TODO: 2017/10/27 下面两句的代码意思就是：把当前Activity的Intent复制成一个新的对象，所有的数据生成一个新的对象，然后把启动的目标给换掉
        Intent service = new Intent(getIntent());
        service.setClass(this, AudioService.class);

        conn = new AudioServiceConnection();
        // TODO: 2017/10/27 BIND_AUTO_CREATE:自动创建绑定
        bindService(service, conn, BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑服务「有绑定 就要有解绑」
        unbindService(conn);
        //当我销毁界面时，注销广播「反注册广播」
        unregisterReceiver(audioreceiver);
        //停止播放歌曲
        audioBinder.stoped();
    }

    //切换 「暂停/播放」 状态
    private void switchPauseStatus() {
        audioBinder.switchPauseStatus();

        // TODO: 2017/10/27 updataPauseBtn:更新暂停按钮的图片
        updataPauseBtn();
    }

    /**
     * 把更新界面的代码和功能代码分离，使用起来特别方便。
     * 更新暂停按钮的图片 并 同步开启示波器的动画
     */
    private void updataPauseBtn() {

        // TODO: 2017/10/27 获取到示波器 帧动画的对象
        AnimationDrawable anim = (AnimationDrawable) ivWave.getDrawable();

        if (audioBinder.isPlaying()) {
            ivPause.setImageResource(R.drawable.selector_btn_audio_play);
            //开启「示波器」动画
            anim.start();
        } else {
            ivPause.setImageResource(R.drawable.selector_btn_audio_pause);
            //停止「示波器」动画
            anim.stop();
        }
    }

    @OnClick({R.id.iv_back, R.id.iv_playmode, R.id.iv_pre, R.id.iv_pause, R.id.iv_next, R.id.iv_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_playmode:
                break;
            case R.id.iv_pre:
                break;
            case R.id.iv_pause:
                switchPauseStatus();
                break;
            case R.id.iv_next:
                break;
            case R.id.iv_list:
                break;
        }
    }

    private class AudioServiceConnection implements ServiceConnection {
        @Override
        //TODO: 2017/10/27 onServiceConnected 服务绑定
        public void onServiceConnected(ComponentName componentName, IBinder service) {

            // TODO: 2017/10/27 AudioService.AudioBinder audioBinder = (AudioService.AudioBinder) service; AudioService.AudioBinder：提取成成员变量
            audioBinder = (AudioService.AudioBinder) service;
            //audioBinder.haha();

        }

        @Override
        //TODO: 2017/10/27 onServiceDisconnected 服务解绑
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }

    private class AudioReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("com.ithiema.audio_prepared".equals(action)) {

                //音乐开始播放
                updataPauseBtn();

                //获取当前正在播放的歌曲
                MusicBean musicBean = (MusicBean) intent.getSerializableExtra("musicBean");

                //更新标题和歌手名
                tvTitle.setText(musicBean.title);
                tvArtist.setText(musicBean.artist);

                // 开启示波器动画 「写在这的话 停止音乐的时候，也在动 所以写在了 updataPauseBtn()里」
                // TODO: 2017/10/27 获取到示波器 帧动画的对象
                // AnimationDrawable anim = (AnimationDrawable) ivWave.getDrawable();
                // anim.start();

            }
        }
    }
}
