package com.itheima.vmplayer;

/**
 * Created by wschun on 2016/9/30.
 */

public interface BaseView<T> {
    /**
     * 给View传递Presenter实例
     * @param presenter
     */
    void setPresenter(T presenter);

}
