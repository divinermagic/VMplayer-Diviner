package a.itcast.mobileplayer95.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import a.itcast.mobileplayer95.bean.YueDanDetailBean;
import a.itcast.mobileplayer95.fragment.playerpage.CommentFragment;
import a.itcast.mobileplayer95.fragment.playerpage.DescriptionFragment;
import a.itcast.mobileplayer95.fragment.playerpage.RelativeFragment;
import a.itcast.mobileplayer95.http.BaseCallBack;
import a.itcast.mobileplayer95.http.HttpManager;
import a.itcast.mobileplayer95.utils.LogUtils;
import a.itcast.mobileplayer95.utils.URLProviderUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class PlayerActivity extends AppCompatActivity {

    private static final String TAG = "PlayerActivity";
    @BindView(R.id.jiecaoplayer)
    JCVideoPlayerStandard jiecaoplayer;
    @BindView(R.id.mv_describe)
    ImageView mvDescribe;
    @BindView(R.id.mv_comment)
    ImageView mvComment;
    @BindView(R.id.mv_relative)
    ImageView mvRelative;
    @BindView(R.id.fl_content)
    FrameLayout flContent;

    //    @Bind(R.id.jiecaoplayer)
//    JCVideoPlayerStandard jiecaoplayer;
//    @Bind(R.id.mv_describe)
//    ImageView mvDescribe;
//    @Bind(R.id.mv_comment)
//    ImageView mvComment;
//    @Bind(R.id.mv_relative)
//    ImageView mvRelative;
//    @Bind(R.id.fl_content)
//    FrameLayout flContent;
    private SparseArray<Fragment> sparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);

        String type = getIntent().getStringExtra("type");

        if ("yuedan".equals(type)) {
            //处理悦单 【因为悦单里面没有直接的URL 必须通过ID来找到播放地址】
            int id = getIntent().getIntExtra("id", -1);//-1:代表的是没有数据
            requestVideoData(id);//请求视频数据

        } else {
            //普通播放模式 【Mv播放】
            String url = getIntent().getStringExtra("url");
            String title = getIntent().getStringExtra("title");
            //初始化播放器
            jiecaoplayer.setUp(url, title);
            //模拟点击按钮
            jiecaoplayer.startButton.performClick();
        }

        // TODO: 2017/10/17  初始化 Fragment 缓存 只在初始化时创建了一次 并放到了缓存里面了
        sparseArray = new SparseArray<>();
        sparseArray.append(R.id.mv_describe, new DescriptionFragment());
        sparseArray.append(R.id.mv_comment, new CommentFragment());
        sparseArray.append(R.id.mv_relative, new RelativeFragment());

        // TODO: 2017/9/27 初始化界面时 默认选中MV描述界面
        mvDescribe.performClick();
    }

    // TODO: 2017/10/17 requestVideoData(int id)请求视频数据
    private void requestVideoData(int id) {
        String url = URLProviderUtil.getPeopleYueDanList(id);

        LogUtils.e(TAG, "PlayerActivity.requestVideoData,url:" + url);

        HttpManager.getInstance().get(url, new BaseCallBack<YueDanDetailBean>() {

            @Override
            public void onFailure(int code, Exception e) {
                Toast.makeText(PlayerActivity.this, "出现错误,错误代码为：" + code, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(YueDanDetailBean yueDanDetailBean) {
                List<YueDanDetailBean.VideosBean> videos = yueDanDetailBean.getVideos();
                YueDanDetailBean.VideosBean videosBean = videos.get(0);//0代表第一首
                //初始化播放器
                jiecaoplayer.setUp(videosBean.getHdUrl(), videosBean.getTitle());
                //模拟点击按钮
                jiecaoplayer.startButton.performClick();
            }

        });
    }

    @Override
    // TODO: 2017/9/23 JCVideoPlayerStandard.onPause 视频不可见时,就把资源释放掉
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();


    }

    @OnClick({R.id.mv_describe, R.id.mv_comment, R.id.mv_relative})
    public void onViewClicked(View view) {
        updataButtonPic(view.getId());
        showFragment(sparseArray.get(view.getId()));
    }

    // TODO: 2017/9/27 将参数里的 Fragment 显示出来
    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_content, fragment);
        transaction.commit();
    }


    // TODO: 2017/9/27 根据选中的ID来更新图片
    private void updataButtonPic(int viewId) {
        //更新简介
        if (viewId == R.id.mv_describe) {
            mvDescribe.setBackgroundResource(R.drawable.player_mv_p);
        } else {
            mvDescribe.setBackgroundResource(R.drawable.player_mv);
        }

        //更新评论
        if (viewId == R.id.mv_comment) {
            mvComment.setBackgroundResource(R.drawable.player_comment_p);
        } else {
            mvComment.setBackgroundResource(R.drawable.player_comment);
        }

        //更新相关
        if (viewId == R.id.mv_relative) {
            mvRelative.setBackgroundResource(R.drawable.player_relative_mv_p);
        } else {
            mvRelative.setBackgroundResource(R.drawable.player_relative_mv);
        }

    }
}
