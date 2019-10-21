package com.pcare.threadpool;

import com.pcare.threadpool.callback.AsyncCallback;
import com.pcare.threadpool.callback.ThreadCallback;
import com.pcare.threadpool.deliver.AndroidDeliver;
import com.pcare.threadpool.entity.ThreadEnity;
import com.pcare.threadpool.factory.PcareThreadFactory;
import com.pcare.threadpool.utils.DelayTaskExecutor;
import com.pcare.threadpool.utils.ThreadToolUtils;
import com.pcare.threadpool.wrapper.CallableWrapper;
import com.pcare.threadpool.wrapper.RunnableWrapper;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Author: gl
 * @CreateDate: 2019/10/16
 * @Description: 自定义线程池
 */
public class PoolThread implements Executor {

    //线程池
    private ExecutorService pool;

    //默认线程名称
    private String defName;

    //默认线程回调
    private ThreadCallback defCallback;

    //默认线程传递
    private Executor defDeliver;

    //确保多线程配置没有冲突
    private ThreadLocal<ThreadEnity> threadLocal;

    public PoolThread(ExecutorService pool, int type,int size, int priority,String defName, ThreadCallback defCallback, Executor defDeliver) {
        if(null == pool){
            pool = createPool(type,size,priority);
        }
        this.pool = pool;
        this.defName = defName;
        this.defCallback = defCallback;
        this.defDeliver = defDeliver;
        this.threadLocal = new ThreadLocal<>();
    }
    /**
     * 为当前的任务设置线程名。
     * @param name              线程名字
     * @return                  PoolThread
     */
    public PoolThread setName(String name) {
        getLocalConfigs().name = defName;
        return this;
    }
    /**
     * 设置当前任务的线程回调，如果未设置，则应使用默认回调。
     * @param callback          线程回调
     * @return                  PoolThread
     */
    public PoolThread setCallback(ThreadCallback callback){
        getLocalConfigs().threadCallback = callback;
        return this;
    }
    /**
     * 设置当前任务的延迟时间.
     * 只有当您的线程池创建时，它才会产生效果。
     * @param time              时长
     * @param unit              time unit
     * @return                  PoolThread
     */
    public PoolThread setDelay(long time, TimeUnit unit){
        long delay = unit.toMillis(time);
        getLocalConfigs().delay = Math.max(0,delay);
        return this;
    }
    /**
     * 启动异步任务，回调用于接收可调用任务的结果。
     * @param callable              callable
     * @param callback              callback
     * @param <T> type
     */
    public <T> void async(Callable<T> callable, AsyncCallback<T> callback) {
        ThreadEnity configs = getLocalConfigs();
        configs.asyncCallback = callback;
        Runnable runnable = new RunnableWrapper(configs).setCallable(callable);
        DelayTaskExecutor delayTaskExecutor = DelayTaskExecutor.getInstance();
        delayTaskExecutor.postDelay(configs.delay, pool, runnable);
        resetLocalConfigs();
    }
    /**
     * 发射任务
     * 提交任务有返回值
     * @param callable              callable
     * @param <T>                   type
     * @return {@link Future}
     */
    public <T> Future<T> submit (Callable<T> callable) {
        Future<T> result;
        callable = new CallableWrapper<>(getLocalConfigs(), callable);
        result = pool.submit(callable);
        resetLocalConfigs();
        return result;
    }
    /**
     * 关闭线程池操作
     */
    public void stop(){
        try {
            // shutdown只是起到通知的作用
            // 只调用shutdown方法结束线程池是不够的
            pool.shutdownNow();
            // (所有的任务都结束的时候，返回TRUE)
            if(!pool.awaitTermination(0, TimeUnit.MILLISECONDS)){
                // 超时的时候向线程池中所有的线程发出中断(interrupted)。
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            // awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。
            e.printStackTrace();
        } finally {
            pool.shutdownNow();
            //close();
        }
    }
    /**
     * 获取要创建的线程池。
     * @return                      线程池
     */
    public ExecutorService getExecutor() {
        return pool;
    }
    /**
     * 销毁的时候可以调用这个方法
     */
    public void close(){
        if(threadLocal!=null){
            threadLocal.remove();
            threadLocal = null;
        }
    }
    /**
     * 设置当前任务的线程传递。如果未设置，则应使用默认传递。
     * @param deliver           thread deliver
     * @return                  PoolThread
     */
    public PoolThread setDeliver(Executor deliver) {
        getLocalConfigs().deliver = deliver;
        return this;
    }

    /**
     * 注意需要用synchronized修饰，解决了多线程的安全问题
     * 获取本地配置参数
     * @return
     */
    private synchronized ThreadEnity getLocalConfigs() {
        ThreadEnity configs = threadLocal.get();
        if (configs == null) {
            configs = new ThreadEnity();
            configs.name = defName;
            configs.threadCallback = defCallback;
            configs.deliver = defDeliver;
            threadLocal.set(configs);
        }
        return configs;
    }
    /**
     * 创建线程池，目前支持以下四种
     * @param type                  类型
     * @param size                  数量size
     * @param priority              优先级
     * @return
     */
    private synchronized ExecutorService createPool(int type, int size, int priority) {
        switch (type) {
            case ThreadBuilder.TYPE_CACHE:
                //它是一个数量无限多的线程池，都是非核心线程，适合执行大量耗时小的任务
                return Executors.newCachedThreadPool(new PcareThreadFactory(priority));
            case ThreadBuilder.TYPE_FIXED:
                //线程数量固定的线程池，全部为核心线程，响应较快，不用担心线程会被回收。
                return Executors.newFixedThreadPool(size, new PcareThreadFactory(priority));
            case ThreadBuilder.TYPE_SCHEDULED:
                //有数量固定的核心线程，且有数量无限多的非核心线程，适合用于执行定时任务和固定周期的重复任务
                return Executors.newScheduledThreadPool(size, new PcareThreadFactory(priority));
            case ThreadBuilder.TYPE_SINGLE:
            default:
                //内部只有一个核心线程，所有任务进来都要排队按顺序执行
                return Executors.newSingleThreadExecutor(new PcareThreadFactory(priority));
        }
    }

    /**
     * 启动任务
     * 这个是实现接口Executor中的execute方法
     * 在将来的某个时间执行给定的命令。该命令可以在新线程、池线程或调用线程中执行，这取决于{@code Executor}实现。
     * 提交任务无返回值
     * @param command              task，注意添加非空注解
     */
    @Override
    public void execute(Runnable command) {
        ThreadEnity enity = getLocalConfigs();
        command = new RunnableWrapper(enity).setRunnable(command);
        DelayTaskExecutor.getInstance().postDelay(enity.delay,pool,command);
        resetLocalConfigs();
    }




    /**
     * 当启动任务或者发射任务之后需要调用该方法
     * 重置本地配置，置null
     */
    private void resetLocalConfigs() {
        threadLocal.set(null);
    }

    public static class ThreadBuilder{
        final static int TYPE_CACHE = 0;
        final static int TYPE_FIXED = 1;
        final static int TYPE_SINGLE = 2;
        final static int TYPE_SCHEDULED = 3;
        int type;
        int size;
        int priority = Thread.NORM_PRIORITY;
        String name;
        ThreadCallback callback;
        Executor deliver;
        ExecutorService pool;

        private ThreadBuilder(int size,  int type, ExecutorService pool) {
            this.size = Math.max(1, size);
            this.type = type;
            this.pool = pool;
        }

        /**
         * 通过Executors.newSingleThreadExecutor()创建线程池
         * 内部只有一个核心线程，所有任务进来都要排队按顺序执行
         */
        public static ThreadBuilder create(ExecutorService pool) {
            return new ThreadBuilder(1, TYPE_SINGLE, pool);
        }

        /**
         * 通过Executors.newCachedThreadPool()创建线程池
         * 它是一个数量无限多的线程池，都是非核心线程，适合执行大量耗时小的任务
         */
        public static ThreadBuilder createCacheable() {
            return new ThreadBuilder(0, TYPE_CACHE, null);
        }

        /**
         * 通过Executors.newFixedThreadPool()创建线程池
         * 线程数量固定的线程池，全部为核心线程，响应较快，不用担心线程会被回收。
         */
        public static ThreadBuilder createFixed(int size) {
            return new ThreadBuilder(size, TYPE_FIXED, null);
        }
        /**
         * 通过Executors.newScheduledThreadPool()创建线程池
         * 有数量固定的核心线程，且有数量无限多的非核心线程，适合用于执行定时任务和固定周期的重复任务
         */
        public static ThreadBuilder createScheduled(int size) {
            return new ThreadBuilder(size, TYPE_SCHEDULED, null);
        }/**
         * 通过Executors.newSingleThreadPool()创建线程池
         * 内部只有一个核心线程，所有任务进来都要排队按顺序执行
         * 和create区别是size数量
         */
        public static ThreadBuilder createSingle() {
            return new ThreadBuilder(0, TYPE_SINGLE, null);
        }

        /**
         * 将默认线程名设置为“已使用”。
         */
        public ThreadBuilder setName (String name) {
            if (name.length()>0) {
                this.name = name;
            }
            return this;
        }

        /**
         * 将默认线程优先级设置为“已使用”。
         */
        public ThreadBuilder setPriority (int priority) {
            this.priority = priority;
            return this;
        }

        /**
         * 将默认线程回调设置为“已使用”。
         */
        public ThreadBuilder setCallback (ThreadCallback callback) {
            this.callback = callback;
            return this;
        }

        /**
         * 设置默认线程交付使用
         */
        public ThreadBuilder setDeliver(Executor deliver) {
            this.deliver = deliver;
            return this;
        }

        /**
         * 创建用于某些配置的线程管理器。
         * @return                  对象
         */
        public PoolThread build() {
            //最大值
            priority = Math.max(Thread.MIN_PRIORITY, priority);
            //最小值
            priority = Math.min(Thread.MAX_PRIORITY, priority);

            size = Math.max(1, size);
            if (name==null || name.length()==0) {
                // 如果没有设置名字，那么就使用下面默认的线程名称
                switch (type) {
                    case TYPE_CACHE:
                        name = "CACHE";
                        break;
                    case TYPE_FIXED:
                        name = "FIXED";
                        break;
                    case TYPE_SINGLE:
                        name = "SINGLE";
                        break;
                    default:
                        name = "POOL_THREAD";
                        break;
                }
            }

            if (deliver == null) {
                deliver = AndroidDeliver.getInstance();
            }
            return new PoolThread( pool,type, size, priority, name, callback, deliver);
        }
    }
}
