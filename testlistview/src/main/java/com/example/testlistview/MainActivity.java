package com.example.testlistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.ListView)
    android.widget.ListView ListView;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ListView.setAdapter(new MyAdapter());
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 500;
        }

        /**
         * @param position    位置
         * @param convertView covertView 列表视图
         * @param parent      视图组
         * @return getView 返回某个指定位置的 View 对象
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TextView textView;
            if (convertView == null) {
                textView = new TextView(MainActivity.this);
                Log.i(TAG, "创建新的View" + position);
            } else {
                textView = (TextView) convertView;
                Log.i(TAG, "复用旧的View" + position);
            }

            textView.setText("我是第" + position + "个产生的");
            Log.i(TAG, "getView --- " + position);
            Toast.makeText(MainActivity.this, "getView -- position" + position, Toast.LENGTH_SHORT).show();

            return textView;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

    }

}
