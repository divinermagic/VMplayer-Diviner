package com.itheima.vmplayer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.itheima.vmplayer.R;
import com.itheima.vmplayer.bean.MVDetailBean;
import com.itheima.vmplayer.fragment.MVDescribeFragment;
import com.itheima.vmplayer.fragment.MVRelativeFragment;
import com.itheima.vmplayer.http.OkHttpManager;
import com.itheima.vmplayer.http.StringCallBack;
import com.itheima.vmplayer.util.URLProviderUtil;


import butterknife.Bind;
import butterknife.ButterKnife;

import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCBuriedPointStandard;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Call;

/**
 * Created by Mr.Wang
 * Date  2016/9/6.
 * Email 1198190260@qq.com
 */
public class MVDetailActivity extends AppCompatActivity {

    @Bind(R.id.videoPlayer)
    JCVideoPlayerStandard videoPlayer;
    @Bind(R.id.mv_describe)
    ImageView mvDescribe;
    @Bind(R.id.mv_comment)
    ImageView mvComment;
    @Bind(R.id.mv_relative)
    ImageView mvRelative;
    @Bind(R.id.fl_content)
    FrameLayout flContent;
    private int id;
    private MVDetailBean mvDetailBean;
    private MVDescribeFragment mvDescribeFragment;
    private MVRelativeFragment mvRelativeFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvdetail);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra("id", -1);
        setImageNomal(R.id.mv_describe);
        getData();



    }


    private void setImageNomal(int id){
        mvDescribe.setBackgroundResource(R.drawable.player_mv);
        mvComment.setBackgroundResource(R.drawable.player_comment);
        mvRelative.setBackgroundResource(R.drawable.player_relative_mv);

        switch (id){
            case R.id.mv_describe:
                mvDescribe.setBackgroundResource(R.drawable.player_mv_p);
                break;
            case R.id.mv_comment:
                mvComment.setBackgroundResource(R.drawable.player_comment_p);
                break;
            case R.id.mv_relative:
                mvRelative.setBackgroundResource(R.drawable.player_relative_mv_p);
                break;
        }
    }

    @OnClick({R.id.mv_describe, R.id.mv_comment, R.id.mv_relative})
    public void onClick(View view) {
        setImageNomal(view.getId());
        switch (view.getId()) {
            case R.id.mv_describe:
                setFragment(mvDescribeFragment);
                break;
            case R.id.mv_comment:
                break;
            case R.id.mv_relative:
               setFragment(mvRelativeFragment);
                break;
        }
    }

    public void getData() {
        OkHttpManager.getOkHttpManager().asyncGet(URLProviderUtil.getRelativeVideoListUrl(id), this, new StringCallBack() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                mvDetailBean = new Gson().fromJson(response, MVDetailBean.class);
                videoPlayer.setUp(mvDetailBean.getUrl(),mvDetailBean.getTitle());
                videoPlayer.startButton.performClick();
                mvDescribeFragment = MVDescribeFragment.newInstance(mvDetailBean);
                mvRelativeFragment = MVRelativeFragment.newInstance(mvDetailBean);
                setFragment(mvDescribeFragment);
            }
        });



    }


    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    public void setFragment(Fragment fragment) {
         if (fragment==null)return;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (fragment.isAdded() && fragment.isVisible()){
            return;
        }
        if (fragment.isAdded()){
            fragmentTransaction.show(fragment);
        }else {
            fragmentTransaction.replace(R.id.fl_content,fragment);
        }
        fragmentTransaction.commit();
    }
}
