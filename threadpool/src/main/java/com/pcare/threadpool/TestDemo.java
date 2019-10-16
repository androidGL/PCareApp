package com.pcare.threadpool;

/**
 * @Author: gl
 * @CreateDate: 2019/10/16
 * @Description:
 */
public class TestDemo {
    /**
    private void startThread1() {
        PoolThread executor = App.getInstance().getExecutor();
        executor.setName("最简单的线程调用方式");
        executor.setDeliver(new AndroidDeliver());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.e("PoolThreadMainActivity","最简单的线程调用方式");
            }
        });
    }


    private void startThread2() {
        executor = App.getInstance().getExecutor();
        executor.setName("异步回调");
        executor.setDelay(2,TimeUnit.SECONDS);
        // 启动异步任务
        executor.async(new Callable<Login>(){
            @Override
            public Login call() throws Exception {
                Log.e("PoolThreadAsyncCallback","耗时操作");
                Thread.sleep(5000);
                // 做一些操作
                return null;
            }
        }, new AsyncCallback<Login>() {
            @Override
            public void onSuccess(Login user) {
                Log.e("PoolThreadAsyncCallback","成功");
            }

            @Override
            public void onFailed(Throwable t) {
                Log.e("PoolThreadAsyncCallback","失败");
            }

            @Override
            public void onStart(String threadName) {
                Log.e("PoolThreadAsyncCallback","开始");
            }
        });
    }


    private void startThread3() {
        PoolThread executor = App.getInstance().getExecutor();
        executor.setName("延迟时间执行任务");
        executor.setDelay(2, TimeUnit.SECONDS);
        executor.setDeliver(new AndroidDeliver());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.e("PoolThreadMainActivity","延迟时间执行任务");
            }
        });
    }


    private void startThread4() {
        PoolThread executor = App.getInstance().getExecutor();
        //设置为当前的任务设置线程名
        executor.setName("延迟时间执行任务");
        //设置当前任务的延迟时间
        executor.setDelay(2, TimeUnit.SECONDS);
        //设置当前任务的线程传递
        executor.setDeliver(new AndroidDeliver());
        //关闭线程池操作
//        executor.stop();
        //销毁的时候可以调用这个方法
//        executor.close();
        executor.setCallback(new ThreadCallback() {
            @Override
            public void onError(String threadName, Throwable t) {

            }

            @Override
            public void onCompleted(String threadName) {

            }

            @Override
            public void onStart(String threadName) {

            }
        });
        Future<String> submit = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Log.d("PoolThreadstartThread4","startThread4---call");
                Thread.sleep(2000);
                String str = "小杨逗比";
                return str;
            }
        });
        try {
            String result = submit.get();
            Log.d("PoolThreadstartThread4","startThread4-----"+result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
     */
}
