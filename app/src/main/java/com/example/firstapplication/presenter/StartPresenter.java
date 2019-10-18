package com.example.firstapplication.presenter;

import com.example.firstapplication.base.BasePresenter;
import com.example.firstapplication.contract.StartContract;
import com.example.firstapplication.model.StartModel;

/**
 * @Author: gl
 * @CreateDate: 2019/10/18
 * @Description:
 */
public class StartPresenter extends BasePresenter<StartContract.View> implements StartContract.Presenter {

    private StartModel startModel;
    public StartPresenter(StartContract.View view) {
        super(view);
        this.startModel = new StartModel();
    }

    @Override
    public void getUserInfo(String id) {
        if(isViewAttach()){
            mWeakActivity.get().setUserInfo(startModel.getUserInfo(id));
        }
    }

    @Override
    public void startCountDown() {

    }

    @Override
    public void startOtherTask() {

    }
}
