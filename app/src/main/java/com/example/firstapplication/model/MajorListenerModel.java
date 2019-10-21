package com.example.firstapplication.model;

import android.util.Log;

import com.example.firstapplication.base.AppApplication;
import com.example.firstapplication.contract.MajorListenerContract;
import com.example.firstapplication.presenter.MajorListenerPresenter;
import com.pcare.threadpool.callback.ThreadCallback;
import com.pcare.threadpool.deliver.AndroidDeliver;

/**
 * @Author: gl
 * @CreateDate: 2019/10/21
 * @Description:
 */
public class MajorListenerModel implements MajorListenerContract.Model {
    private final String TIP_HEART = "listener任务-胸腔";
    private final String TIP_STOMACH = "listener任务-腹腔";
    private String heartData,stomachData;
    private MajorListenerPresenter presenter;

    public MajorListenerModel(MajorListenerPresenter presenter){
        this.presenter = presenter;
    }

    ThreadCallback threadCallback=new ThreadCallback() {
        @Override
        public void onCompleted(String threadName) {
            Log.i("aaaaaaaaaaa","这儿是听胸腔的结果22222");
            switch (threadName) {
                case TIP_HEART:
                    heartData = "这儿是听胸腔的结果";
                    presenter.setHeartData(heartData);
                    break;
                case TIP_STOMACH:
                    stomachData = "这儿是听腹腔的结果";
                    presenter.setHeartData(stomachData);
                    break;
            }
        }

        @Override
        public void onError(String threadName, Throwable throwable) {

        }

        @Override
        public void onStart(String threadName) {

        }
    };
    @Override
    public String getHeartData() {
        return heartData;
    }

    @Override
    public String getStomachData() {
        return stomachData;
    }

    @Override
    public void startHeart() {
        AppApplication.getInstance().getExecutor()
                .setName(TIP_HEART)
                .setCallback(threadCallback)
                .setDeliver(new AndroidDeliver()).execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void startStomach() {
        AppApplication.getInstance().getExecutor()
                .setName(TIP_STOMACH)
                .setCallback(threadCallback)
                .setDeliver(new AndroidDeliver()).execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
