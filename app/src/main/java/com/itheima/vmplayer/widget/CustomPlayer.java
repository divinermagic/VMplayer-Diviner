package com.itheima.vmplayer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.itheima.vmplayer.R;

import fm.jiecao.jcvideoplayer_lib.JCMediaManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by wschun on 2016/10/3.
 */

public class CustomPlayer extends JCVideoPlayer {
    public CustomPlayer(Context context) {
        super(context);
    }

    public CustomPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.jc_layout_base;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.start){
            if (JCMediaManager.instance().mediaPlayer.isPlaying())
                Toast.makeText(getContext(),"开始播放啦",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getContext(),"开始暂停啦",Toast.LENGTH_SHORT).show();
        }

        super.onClick(v);


    }
}
