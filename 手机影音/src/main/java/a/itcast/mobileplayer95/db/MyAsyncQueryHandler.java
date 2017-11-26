package a.itcast.mobileplayer95.db;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;

import a.itcast.mobileplayer95.adapter.VBangAdapter;
import a.itcast.mobileplayer95.utils.LogUtils;

/**
 * Created by divinermagic on 2017/10/17.
 * @author Diviner
 */

public class MyAsyncQueryHandler extends AsyncQueryHandler {

    private static final String TAG = "MyAsyncQueryHandler";

    public MyAsyncQueryHandler(ContentResolver cr) {
        super(cr);
    }

    @Override
    //异步查询获取到的结果
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        LogUtils.e(TAG,"MyAsyncQueryHandler.onQueryComplete,获取到cursor数据");
        VBangAdapter adapter = (VBangAdapter) cookie;
        adapter.swapCursor(cursor);
    }
}
