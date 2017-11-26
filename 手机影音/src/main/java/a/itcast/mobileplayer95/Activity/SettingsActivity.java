package a.itcast.mobileplayer95.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置界面 并使用 ToolBar 来作为标题栏
 * @author Diviner
 */
public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";

    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.cacheSize)
    TextView cacheSize;
    @BindView(R.id.rl_clear_chche)
    RelativeLayout rlClearChche;
    @BindView(R.id.switch_push)
    SwitchCompat switchPush;
    @BindView(R.id.rl_switch_push)
    RelativeLayout rlSwitchPush;
    @BindView(R.id.switch_loadimg_no_wifi)
    SwitchCompat switchLoadimgNoWifi;
    @BindView(R.id.rl_loadimg_withwifi)
    RelativeLayout rlLoadimgWithwifi;
    @BindView(R.id.rl_about)
    RelativeLayout rlAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        //初始化标题栏
        setSupportActionBar(toolBar);
        //标题栏名字
        getSupportActionBar().setTitle("设置中心");
        //返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initData();
    }

    /**
     * 获取缓存文件大小,并填充界面
     */
    private void initData() {
        long fileSize = computeCacheSize();

        Log.e(TAG, "initData: fileSize="+fileSize );

        DecimalFormat format = new DecimalFormat("#.00M");

        String sizeStr = format.format(fileSize / 1024 /1024f);

        cacheSize.setText(sizeStr);
    }

    /**
     * 计算 Glide[格力得] 文件的大小
     * @return
     */
    private long computeCacheSize() {

        File cacheDir = Glide.getPhotoCacheDir(this);

        //[健壮性检查]如果不存在 && 不是一个目录的 直接返回0 说没有
        if (!cacheDir.exists()&&!cacheDir.isDirectory()) return 0;

        File[] childFiles = cacheDir.listFiles();

        long size = 0;
        for (File child : childFiles) {
            size += child.length();
            Log.e(TAG, "computeCacheSize:每个文件的大小"+child.length() );
        }

        return size;
    }

    @OnClick({R.id.rl_clear_chche, R.id.rl_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_clear_chche:
                cacheSize.setText("0.00M");
                //清空缓存
               new Thread(){
                   @Override
                   public void run() {
                       Glide.get(SettingsActivity.this).clearDiskCache();
                       Log.e(TAG, "onViewClicked:清除缓存后的大小为:="+computeCacheSize());
                   }
               }.start();
                break;
            case R.id.rl_about:
                Intent intent = new Intent(this,AboutActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);

    }
}
