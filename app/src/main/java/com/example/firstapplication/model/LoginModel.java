package com.example.firstapplication.model;

import com.example.firstapplication.base.BaseModel;
import com.example.firstapplication.contract.LoginContract;
import com.example.firstapplication.presenter.LoginPresenter;

/**
 * @author gelan
 * @date 2019/10/9
 * Description:
 */

public class LoginModel extends BaseModel<LoginPresenter, LoginContract.Model>{

    public LoginModel(LoginPresenter loginPresenter) {
        super(loginPresenter);
    }

    @Override
    public LoginContract.Model getContract() {
        return new LoginContract.Model() {
            @Override
            public void executeLogin(String name, String pwd) throws Exception {
                //模拟网络请求
            }
        };
    }
}
