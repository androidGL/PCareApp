package com.example.firstapplication.activity;

import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.view.TextureView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstapplication.R;
import com.example.firstapplication.base.SimpleBaseActivity;
import com.example.firstapplication.contract.MajorLookContarct;
import com.example.firstapplication.presenter.MajorLookPresenter;

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
        return textureView.getSurfaceTexture();
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
