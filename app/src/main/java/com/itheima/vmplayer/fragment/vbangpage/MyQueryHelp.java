package com.itheima.vmplayer.fragment.vbangpage;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.widget.CursorAdapter;

import com.itheima.vmplayer.adapter.MyCursorAdapter;

/**
 * Created by wschun on 2016/9/30.
 */

public class MyQueryHelp extends AsyncQueryHandler {
    private VbangContract.View view;
    public MyQueryHelp(ContentResolver cr, VbangContract.View view) {
        super(cr);
        this.view=view;
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        super.onQueryComplete(token, cookie, cursor);
        if (token==0 && cookie instanceof CursorAdapter){
            MyCursorAdapter myCursorAdapter= (MyCursorAdapter) cookie;
            myCursorAdapter.changeCursor(cursor);
            view.setData();
        }


    }
}
