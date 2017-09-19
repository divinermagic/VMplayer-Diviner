package com.itheima.vmplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itheima.vmplayer.R;

import java.io.File;
import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.cacheSize)
    TextView cacheSize;
    @Bind(R.id.rl_clear_chche)
    RelativeLayout rlClearChche;
    @Bind(R.id.switch_push)
    SwitchCompat switchPush;
    @Bind(R.id.rl_switch_push)
    RelativeLayout rlSwitchPush;
    @Bind(R.id.switch_loadimg_no_wifi)
    SwitchCompat switchLoadimgNoWifi;
    @Bind(R.id.rl_loadimg_withwifi)
    RelativeLayout rlLoadimgWithwifi;
    @Bind(R.id.rl_about)
    RelativeLayout rlAbout;
    @Bind(R.id.activity_setting)
    LinearLayout activitySetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle(R.string.setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        File file= Glide.getPhotoCacheDir(this);
        DecimalFormat decimalFormat=new DecimalFormat( );
        String format = decimalFormat.format(getDirSize(file));
        cacheSize.setText(format+"M");
    }

    private float getDirSize(File file) {
        if (file.exists()){
            if (file.isDirectory()){
                File[] files = file.listFiles();
                float size=0;
                for (File fil: files) {
                    size+=getDirSize(fil);
                }
                return size;
            }else {
                float size = (float) file.length() / 1024 / 1024;
                return size;
            }
        }else {
            return 0.0f;
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.rl_clear_chche, R.id.rl_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_clear_chche:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(SettingActivity.this).clearDiskCache();
                    }
                }).start();
                cacheSize.setText(0+"M");
                break;
            case R.id.rl_about:
                startActivity(new Intent(SettingActivity.this,AboutActivity.class));
                break;
        }
    }
}
