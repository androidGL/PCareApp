package com.example.firstapplication.contract;

import com.example.firstapplication.base.IView;

/**
 * @Author: gl
 * @CreateDate: 2019/10/22
 * @Description: 脉诊
 */
public interface MajorPulseContract {
    interface Model{
        String getPulseInfo();
    }
    interface View extends IView {
        void startPulse();
        void finishPulse(String info);
        void toOtherPage();
        void showWaveLine(float data);
    }
    interface Presenter {
        void startPulse();
        void finishPulse();
    }
}
