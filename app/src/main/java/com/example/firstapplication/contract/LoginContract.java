package com.example.firstapplication.contract;

import com.example.firstapplication.resultBean.BaseEntity;
/**
 * @author gelan
 * @date 2019/10/9
 * Description:将model层，presenter层协商的共同业务，封装成接口，契约，合同。
 */
public interface LoginContract {
    interface Model{
        void executeLogin(String name, String pwd) throws Exception;
    }
    interface View<T extends BaseEntity>{
        //请求结果
        void handlerResult(T t);
    }
    interface Presenter<T extends BaseEntity>{
        //登录请求（接收到view层的指令，可以自己做，也可以让model层做）
        void requestLogin(String name,String pwd);
        //结果响应（接收到model层处理的结果，通知view层刷新）
        void responseResult(T t);

    }
}
