package com.example.firstapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.firstapplication.R;
import com.example.firstapplication.base.SimpleBaseActivity;
import com.example.firstapplication.contract.StartContract;
import com.example.firstapplication.entity.UserInfo;
import com.example.firstapplication.presenter.StartPresenter;

import butterknife.BindView;

public class StartActivity extends SimpleBaseActivity<StartPresenter> implements StartContract.View {
    @BindView(R.id.start_timer)
    TextView displayNum;

    @BindView(R.id.user_info)
    TextView userInfoView;

    @BindView(R.id.look_finish_text)
    TextView lookFinish;
    @BindView(R.id.listener_finish_text)
    TextView listenerFinish;
    @BindView(R.id.request_finish_text)
    TextView requestFinish;
    @BindView(R.id.pulse_finish_text)
    TextView pulseFinish;

    private StartPresenter startPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.layout_start;
    }

    @Override
    protected StartPresenter bindPresenter() {
        startPresenter = new StartPresenter((StartContract.View) getSelfActivity());
        return startPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //11表示时身份证ID
        startPresenter.getUserInfo("11");
        startPresenter.startCountDown();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(globalUserInfo.isLook())
            lookFinish.setText(getResources().getString(R.string.finish));
        if(globalUserInfo.isListener())
            listenerFinish.setText(getResources().getString(R.string.finish));
        if(globalUserInfo.isRequest())
            requestFinish.setText(getResources().getString(R.string.finish));
        if(globalUserInfo.isPulse())
            pulseFinish.setText(getResources().getString(R.string.finish));
    }

    public void startTreat(View view) {
        displayNum.setVisibility(View.GONE);
        startPresenter.finishCountDown();
        switch (view.getId()){
            case R.id.start_look:
                startActivity(new Intent(StartActivity.this,MajorLookActivity.class));
                break;
            case R.id.start_listener:
                startActivity(new Intent(StartActivity.this,MajorListenerActivity.class));
                break;
            case R.id.start_request:
                startActivity(new Intent(StartActivity.this,MajorRequestActivity.class));
                break;
            case R.id.start_pulse:
                startActivity(new Intent(StartActivity.this,MajorPulseActivity.class));
                break;
        }
    }

    @Override
    public void setUserInfo(UserInfo userInfo) {
        globalUserInfo = userInfo;
        userInfoView.setText("就诊号："+userInfo.getId()
                +"\n个人信息："+userInfo.getName()+"/"+userInfo.getAge()+"岁/"+userInfo.getSex()
        +"\n科室："+userInfo.getType()
        +"\n专家姓名："+userInfo.getDoctorName());

    }

    @Override
    public void setCountDown(String num) {
        displayNum.setText(num);
    }

    @Override
    public void startOtherActivity(boolean isAuto) {
        startPresenter.finishCountDown();
        displayNum.setVisibility(View.GONE);
        if(isAuto){
            toNextPage();
//            startActivity(new Intent(StartActivity.this,MajorLookActivity.class));
        }
    }

}
