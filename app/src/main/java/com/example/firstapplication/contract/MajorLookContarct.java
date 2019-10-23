package com.example.firstapplication.contract;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.TextureView;

import com.example.firstapplication.base.IView;

/**
 * @Author: gl
 * @CreateDate: 2019/10/23
 * @Description:
 */
public interface MajorLookContarct {
    interface Model{

    }
    interface Presenter{
        void openCamera();
        void startPreview();
        TextureView.SurfaceTextureListener getTextureListener();
        void startFace();
        void startTougue();
        void closeSession();


    }
    interface View extends IView{
        void startCamera();//开启摄像头
        SurfaceTexture getSurfaceTexture();

        void startFace();
        void finishFace();
        void startTougue();
        void finishTougue();
    }
}
