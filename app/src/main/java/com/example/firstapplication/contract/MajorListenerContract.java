package com.example.firstapplication.contract;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.firstapplication.base.IView;

/**
 * @Author: gl
 * @CreateDate: 2019/10/21
 * @Description:
 */
public interface MajorListenerContract {
    interface Model{
        String getHeartData();
        String getStomachData();
        void startHeart();
        void startStomach();

    }
    interface View extends IView{
        void setHeartData(String data);
        void setStomachData(String data);
        void startHeart();
        void startStomach();
        void onHeartFinish();
        void onStomachFinish();

        void showWaveLine(float data);

    }
    interface Presenter{
        void setHeartData(String data);
        void setStomachData(String data);
        void startHeart();
        void startStomach();

    }
}
