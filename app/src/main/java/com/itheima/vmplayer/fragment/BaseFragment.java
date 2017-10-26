package com.itheima.vmplayer.fragment;

import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by wschun on 2016/9/28.
 */

public class BaseFragment extends Fragment {

    protected View rootView;
    protected int screenWidth, screenHeight;
    protected int offset;
    protected static final int SIZE = 10;
    private MaterialDialog.Builder builder;
    private MaterialDialog dialog;
    protected boolean refresh=false;
    protected int lastVisibleItemPosition;
    protected boolean hasmore=true;

    protected void obserView(int width, int height) {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = screenWidth * height / width;
    }

    protected void showLoging() {
        if (builder == null){
            builder = new MaterialDialog.Builder(getActivity());
            builder.title("等一下");
            builder.content("正在努力加载中。。。");
            builder.cancelable(false);
            builder.progress(true, 0);
        }
        dialog = builder.show();
    }

    protected void dismiss() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

}
