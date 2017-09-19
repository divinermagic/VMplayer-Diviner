package com.itheima.vmplayer.fragment.vbangpage;

import android.content.Context;
import android.widget.CursorAdapter;

import com.itheima.vmplayer.BasePresenter;
import com.itheima.vmplayer.BaseView;


/**
 * Created by wschun on 2016/9/30.
 */

public interface VbangContract {
    interface Prestener extends BasePresenter{
        void query(Context content, CursorAdapter cursorAdapter);

    }
    interface  View extends BaseView<Prestener> {
        void setData();
        void setError(String error);
    }
}
