package com.example.firstapplication.contract;

import android.graphics.SurfaceTexture;
import android.view.TextureView;

import com.example.firstapplication.base.BasePresenter;
import com.example.firstapplication.base.IView;
import com.example.firstapplication.entity.UserInfo;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import okhttp3.ResponseBody;

/**
 * @Author: gl
 * @CreateDate: 2019/10/30
 * @Description: 用户身份认证
 */
public interface VerifyFaceContarct {
    //1,设置用户信息
    //2，显示用户信息
    //3，打开摄像头
    //4，人脸验证
    //5，请求人脸验证接口
    //6，验证成功后更新界面
    interface Model{
        void getUserInfo();//请求接口获取用户信息
        Disposable detectFace(String base64, DisposableSingleObserver<ResponseBody> observer);//注册人脸
        Disposable compareFace(String faceToken, String base64, DisposableSingleObserver<ResponseBody> observer);//验证人脸
    }
    interface Presenter {
        void setUserInfo(UserInfo info);//设置用户信息
        void openCamera(TextureView textureView);//打开摄像头
        void closeCamera();
        void detectFaceRequest(String faceId);//验证人脸
        void verifyFaceRequest(String faceId);//验证人脸
        void verifyFaceResult();//人脸验证结果
    }
    interface View extends IView{
        void setUserInfo(UserInfo info);//显示用户信息
        void setVerifyResult(String msg);//人脸验证成功
        void showToast(String msg);//土司
        SurfaceTexture getSurfaceTexture();
    }
}
