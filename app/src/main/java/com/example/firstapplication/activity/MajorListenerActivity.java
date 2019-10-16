package com.example.firstapplication.activity;

import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceView;

import com.example.firstapplication.R;
import com.example.firstapplication.base.SimpleBaseActivity;

import butterknife.BindView;

/**
 * @Author: gl
 * @CreateDate: 2019/10/16
 * @Description:
 */
public class MajorListenerActivity extends SimpleBaseActivity {
    @BindView(R.id.listener_heart_wave)
    SurfaceView hearWaveView;
    @Override
    public int getLayoutId() {
        return R.layout.activity_major_listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
