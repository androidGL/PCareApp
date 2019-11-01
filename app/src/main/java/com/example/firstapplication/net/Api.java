package com.example.firstapplication.net;

import com.example.firstapplication.entity.UserInfo;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    public static final String BASEURL = "https://api.apiopen.top";//测试api，
    public static final String name = "盗墓笔记";
    public static final String BASEURLFACE = "https://api-cn.faceplusplus.com";



    @POST("facepp/v3/detect")
    Single<ResponseBody> detectFace(@Query("api_key") String key,@Query("api_secret") String secret,
                                    @Query("image_base64") String base64);
//    @POST("facepp/v3/compare")
//    Single<ResponseBody> compareFace(@Query("api_key") String key,@Query("api_secret") String secret,
//                                     @Query("name") String faceToken,@Query("image_base64_2") String base64);

    @POST("facepp/v3/compare")
    Single<ResponseBody> compareFace(@Query("api_key") String key,@Query("api_secret") String secret,
                                     @Query("face_token1") String faceToken,@Query("image_base64_2") String base64);

    @GET("novelSearchApi")
    Single<ResponseBody> getCall(@Query("name") String ip);//测试ti

    //    @GET("novelSearchApi")
//    Call<ResponseBody> getCall2(@Query("name") String ip);//测试最简单的
    @GET("novelSearchApi")
    Observable<ResponseBody> getCall2(@Query("name") String ip);//测试+Rxjava


    @POST("searchAuthors")
    Single<ResponseBody> test(@Query("name") String base64);
    @POST("sug")
    @FormUrlEncoded
    Observable<ResponseBody> getCallPost(@Field("q") String q, @Field("code") String code);
}
