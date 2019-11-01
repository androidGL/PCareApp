package com.example.firstapplication.activity;

import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;

import com.example.firstapplication.R;
import com.example.firstapplication.base.SimpleBaseActivity;
import com.example.firstapplication.contract.VerifyFaceContarct;
import com.example.firstapplication.entity.UserInfo;
import com.example.firstapplication.presenter.VerifyFacePresenter;

import butterknife.BindView;

/**
 * @Author: gl
 * @CreateDate: 2019/10/30
 * @Description:
 */
public class VerifyFaceActivity extends SimpleBaseActivity<VerifyFacePresenter> implements VerifyFaceContarct.View {

    @BindView(R.id.look_start)
    TextView start;

    @BindView(R.id.look_container)
    TextureView textureView;
    private String faceId = "12345";

    @Override
    public int getLayoutId() {
        return R.layout.activity_verify_face;
    }

    @Override
    protected VerifyFacePresenter bindPresenter() {
        return new VerifyFacePresenter((VerifyFaceContarct.View) getSelfActivity());
    }

    @Override
    public void start() {
        presenter.openCamera(textureView);
//        presenter.verifyFaceRequest(faceId);
        presenter.detectFaceRequest(faceId);

    }

    @Override
    public void setUserInfo(UserInfo info) {

    }

    @Override
    public void setVerifyResult(String msg) {
        showToast(msg);

    }

    @Override
    public void showToast(String msg) {
        Log.i("aaaaaaa", msg);

    }
    @Override
    public SurfaceTexture getSurfaceTexture(){
        return textureView.getSurfaceTexture();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.closeCamera();
    }
}
