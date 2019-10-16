package com.pcare.threadpool.wrapper;

import com.pcare.threadpool.callback.NormalCallback;
import com.pcare.threadpool.entity.ThreadEnity;
import com.pcare.threadpool.utils.ThreadToolUtils;

import java.util.concurrent.Callable;

/**
 * @Author: gl
 * @CreateDate: 2019/10/16
 * @Description:
 */
public class RunnableWrapper implements Runnable{
    private String name;
    private NormalCallback normalCallback;
    private Runnable runnable;
    private Callable callable;

    public RunnableWrapper(ThreadEnity enity){
        this.name = enity.name;
        this.normalCallback = new NormalCallback(enity.threadCallback,enity.asyncCallback,enity.deliver);
    }

    public RunnableWrapper setRunnable(Runnable runnable) {
        this.runnable = runnable;
        return this;
    }

    public RunnableWrapper setCallable(Callable callable) {
        this.callable = callable;
        return this;
    }

    @Override
    public void run() {
        Thread current = Thread.currentThread();
        ThreadToolUtils.resetThread(current,name,normalCallback);
        normalCallback.onStart(name);
        if(null != runnable){
            runnable.run();
        }else if(null != callable){
            try {
                Object result = callable.call();
                normalCallback.onSuccess(result);
            }catch (Exception e){
                normalCallback.onError(name,e);
            }
        }
        normalCallback.onCompleted(name);

    }
}
