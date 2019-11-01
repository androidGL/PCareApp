package com.example.firstapplication.model;

import android.os.Handler;

import com.example.firstapplication.contract.VerifyFaceContarct;
import com.example.firstapplication.net.Api;
import com.example.firstapplication.net.RetrofitHelper;
import com.example.firstapplication.utils.ConstantFile;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * @Author: gl
 * @CreateDate: 2019/10/30
 * @Description:
 */
public class VerifyFaceModel implements VerifyFaceContarct.Model {

    @Override
    public void getUserInfo() {

    }

    @Override
    public Disposable detectFace(String base64, DisposableSingleObserver<ResponseBody> observer) {
        return RetrofitHelper
                .getInstance()
                .getRetrofit()
                .create(Api.class)
//                .test("李白")
                .detectFace(ConstantFile.FACEKEY,ConstantFile.FACESECRET,base64)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(observer);
    }

    @Override
    public Disposable compareFace(String faceToken,String base64, DisposableSingleObserver<ResponseBody> observer) {
        return RetrofitHelper
                .getInstance()
                .getRetrofit()
                .create(Api.class)
                .compareFace(ConstantFile.FACEKEY,ConstantFile.FACESECRET,faceToken,base64)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(observer);

    }
}
