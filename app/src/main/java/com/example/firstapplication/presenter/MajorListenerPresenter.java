package com.example.firstapplication.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.firstapplication.base.AppApplication;
import com.example.firstapplication.base.BasePresenter;
import com.example.firstapplication.contract.MajorListenerContract;
import com.example.firstapplication.model.MajorListenerModel;
import com.pcare.threadpool.callback.ThreadCallback;
import com.pcare.threadpool.deliver.AndroidDeliver;

/**
 * @Author: gl
 * @CreateDate: 2019/10/21
 * @Description:
 */
public class MajorListenerPresenter extends BasePresenter<MajorListenerContract.View> implements MajorListenerContract.Presenter {
    private MajorListenerModel model;
    private final int TIP_HEART = 0;
    private final int TIP_STOMACH = 1;
    private final int TIP_HEART1 = 10;
    private final int TIP_STOMACH1 = 11;
    private String heartData, stomachData;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIP_HEART:
                    heartData = "这儿是听胸腔的结果";
                    setHeartData(heartData);
                    break;
                case TIP_STOMACH:
                    stomachData = "这儿是听腹腔的结果";
                    setStomachData(stomachData);
                    break;
                case TIP_HEART1:
                    getView().onHeartFinish();
                    break;
                case TIP_STOMACH1:
                    getView().onStomachFinish();
                    break;
            }
        }
    };

    public MajorListenerPresenter(MajorListenerContract.View view) {
        super(view);
        model = new MajorListenerModel(this);
    }

    @Override
    public void setHeartData(String heartData) {
        if (isViewAttach()) {
            getView().setHeartData(heartData);
            AppApplication.getInstance().getExecutor()
                    .setName("listener任务-胸腔2")
                    .setDeliver(new AndroidDeliver()).execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message message = new Message();
                    message.what = 10;
                    handler.sendMessage(message);
                }
            });
        }
    }

    @Override
    public void setStomachData(String data) {
        if (isViewAttach()) {
            getView().setStomachData(data);

            AppApplication.getInstance().getExecutor()
                    .setName("listener任务-腹腔2")
                    .setDeliver(new AndroidDeliver()).execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message message = new Message();
                    message.what = 11;
                    handler.sendMessage(message);
                }
            });
        }
    }

    @Override
    public void startHeart() {
        AppApplication.getInstance().getExecutor()
                .setName("listener任务-胸腔")
                .setDeliver(new AndroidDeliver()).execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = 0;
                handler.sendMessage(message);
            }
        });
//        model.startHeart();
    }

    @Override
    public void startStomach() {
        AppApplication.getInstance().getExecutor()
                .setName("listener任务-腹腔")
                .setDeliver(new AndroidDeliver()).execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        });
//        model.startStomach();
    }

}
