package com.example.firstapplication.base;

import android.app.Application;
import android.util.Log;

import com.example.firstapplication.utils.InitializeService;
import com.pcare.threadpool.PoolThread;
import com.pcare.threadpool.callback.ThreadCallback;

/**
 * @Author: gl
 * @CreateDate: 2019/10/16
 * @Description:
 */
public class AppApplication extends Application {
    private static AppApplication instance;
    private PoolThread executor;
    private final String TAG = "LogCallback";

    public static AppApplication getInstance() {
        if(null == instance){
            synchronized (AppApplication.class){
                if(null == instance){
                    instance = new AppApplication();
                }
                return instance;
            }
        }else
            return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //初始化线程管理器
        initThreadPool();
        InitializeService.start(this,"PCare");

    }

    private ThreadCallback threadCallback = new ThreadCallback() {
        @Override
        public void onError(String name, Throwable t) {
            Log.e(TAG, "LogCallback"+"------onError"+"-----"+name+"----"+Thread.currentThread()+"----"+t.getMessage());
        }

        @Override
        public void onCompleted(String name) {
            Log.e(TAG, "LogCallback"+"------onCompleted"+"-----"+name+"----"+Thread.currentThread());
        }

        @Override
        public void onStart(String name) {
            Log.e(TAG, "LogCallback"+"------onStart"+"-----"+name+"----"+Thread.currentThread());
        }
    };
    private void initThreadPool() {
        // 创建一个独立的实例进行使用
        executor = PoolThread.ThreadBuilder
                .createFixed(5)
                .setPriority(Thread.MAX_PRIORITY)
                .setCallback(threadCallback)
                .build();
    }
    /**
     * 获取线程池管理器对象，统一的管理器维护所有的线程池
     * @return                      executor对象
     */
    public PoolThread getExecutor(){
        if(executor ==null){
            executor = PoolThread.ThreadBuilder
                    .createFixed(5)
                    .setPriority(Thread.MAX_PRIORITY)
                    .setCallback(threadCallback)
                    .build();
        }
        return executor;
    }

}
