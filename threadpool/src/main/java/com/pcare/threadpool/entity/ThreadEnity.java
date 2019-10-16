package com.pcare.threadpool.entity;

import com.pcare.threadpool.callback.AsyncCallback;
import com.pcare.threadpool.callback.ThreadCallback;

import java.util.concurrent.Executor;

/**
 * @Author: gl
 * CreateDate: 2019/10/16
 * @Description: 创建任务的实体类
 */
public class ThreadEnity {
    //线程名称
    public String name;
    //线程执行延迟的时间
    public long delay;
    //线程执行者
    public Executor deliver;
    //用户任务的状态回调
    public ThreadCallback threadCallback;
    //异步任务回调
    public AsyncCallback asyncCallback;
}
