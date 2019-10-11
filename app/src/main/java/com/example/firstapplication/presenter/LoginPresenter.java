package com.example.firstapplication.presenter;

import com.example.firstapplication.base.BasePresenter;
import com.example.firstapplication.resultBean.UserInfo;
import com.example.firstapplication.contract.LoginContract;
import com.example.firstapplication.model.LoginModel;
import com.example.firstapplication.view.LoginActivity;

/**
 * @author gelan
 * @date 2019/10/9
 * Description:
 */

public class LoginPresenter extends BasePresenter<LoginActivity, LoginModel, LoginContract.Presenter> {
    @Override
    public LoginContract.Presenter getContract() {
     return new LoginContract.Presenter<UserInfo>(){

         @Override
         public void requestLogin(String name, String pwd) {
             //P层3种风格
             try {
                 //第1种,P层不做逻辑处理，只是交给model层
                 m.getContract().executeLogin(name,pwd);
                 //第2种,功能模块做
                 //第3种，P层自己处理

             }catch (Exception e){

             }

         }

         @Override
         public void responseResult(UserInfo userInfo) {
             getView().getContract().handlerResult(userInfo);

         }
     };
    }

    @Override
    protected LoginModel getModel() {
        return new LoginModel(this);
    }
}
