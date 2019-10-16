package com.pcare.threadpool.callback;

/**
 * @Author: gl
 * @CreateDate: 2019/10/16
 * @Description:会调接口，用于通知用户任务的状态
 */
public interface ThreadCallback {
    /**
     * 线程执行完成
     * @param threadName
     */
    void onCompleted(String threadName);

    /**
     * 线程异常
     * @param threadName
     * @param throwable
     */
    void onError(String threadName,Throwable throwable);

    /**
     * 线程开始
     * @param threadName
     */
    void onStart(String threadName);
}
