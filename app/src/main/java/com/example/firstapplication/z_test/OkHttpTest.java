package com.example.firstapplication.z_test;


import android.util.Log;

import com.example.firstapplication.net.Api;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class OkHttpTest {
//    public static void main(String[] args){
//        OkHttpTest okHttpTest = new OkHttpTest();
//        okHttpTest.request();
//    }
    //https://suggest.taobao.com/sug?code=utf-8&q=书
    public Retrofit getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://suggest.taobao.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    public void request(){
        //异步请求
        Api request = getRetrofit().create(Api.class);
        Observable<ResponseBody> call = request.getCallPost("书","utf-8");
        call.subscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseBody value) {
                Log.i("aaaaaaaaaaaa",value.toString());
            }

            @Override
            public void onError(Throwable e) {
                Log.i("aaaaaaaaaaaa","连接失败");
            }

            @Override
            public void onComplete() {

            }
        });


        //同步请求
//        try{
//            Response<UserInfo> response = call.execute();
//            response.body().toString();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
    }
//    private void simple() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Api.BASEURL)
//                .build();
//        Call<ResponseBody> call = retrofit.create(Api.class).getCall2(Api.name);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    Log.i("bbbbb","aa"+response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.i("bbbbb","aa"+t.getMessage());
//
//            }
//        });
//    }
//    private void rxAndroid(){
//        new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .baseUrl(Api.BASEURL)
//                .build()
//                .create(Api.class).getCall2(Api.name)
//                .subscribeOn(Schedulers.newThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<ResponseBody>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(ResponseBody value) {
//                        try {
//                            showToast("2"+value.string());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }
}
