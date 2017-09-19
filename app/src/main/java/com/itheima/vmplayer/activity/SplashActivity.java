package com.itheima.vmplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.itheima.vmplayer.R;

/**
 * Created by wschun on 2016/9/27.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //一般在欢迎界面要做的所有操作
        //初始化数据库的操作
        //初始化程序文件的目录结构
        //拷贝文件
        //初始化第三方类库，如imageloder,ShareSDK

        ImageView ivSplash = (ImageView) findViewById(R.id.iv_splash);
        /**
         * 加载一个动画
         */
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        /**
         * 开启动画
         */
        ivSplash.startAnimation(animation);
        /**
         * 给动画设置监听
         */
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //当动画开启的时候
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //当动画结束的时候
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //当动画重新执行的时候
            }
        });


    }
}
