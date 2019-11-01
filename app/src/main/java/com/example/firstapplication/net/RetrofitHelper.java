package com.example.firstapplication.net;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author: gl
 * @CreateDate: 2019/10/30
 * @Description:
 */
public class RetrofitHelper {
    private static volatile RetrofitHelper instance;
    private final Retrofit retrofit;
    private String mBaseUrl;
    private static final int READ_TIMEOUT = 60;//读取超时时间（秒）
    private static final int CONN_TIMEOUT = 12;//连接超时时间（秒）

    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            showLog("retrofitBack = " + message);
        }
    });


    private RetrofitHelper() {
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Api.BASEURLFACE)
                .client(new OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .connectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                        .build())
                .build();
    }

    public static RetrofitHelper getInstance() {
        if (instance == null) {
            synchronized (RetrofitHelper.class) {
                if (instance == null) {
                    instance = new RetrofitHelper();
                }
            }
        }
        return instance;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    private void showLog(String msg) {
        Log.i(getClass().getName(), msg);
    }


}
