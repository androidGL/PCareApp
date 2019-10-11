package com.example.firstapplication.http;

import com.example.firstapplication.resultBean.UserInfo;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author gelan
 * @date 2019/10/10
 * Description:网络请求接口
 */

public interface Api {
    @GET("")
    Call<UserInfo> getUser();//如果想直接获得Response Body的内容，可以定义网络请求返回值为Call<ResponseBody>
}
