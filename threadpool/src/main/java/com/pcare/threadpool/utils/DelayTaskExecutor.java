package com.pcare.threadpool.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @Author: gl
 * @CreateDate: 2019/10/16
 * @Description: 使用核心线程池启动延迟任务的类
 */
public final class DelayTaskExecutor {

    private ScheduledExecutorService dispather;

    private static DelayTaskExecutor instance = new DelayTaskExecutor();

    public static DelayTaskExecutor getInstance() {
        if(null == instance){
            synchronized (DelayTaskExecutor.class){
                if(null == instance){
                    instance = new DelayTaskExecutor();
                }
                return instance;
            }
        }else
            return instance;
    }

    private DelayTaskExecutor() {
        dispather = Executors.newScheduledThreadPool(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = newThread(r);
                thread.setName("PCare-DelayTask-Dispatcher");
                thread.setPriority(Thread.MAX_PRIORITY);
                return thread;
            }
        });
    }

    /**
     * 设置延迟时间，单位为毫秒
     * @param delay
     * @param pool
     * @param runnable
     */
    public void postDelay(long delay, final ExecutorService pool,final Runnable runnable){
        if(0==delay){
            pool.execute(runnable);
            return;
        }
        dispather.schedule(new Runnable() {
            @Override
            public void run() {
                pool.execute(runnable);
            }
        },delay, TimeUnit.MILLISECONDS);
    }

}
