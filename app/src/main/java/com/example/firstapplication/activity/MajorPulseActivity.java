package com.example.firstapplication.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.firstapplication.R;
import com.example.firstapplication.base.SimpleBaseActivity;
import com.example.firstapplication.contract.MajorPulseContract;
import com.example.firstapplication.presenter.MajorPulsePresenter;
import com.example.firstapplication.view.WaveShowView;

import butterknife.BindView;

/**
 * @Author: gl
 * @CreateDate: 2019/10/16
 * @Description:
 */
public class MajorPulseActivity extends SimpleBaseActivity<MajorPulsePresenter> implements MajorPulseContract.View {
    private MajorPulsePresenter presenter;

    @BindView(R.id.pluse_tip)
    TextView pulseInfoText;

    @BindView(R.id.pluse_wave)
    WaveShowView waveShowView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startPulse();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_major_pluse;
    }

    @Override
    protected MajorPulsePresenter bindPresenter() {
        presenter=new MajorPulsePresenter((MajorPulseContract.View) getSelfActivity());
        return presenter;
    }


    @Override
    public void startPulse() {
        presenter.startPulse();

    }

    @Override
    public void finishPulse(String info) {
        pulseInfoText.setText(info);
        presenter.finishPulse();
    }

    @Override
    public void toOtherPage() {
        globalUserInfo.setPulse(true);
//        toNextPage();
//        finish();

    }
    @Override
    public void setWaveData() {
//        waveShowView.setData(presenter.getHealth_data1());
//        waveShowView.setData(null,waveShowView.SHOW_MODEL_DYNAMIC_REFRESH);
    }

    @Override
    public void showWaveLine(float line) {
        waveShowView.showLine(line);

    }
}
