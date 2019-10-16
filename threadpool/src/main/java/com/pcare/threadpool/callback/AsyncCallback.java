package com.pcare.threadpool.callback;

/**
 * @Author: gl
 * @CreateDate: 2019/10/16
 * @Description: 异步callback回调接口
 */
public interface AsyncCallback<T> {
    /**
     * 成功时回调
     * @param t
     */
    void onSuccess(T t);

    /**
     * 失败时回调
     * @param throwable
     */
    void onFailes(Throwable throwable);

    /**
     * 通知用户任务开始运行
     * @param threadName
     */
    void onStart(String threadName);
}
