package a.itcast.mobileplayer95.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import a.itcast.mobileplayer95.R;
import butterknife.Bind;
import butterknife.ButterKnife;

// TODO: 2017/9/4 引导界面
public class SplashActivity extends AppCompatActivity {

    @Bind(R.id.splash_iv_bg)
    ImageView splashIvBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        // TODO: 2017/9/4 播放缩放动画 [用XML文件来生成动画对象]
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            // TODO: 2017/9/4 动画开始 
            public void onAnimationStart(Animation animation) {
                
            }

            @Override
            // TODO: 2017/9/4 动画结束 
            public void onAnimationEnd(Animation animation) {
                startMainActivity();
            }

            @Override
            // TODO: 2017/9/4 动画重复播放
            public void onAnimationRepeat(Animation animation) {

            }
        });
        splashIvBg.startAnimation(animation);

    }

    private void startMainActivity() {
        //打开第二个界面 MainActivity
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

        //关闭当前动画
        finish();
        // TODO: 2017/9/4  跳转到主界面，并处理透明渐变转场动画 动态设置界面转场效果
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

}
