package com.example.firstapplication.presenter;

import android.os.Handler;
import android.os.Message;

import com.example.firstapplication.base.AppApplication;
import com.example.firstapplication.base.BasePresenter;
import com.example.firstapplication.contract.MajorPulseContract;
import com.example.firstapplication.model.MajorPulseModel;
import com.pcare.threadpool.deliver.AndroidDeliver;

/**
 * @Author: gl
 * @CreateDate: 2019/10/22
 * @Description:
 */
public class MajorPulsePresenter extends BasePresenter<MajorPulseContract.View> implements MajorPulseContract.Presenter {

    private MajorPulseModel model;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    getView().finishPulse(model.getPulseInfo());
                    break;
                case 1:
                    getView().toOtherPage();
                    break;
            }

        }
    };
    public MajorPulsePresenter(MajorPulseContract.View view) {
        super(view);
        model = new MajorPulseModel();
    }

    @Override
    public void startPulse() {
        if (isViewAttach()) {
            AppApplication.getInstance().getExecutor()
                    .setName("pluse任务")
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
        }
    }

    @Override
    public void finishPulse() {
        if (isViewAttach()) {
            AppApplication.getInstance().getExecutor()
                    .setName("pluse任务1")
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
        }
    }
}
