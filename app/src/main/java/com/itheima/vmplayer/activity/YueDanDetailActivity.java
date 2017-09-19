package com.itheima.vmplayer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.itheima.vmplayer.R;
import com.itheima.vmplayer.bean.YueDanDetailBean;
import com.itheima.vmplayer.http.OkHttpManager;
import com.itheima.vmplayer.http.StringCallBack;
import com.itheima.vmplayer.util.URLProviderUtil;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Call;

/**
 * Created by Mr.Wang
 * Date  2016/9/6.
 * Email 1198190260@qq.com
 */
public class YueDanDetailActivity extends AppCompatActivity {

    @Bind(R.id.videoPlayer)
    JCVideoPlayerStandard videoPlayer;
    @Bind(R.id.iv_yuedan_describe)
    ImageView ivYuedanDescribe;
    @Bind(R.id.iv_yuedan_comment)
    ImageView ivYuedanComment;
    @Bind(R.id.iv_yuedan_list)
    ImageView ivYuedanList;
    @Bind(R.id.fl_content)
    FrameLayout flContent;
    private int id;
    private YueDanDetailBean yueDanDetailBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuedan);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra("id", -1);

        getData();
    }

    private void setImgNomal(){
        ivYuedanDescribe.setBackgroundResource(R.drawable.player_yue);
        ivYuedanComment.setBackgroundResource(R.drawable.player_yue_comment);
        ivYuedanList.setBackgroundResource(R.drawable.player_yuelist);
    }

    @OnClick({R.id.iv_yuedan_describe, R.id.iv_yuedan_comment, R.id.iv_yuedan_list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_yuedan_describe:
                setImgNomal();
                ivYuedanDescribe.setBackgroundResource(R.drawable.player_mv_p);
                break;
            case R.id.iv_yuedan_comment:
                setImgNomal();
                ivYuedanComment.setBackgroundResource(R.drawable.player_comment_p);
                break;
            case R.id.iv_yuedan_list:
                setImgNomal();
                ivYuedanList.setBackgroundResource(R.drawable.player_yuelist_p);

                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    public void getData() {

        OkHttpManager.getOkHttpManager().asyncGet(URLProviderUtil.getPeopleYueDanList(id), this, new StringCallBack() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                yueDanDetailBean = new Gson().fromJson(response, YueDanDetailBean.class);
                videoPlayer.setUp(yueDanDetailBean.getVideos().get(0).getUrl(),yueDanDetailBean.getVideos().get(0).getTitle());
                videoPlayer.startButton.performClick();

            }
        });


    }
}
