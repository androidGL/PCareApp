package com.example.firstapplication.contract;

import android.widget.TextView;

import com.example.firstapplication.base.IView;
import com.example.firstapplication.base.SimpleBaseActivity;
import com.example.firstapplication.entity.UserInfo;

/**
 * @Author: gl
 * @CreateDate: 2019/10/18
 * @Description:
 */
public interface StartContract {
    interface Model {
        UserInfo getUserInfo(String id);
    }
    interface View extends IView {
        void setUserInfo(UserInfo userInfo);
        void setCountDown(String num);
        void startOtherActivity(boolean isAuto);
    }
    interface Presenter{
        void getUserInfo(String id);
        void startCountDown();
        void finishCountDown();
    }
}
