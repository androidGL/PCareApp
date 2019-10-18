package com.example.firstapplication.activity;

import com.example.firstapplication.R;
import com.example.firstapplication.base.IPresenter;
import com.example.firstapplication.base.SimpleBaseActivity;

/**
 * @Author: gl
 * @CreateDate: 2019/10/18
 * @Description:
 */
public class HelpActivity extends SimpleBaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    protected IPresenter bindPresenter() {
        return null;
    }
}
