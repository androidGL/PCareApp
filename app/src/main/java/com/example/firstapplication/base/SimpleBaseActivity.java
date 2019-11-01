package com.example.firstapplication.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.firstapplication.activity.HelpActivity;
import com.example.firstapplication.activity.MajorListenerActivity;
import com.example.firstapplication.activity.MajorLookActivity;
import com.example.firstapplication.activity.MajorPulseActivity;
import com.example.firstapplication.activity.MajorRequestActivity;
import com.example.firstapplication.activity.ResultActivity;
import com.example.firstapplication.entity.UserInfo;
import com.example.firstapplication.utils.ConstantFile;

import butterknife.ButterKnife;

public abstract class SimpleBaseActivity<P extends IPresenter> extends Activity implements IView{
    protected P presenter;
    protected static UserInfo globalUserInfo = new UserInfo();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        presenter = bindPresenter();
        start();
    }
    public abstract int getLayoutId();
    // 绑定Presenter
    protected abstract P bindPresenter();

    //开始了
    public void start(){

    }


    @Override
    public Activity getSelfActivity() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter != null)
            presenter.detachView();
    }

    public void startHelpActivity(View view) {
        startActivity(new Intent(this, HelpActivity.class));
    }

    public void toNextPage(){
        switch (globalUserInfo.getNextPage()){
            case ConstantFile.LOOK:
                startActivity(new Intent(this, MajorLookActivity.class));
                break;
            case ConstantFile.LISTENER:
                startActivity(new Intent(this, MajorListenerActivity.class));
                break;
            case ConstantFile.REQUEST:
                startActivity(new Intent(this, MajorRequestActivity.class));
                break;
            case ConstantFile.PLUSE:
                startActivity(new Intent(this, MajorPulseActivity.class));
                break;
            case ConstantFile.ALL:
                startActivity(new Intent(this, ResultActivity.class));
                break;

        }
    }
}
