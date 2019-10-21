package com.example.firstapplication.model;

import com.example.firstapplication.contract.StartContract;
import com.example.firstapplication.entity.UserInfo;

/**
 * @Author: gl
 * @CreateDate: 2019/10/18
 * @Description:
 */
public class StartModel implements StartContract.Model {

    @Override
    public UserInfo getUserInfo(String id) {
        return new UserInfo("1234299","王二小",23,"女","脑科","华佗");

    }

}
