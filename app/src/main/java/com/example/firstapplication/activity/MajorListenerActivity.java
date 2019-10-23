package com.example.firstapplication.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstapplication.R;
import com.example.firstapplication.base.AppApplication;
import com.example.firstapplication.base.IPresenter;
import com.example.firstapplication.base.SimpleBaseActivity;
import com.example.firstapplication.contract.MajorListenerContract;
import com.example.firstapplication.presenter.MajorListenerPresenter;
import com.example.firstapplication.view.WaveShowView;
import com.pcare.threadpool.PoolThread;
import com.pcare.threadpool.callback.ThreadCallback;
import com.pcare.threadpool.deliver.AndroidDeliver;

import butterknife.BindView;


/**
 * @Author: gl
 * @CreateDate: 2019/10/16
 * https://github.com/cokuscz/audioWaveCanvas
 * @Description:
 */
public class MajorListenerActivity extends SimpleBaseActivity<MajorListenerPresenter> implements MajorListenerContract.View {
    @BindView(R.id.listener_result)
    TextView listenerResult;
    @BindView(R.id.pluse_image)
    ImageView pluseImage;
    private MajorListenerPresenter presenter;


    @BindView(R.id.pluse_wave)
    WaveShowView waveShowView;


    @Override
    public int getLayoutId() {
        return R.layout.activity_major_listener;
    }

    @Override
    protected MajorListenerPresenter bindPresenter() {
        presenter = new MajorListenerPresenter((MajorListenerContract.View) getSelfActivity());
        return presenter;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startHeart();
    }

    @Override
    public void setHeartData(String data) {
        listenerResult.setText(getString(R.string.heart_info));
    }

    @Override
    public void setStomachData(String data) {
        listenerResult.setText(getString(R.string.stomach_info));

    }

    @Override
    public void startHeart() {
        presenter.startHeart();
        listenerResult.setText("检查项目：胸腔听诊 \n心率状态：检测中... \n每分钟心跳数：检测中...");

    }


    @Override
    public void startStomach() {
        pluseImage.setImageDrawable(getResources().getDrawable(R.mipmap.stomach));
        listenerResult.setText("检查项目：腹腔听诊 \n肠鸣音：检测中... \n状态：检测中...");
        presenter.startStomach();
    }

    @Override
    public void onHeartFinish() {
        startStomach();
    }

    @Override
    public void onStomachFinish() {
        Toast.makeText(MajorListenerActivity.this, getString(R.string.listener_tip_next), Toast.LENGTH_LONG).show();
        globalUserInfo.setListener(true);
        toNextPage();
        finish();
    }

    @Override
    public void showWaveData() {
//        waveShowView.setData(presenter.getHealth_data1(),waveShowView.SHOW_MODEL_DYNAMIC_SCROLL);
        waveShowView.setData(null,waveShowView.SHOW_MODEL_DYNAMIC_REFRESH);
    }

    @Override
    public void showWaveLine(float line) {
        waveShowView.showLine(line);

    }

//    @Override
//    protected void onPause() {
//        if(null != presenter)
//            presenter.detachView();
//        super.onPause();
//    }
}
