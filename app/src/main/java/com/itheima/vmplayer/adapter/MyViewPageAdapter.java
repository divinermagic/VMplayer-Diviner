package com.itheima.vmplayer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.itheima.vmplayer.bean.AreaBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wschun on 2016/10/1.
 */

public class MyViewPageAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList=new ArrayList<>();
    private  List<AreaBean> areaBeanlist=new ArrayList<>();

    public MyViewPageAdapter(FragmentManager fm,List<Fragment> fragmentList, List<AreaBean> areaBeanlist) {
        super(fm);
        this.fragmentList=fragmentList;
        this.areaBeanlist=areaBeanlist;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return areaBeanlist.get(position).name;
    }
}
