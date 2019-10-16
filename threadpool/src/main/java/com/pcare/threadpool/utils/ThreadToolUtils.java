package com.pcare.threadpool.utils;

import androidx.annotation.NonNull;

import com.pcare.threadpool.callback.ThreadCallback;

/**
 * @Author: gl
 * @CreateDate: 2019/10/16
 * @Description:工具类
 */
public class ThreadToolUtils {
    public static void resetThread(Thread thread, final String name, final ThreadCallback callback){
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
                if(null!=callback){
                    callback.onError(name,e);
                }
            }
        });
    }
}
