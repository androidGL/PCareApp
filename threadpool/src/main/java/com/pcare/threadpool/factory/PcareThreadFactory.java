package com.pcare.threadpool.factory;

import java.util.concurrent.ThreadFactory;

/**
 * @Author: gl
 * @CreateDate: 2019/10/16
 * @Description: 默认的Thread工厂，设置优先级
 */
public class PcareThreadFactory implements ThreadFactory {

    private int priority;

    public PcareThreadFactory(int priority) {
        this.priority = priority;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setPriority(priority);
        return thread;
    }
}
