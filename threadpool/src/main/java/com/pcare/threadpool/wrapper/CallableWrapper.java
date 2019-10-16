package com.pcare.threadpool.wrapper;

import com.pcare.threadpool.callback.NormalCallback;
import com.pcare.threadpool.callback.ThreadCallback;
import com.pcare.threadpool.entity.ThreadEnity;
import com.pcare.threadpool.utils.ThreadToolUtils;

import java.util.concurrent.Callable;

/**
 * @Author: gl
 * @CreateDate: 2019/10/16
 * @Description:
 */
public final class CallableWrapper<T> implements Callable<T> {

    private String name;
    private ThreadCallback callback;
    private Callable<T> proxy;

    public CallableWrapper(ThreadEnity enity,Callable<T> proxy) {
        this.name = enity.name;
        this.proxy = proxy;
        this.callback = new NormalCallback(enity.threadCallback,enity.asyncCallback,enity.deliver);
    }

    @Override
    public T call() {
        ThreadToolUtils.resetThread(Thread.currentThread(),name,callback);
        if(null != callback){
            callback.onStart(name);
        }
        T t = null;
        try{
            t = proxy == null ? null : proxy.call();
        }catch (Exception e){
            e.printStackTrace();
            if(null != callback)
                callback.onError(name,e);
        }finally {
            if(null != callback){
                callback.onCompleted(name);
            }
        }
        return t;
    }
}
