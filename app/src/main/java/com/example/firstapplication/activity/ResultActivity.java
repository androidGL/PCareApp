package com.example.firstapplication.activity;

import com.example.firstapplication.R;
import com.example.firstapplication.base.IPresenter;
import com.example.firstapplication.base.SimpleBaseActivity;

/**
 * @Author: gl
 * @CreateDate: 2019/10/22
 * @Description:
 */
public class ResultActivity extends SimpleBaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_result;
    }

    @Override
    protected IPresenter bindPresenter() {
        return null;
    }
}
