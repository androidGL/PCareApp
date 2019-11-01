package com.example.firstapplication.activity;

import android.content.Intent;
import android.view.View;

import com.example.firstapplication.R;
import com.example.firstapplication.base.IPresenter;
import com.example.firstapplication.base.SimpleBaseActivity;

/**
 * @Author: gl
 * @CreateDate: 2019/10/31
 * @Description:
 */
public class IndexActivity extends SimpleBaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_index;
    }

    @Override
    protected IPresenter bindPresenter() {
        return null;
    }
    public void toNext(View view) {
        startActivity(new Intent(this,VerifyFaceActivity.class));
    }
}
