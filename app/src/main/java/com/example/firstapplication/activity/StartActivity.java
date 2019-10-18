package com.example.firstapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.example.firstapplication.R;
import com.example.firstapplication.base.SimpleBaseActivity;

import butterknife.BindView;

public class StartActivity extends SimpleBaseActivity {
    @BindView(R.id.start_timer)
    TextView displayNum;
    private CountDownTimer timer;

    @Override
    public int getLayoutId() {
        return R.layout.layout_start;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timer=new CountDownTimer(10*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                displayNum.setText(String.valueOf((int)millisUntilFinished/1000)+"s后开始为您全面预诊");
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(StartActivity.this,MajorLookActivity.class));
//                displayNum.setVisibility(View.GONE);
                timer.cancel();
            }
        };
        timer.start();
    }

    public void startTreat(View view) {
        timer.cancel();
        displayNum.setVisibility(View.GONE);
        switch (view.getId()){
            case R.id.start_look:
                startActivity(new Intent(StartActivity.this,MajorLookActivity.class));
                timer.cancel();
                break;
            case R.id.start_listener:
                startActivity(new Intent(StartActivity.this,MajorListenerActivity.class));
                timer.cancel();
                break;
            case R.id.start_request:
                startActivity(new Intent(StartActivity.this,MajorRequestActivity.class));
                timer.cancel();
                break;
            case R.id.start_pulse:
                startActivity(new Intent(StartActivity.this,MajorPulseActivity.class));
                timer.cancel();
                break;
        }
    }
}
