package com.example.firstapplication.z_test;

import com.example.firstapplication.entity.UserInfo;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    //返回类型Call是接收数据的类
    @GET("sug")
    Call<ResponseBody> getCall(@Query("q") String name,@Query("code") String code);
    @POST("sug")
    @FormUrlEncoded
    Observable<ResponseBody> getCallPost(@Field("q") String q, @Field("code") String code);
}
