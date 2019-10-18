package com.example.firstapplication.base;

import android.app.Activity;

/**
 * @Author: gl
 * @CreateDate: 2019/10/18
 * @Description: 当Presenter中需要获取上下文对象时，传递上下文对象，不会让Presenter直接持有
 */
public interface IView {
    Activity getSelfActivity();
}
