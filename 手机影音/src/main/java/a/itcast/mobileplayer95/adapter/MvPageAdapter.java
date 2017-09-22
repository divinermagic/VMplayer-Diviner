package a.itcast.mobileplayer95.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * 作者：Magic on 2017/9/22 18:44
 * 邮箱：bonian1852@163.com
 */
// TODO: 2017/9/22 新建的 MvPapeAdapter 一开始继承的 PagerAdapter 然后我们动态继承 FragmentStatePagerAdapter

/**
 * FragmentStatePagerAdapter:
 *          因为我们有7个子界面 如果我们想在一个子界面中刷新全部的界面 我们用这个更方便
 */
public class MvPageAdapter  extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList;

    // TODO: 2017/9/22 [6].生成一个List<String> 这里面放的就是我们的Title
    private List<String> titleList;

    public MvPageAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    // TODO: 2017/9/22 [5].把文字显示到 tablayout 上
    @Override
    public CharSequence getPageTitle(int position) {
        // TODO: 2017/9/22 [7].返回 titleList.get(position); 然后删除原来的构造方法,并生成新的
        return titleList.get(position);
        
    }
}
