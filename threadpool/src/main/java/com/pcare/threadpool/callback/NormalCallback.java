package com.pcare.threadpool.callback;

import java.util.concurrent.Executor;

/**
 * @Author: gl
 * @CreateDate: 2019/10/16
 * @Description:
 */
public class NormalCallback implements ThreadCallback,AsyncCallback{
    private ThreadCallback threadCallback;
    private AsyncCallback asyncCallback;
    private Executor deliver;

    public NormalCallback(ThreadCallback threadCallback, AsyncCallback asyncCallback, Executor deliver) {
        this.threadCallback = threadCallback;
        this.asyncCallback = asyncCallback;
        this.deliver = deliver;
    }

    //回调成功
    @Override
    public void onSuccess(Object o) {

    }

    //回调失败
    @Override
    public void onFailes(Throwable throwable) {

    }

    //回调完成
    @Override
    public void onCompleted(String threadName) {

    }

    //回调错误
    @Override
    public void onError(String threadName, Throwable throwable) {

    }

    //回调开始
    @Override
    public void onStart(String threadName) {

    }
}
