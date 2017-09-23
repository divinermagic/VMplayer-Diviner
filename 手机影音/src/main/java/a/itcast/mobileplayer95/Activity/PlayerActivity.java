package a.itcast.mobileplayer95.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import a.itcast.mobileplayer95.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class PlayerActivity extends AppCompatActivity {

    @Bind(R.id.jiecaoplayer)
    JCVideoPlayerStandard jiecaoplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        jiecaoplayer.setUp(url,title);
    }

    @Override
    // TODO: 2017/9/23 JCVideoPlayerStandard.onPause 视频不可见时,就把资源释放掉
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
