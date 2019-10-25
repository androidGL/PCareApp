package com.example.firstapplication.contract;

import com.example.firstapplication.base.BasePresenter;
import com.example.firstapplication.base.IPresenter;
import com.example.firstapplication.base.IView;

/**
 * @Author: gl
 * @CreateDate: 2019/10/22
 * @Description:
 */
public interface MajorPulseContract {
    interface Model{
        String getPulseInfo();
    }
    interface View extends IView {
        void startPulse();
        void finishPulse(String info);
        void toOtherPage();
        void setWaveData();
        void showWaveLine(float line);
    }
    interface Presenter {
        void startPulse();
        void finishPulse();
    }
}
