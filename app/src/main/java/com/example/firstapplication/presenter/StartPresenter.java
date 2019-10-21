package com.example.firstapplication.presenter;

import android.os.CountDownTimer;
import com.example.firstapplication.base.BasePresenter;
import com.example.firstapplication.contract.StartContract;
import com.example.firstapplication.model.StartModel;

/**
 * @Author: gl
 * @CreateDate: 2019/10/18
 * @Description:
 */
public class StartPresenter extends BasePresenter<StartContract.View> implements StartContract.Presenter {

    private CountDownTimer timer;
    private StartModel startModel;
    public StartPresenter(StartContract.View view) {
        super(view);
        this.startModel = new StartModel();
    }

    @Override
    public void getUserInfo(String id) {
        if(isViewAttach()){
            getView().setUserInfo(startModel.getUserInfo(id));
        }
    }

    @Override
    public void startCountDown() {
        timer=new CountDownTimer(10*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                getView().setCountDown(String.valueOf((int)millisUntilFinished/1000)+"s后开始为您全面预诊");
            }

            @Override
            public void onFinish() {
                getView().startOtherActivity(true);
                finishCountDown();
            }
        };
        timer.start();

    }

    @Override
    public void finishCountDown() {
        timer.cancel();
    }

}
