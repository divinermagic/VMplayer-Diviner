package a.itcast.mobileplayer95.Activity;


import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import a.itcast.mobileplayer95.R;
import a.itcast.mobileplayer95.fragment.Homepage.HomeFragment;
import a.itcast.mobileplayer95.fragment.Mvpage.MvFragment;
import a.itcast.mobileplayer95.fragment.TestFragment;
import a.itcast.mobileplayer95.fragment.yuedanpage.YueDanFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Bind(R.id.toobar)
    Toolbar toobar;
    // SparseArray<> 稀疏数组 是Android内部特有的api,标准的jdk是没有这个类的.在Android内部用来替代HashMap<Integer,E>这种形式,
    // 使用SparseArray更加节省内存空间的使用,SparseArray也是以key和value对数据进行保存的.使用的时候只需要指定value的类型即可.
    // 并且key不需要封装成对象类型.
    private SparseArray<Fragment> sparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // TODO: 2017/9/5 动作7: 初始化Toolbar
        // TODO: 2017/9/4 将ToolBar设置为标题栏
        setSupportActionBar(toobar);
        // TODO: 2017/9/4 修改Toolbar的属性 setTitle
        getSupportActionBar().setTitle("VMPlayer");

        // TODO: 2017/9/6 初始化 Fragment 集合  append:附加 要先初始化集合 才能显示界面
        sparseArray = new SparseArray<>();
        sparseArray.append(R.id.bottom_home, new HomeFragment());
        sparseArray.append(R.id.bottom_mv, new MvFragment());
        sparseArray.append(R.id.bottom_vbang, TestFragment.newInstance("V榜"));
        sparseArray.append(R.id.bottom_yuedan, new YueDanFragment());

        // TODO: 2017/9/5 处理底部栏 bottom-bar  1.3.3的版本不能在布局xml文件里直接使用 只能在代码中实现
        // TODO: 2017/9/5  在build.gradle中导包 compile 'com.roughike:bottom-bar:1.3.3'
        // TODO: 2017/9/5 BottomBar.attach();的意思是 把这个底部栏附加到界面上去 savedInstanceState是onCreate里面的那个
        BottomBar bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.bottombar, new OnMyMenuTabClickListener());

        //com.apkfuns.logutils.LogUtils.e(TAG,"MainActivity.onCreate,sparseArray:"+sparseArray);
    }

    // TODO: 2017/9/5 动作8
    // TODO: 2017/9/5 onCreateOptionsMenu 创建目录菜单 这个菜单会依附到Toolbar上
    // TODO: 2017/9/5 动作9 getMenuInflater().inflate(R.menu.activity_main,menu); 用这个创建一个 activity_main.xml文件里面写
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // TODO: 2017/9/5 动作10: onOptionsItemSelected 处理菜单的点击监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // TODO: 2017/9/6 这个提取的方法是: 42行 bottomBar.setItemsFromMenu(R.menu.bottombar, new OnMyMenuTabClickListener());
    private class OnMyMenuTabClickListener implements OnMenuTabClickListener {
        @Override
        // TODO: 2017/9/5 OnMenuTabClickListener--onMenuTabSelected:选中了某一个条目 Tab
        public void onMenuTabSelected(@IdRes int menuItemId) {
            //Fragment fragment = TestFragment.newInstance("这是一个测试界面");
            // TODO: 2017/9/6 切换到当前按钮对应的Fragment
            // TODO: 2017/9/6 sparseArray.get(menuItemId) 要一个ID 就像是HashMap<Integer,E>的ID,直接给予目录的ID menuItemId
            //com.apkfuns.logutils.LogUtils.e(TAG,"OnMyMenuTabClickListener.onMenuTabSelected,sparseArray:"+sparseArray);
            Fragment fragment = sparseArray.get(menuItemId);
            switchFragment(fragment);
        }

        @Override
        // TODO: 2017/9/5 OnMenuTabClickListener--onMenuTabReSelected:重复选中某一个条目 Tab [一般用来刷新操作]
        public void onMenuTabReSelected(@IdRes int menuItemId) {

        }
    }

    /**
     * 将参数里面的 Fragment 显示出来
     *
     * @param fragment // TODO: 2017/9/5  getSupportFragmentManager:获取Fragment的管理器的支持,beginTransaction:开始事务
     */
    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FragmentTransaction replace = transaction.replace(R.id.container, fragment);

        transaction.commit();
    }
}
