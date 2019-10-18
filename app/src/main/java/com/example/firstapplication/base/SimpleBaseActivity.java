package com.example.firstapplication.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.firstapplication.activity.HelpActivity;

import butterknife.ButterKnife;

public abstract class SimpleBaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
    }
    public abstract int getLayoutId();

    public void startHelpActivity(View view) {
        startActivity(new Intent(this, HelpActivity.class));
    }
}
