package com.itheima.vmplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.itheima.vmplayer.R;
import com.itheima.vmplayer.fragment.homepage.HomeFragment;
import com.itheima.vmplayer.fragment.mvpage.MVFragment;
import com.itheima.vmplayer.fragment.vbangpage.VBangFragment;
import com.itheima.vmplayer.fragment.yuedanpage.YueDanFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {


    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    private SparseArray<Fragment> sparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolBar);

        sparseArray = new SparseArray<>();
        sparseArray.put(R.id.bottom01,new HomeFragment());
        sparseArray.put(R.id.bottom02,new MVFragment());
        sparseArray.put(R.id.bottom03,new VBangFragment());
        sparseArray.put(R.id.bottom04,new YueDanFragment());

        //底部导航栏 bottombar
        //关联到当前的Activity
        BottomBar bottomBar = BottomBar.attach(this, savedInstanceState);

        bottomBar.setItemsFromMenu(R.menu.bottom_menu, new OnMenuTabClickListener() {
            /**
             * 当菜单条目被选中是触发
             * @param menuItemId
             */
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                Fragment fragment=sparseArray.get(menuItemId);
                setContent(fragment);
            }

            /**
             * 当菜单条目再次被选中时触发
             * @param menuItemId
             */
            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });

    }


    @OnClick(R.id.iv_setting)
    public void onClick() {
        startActivity(new Intent(MainActivity.this,SettingActivity.class));
    }

    /**
     * 切换中间的View方法
     * @param fragment 要被展示的Fragment
     */
    public void setContent(Fragment fragment) {
        if (fragment==null) return;
//        在activity中使用fragment的一个伟大的好处是能跟据用户的输入
//        对fragment进行添加、删除、替换以及执行其它动作的能力。
//        你提交的一组fragment的变化叫做一个事务。事务通过FragmentTransaction来执行。
//        你还可以把每个事务保存在activity的后退栈中，
//        这样就可以让用户在fragment变化之间导航（跟在activity之间导航一样）。
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        fragment.isAdded() 代表的是该Fragment是否添加到Activity中
        if (fragment.isAdded()){
//            该Fragment对象对用户是否可见
            if (fragment.isVisible()){

            }else {
                transaction.show(fragment);
            }
        }else {
            transaction.replace(R.id.fl_content,fragment);
        }
        transaction.commit();
    }


    @Override
    public void onBackPressed() {

        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
         builder.title("退出提示！");
        builder.content("确定要对出应用吗?");
        builder.positiveText("确定");
        builder.negativeText("点错了");
        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                finish();
            }
        });
        builder.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        });
       builder.show();
    }
}
