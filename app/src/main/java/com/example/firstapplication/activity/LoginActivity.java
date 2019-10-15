package com.example.firstapplication.activity;

import com.example.firstapplication.base.BaseActivity;
import com.example.firstapplication.resultBean.UserInfo;
import com.example.firstapplication.contract.LoginContract;
import com.example.firstapplication.presenter.LoginPresenter;

/**
 * @author gelan
 * @date 2019/10/9
 * Description:
 */

public class LoginActivity extends BaseActivity<LoginPresenter,LoginContract.View> {
    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter();
    }

    public LoginContract.View<UserInfo> getContract() {
        return new LoginContract.View<UserInfo>(){

            @Override
            public void handlerResult(UserInfo userInfo) {
                if(null != userInfo){
                    //登录成功
                }
            }
        };
    }
}
