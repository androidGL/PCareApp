package com.example.firstapplication.activity;

import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.firstapplication.R;
import com.example.firstapplication.base.AppApplication;
import com.example.firstapplication.base.IPresenter;
import com.example.firstapplication.base.SimpleBaseActivity;
import com.example.firstapplication.contract.MajorLookContarct;
import com.example.firstapplication.presenter.MajorListenerPresenter;
import com.example.firstapplication.presenter.MajorLookPresenter;
import com.pcare.threadpool.PoolThread;
import com.pcare.threadpool.deliver.AndroidDeliver;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

/**
 * @Author: gl
 * @CreateDate: 2019/10/16
 * @Description:
 */
public class MajorLookActivity extends SimpleBaseActivity<MajorLookPresenter> implements MajorLookContarct.View {


    @BindView(R.id.look_tip)
    TextView tip;

    @BindView(R.id.look_container)
    TextureView textureView;

    private MajorLookPresenter presenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_major_look;
    }

    @Override
    protected MajorLookPresenter bindPresenter() {
        presenter = new MajorLookPresenter((MajorLookActivity) getSelfActivity());
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startCamera();
    }

    @Override
    public void startCamera() {
        presenter.startCameraThread();
        if(!textureView.isAvailable()){
            textureView.setSurfaceTextureListener(presenter.getTextureListener());
        }else {
            presenter.startPreview();
        }
    }

    @Override
    public SurfaceTexture getSurfaceTexture(){
        SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
        return surfaceTexture;
    }

    @Override
    public void startFace() {
        presenter.startFace();
    }

    @Override
    public void finishFace() {
        tip.setText(getString(R.string.look_tip2));
    }

    @Override
    public void startTougue() {
        presenter.startTougue();
    }

    @Override
    public void finishTougue() {
        Toast.makeText(MajorLookActivity.this, getString(R.string.look_tip_next), Toast.LENGTH_LONG).show();
        globalUserInfo.setLook(true);
        toNextPage();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.closeSession();
    }
}
