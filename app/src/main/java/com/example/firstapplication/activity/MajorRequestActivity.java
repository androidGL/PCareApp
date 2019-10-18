package com.example.firstapplication.activity;

import android.content.Intent;
import android.view.View;

import com.example.firstapplication.R;
import com.example.firstapplication.base.SimpleBaseActivity;
import com.example.firstapplication.contract.LoginContract;

/**
 * @Author: gl
 * @CreateDate: 2019/10/16
 * @Description:
 */
public class MajorRequestActivity extends SimpleBaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_major_request;
    }

    public void startRequest(View view) {
        Intent intent = new Intent(MajorRequestActivity.this,QuestionActivity.class);
        switch (view.getId()){
            case R.id.request_type_select:
                intent.putExtra("type","select");
                break;
            case R.id.request_type_speak:
                intent.putExtra("type","speak");
                break;
        }
        startActivity(intent);
        finish();
    }
}
