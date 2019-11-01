package com.example.firstapplication.net;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author: gl
 * @CreateDate: 2019/11/1
 * @Description:
 */
public interface ApiFace {
    @Headers({})

    @POST("facepp/v3/detect")
    Single<ResponseBody> detectFace(@Query("api_key") String key, @Query("api_secret") String secret,
                                    @Query("image_base64") String base64);

    @POST("facepp/v3/compare")
    Single<ResponseBody> compareFace(@Query("api_key") String key,@Query("api_secret") String secret,
                                     @Query("face_token1") String faceToken,@Query("image_base64_2") String base64);

    @GET("novelSearchApi")
    Single<ResponseBody> getCall(@Query("name") String ip);//测试ti

    //    @GET("novelSearchApi")
//    Call<ResponseBody> getCall2(@Query("name") String ip);//测试最简单的
    @GET("novelSearchApi")
    Observable<ResponseBody> getCall2(@Query("name") String ip);//测试+Rxjava

    @POST("sug")
    @FormUrlEncoded
    Observable<ResponseBody> getCallPost(@Field("q") String q, @Field("code") String code);
}
