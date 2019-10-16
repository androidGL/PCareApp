package com.pcare.threadpool.deliver;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * @Author: gl
 * @CreateDate: 2019/10/16
 * @Description://默认在Android平台下
 */
public class AndroidDeliver implements Executor {

    private static AndroidDeliver instance;
    private Handler main = new Handler(Looper.getMainLooper());
    public static AndroidDeliver getInstance(){
        if (null == instance){
            synchronized (AndroidDeliver.class){
                if(null == instance){
                    instance = new AndroidDeliver();
                }
                return instance;
            }
        }else
            return instance;

    }
    @Override
    public void execute(final Runnable command) {
        //获取应用主线程
        Looper mainLooper = Looper.getMainLooper();
        //如果当前looper是在主线程，那么只需调用run方法
        if(Looper.myLooper() == mainLooper){
            command.run();
            return;
        }
        //否则开启子线程
        main.post(new Runnable() {
            @Override
            public void run() {
                if(null != command){
                    command.run();
                }
            }
        });
    }
}
