package com.example.firstapplication.activity;

import com.example.firstapplication.R;
import com.example.firstapplication.base.IPresenter;
import com.example.firstapplication.base.SimpleBaseActivity;

/**
 * @Author: gl
 * @CreateDate: 2019/10/16
 * @Description:
 */
public class ReceiveActivity extends SimpleBaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_receive;
    }

    @Override
    protected IPresenter bindPresenter() {
        return null;
    }
}
