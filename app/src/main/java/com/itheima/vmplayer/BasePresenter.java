package com.itheima.vmplayer;

/**
 * Created by wschun on 2016/9/30.
 */

public interface BasePresenter {
    /**
     *获取数据的方法
     * @param offest 改项目中会大量用到分页加载，代表的是起始位置
     * @param size  每一页的条目个数
     */
    void getData(int offest,int size);

}
