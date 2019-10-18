package com.example.firstapplication.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Surface;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstapplication.R;
import com.example.firstapplication.base.AppApplication;
import com.example.firstapplication.base.IPresenter;
import com.example.firstapplication.base.SimpleBaseActivity;
import com.pcare.threadpool.deliver.AndroidDeliver;

import butterknife.BindView;


/**
 * @Author: gl
 * @CreateDate: 2019/10/16
 * https://github.com/cokuscz/audioWaveCanvas
 * @Description:
 */
public class MajorListenerActivity extends SimpleBaseActivity {
//    @BindView(R.id.listener_heart_wave)
//    SurfaceView hearWaveView;
    private final int TIP_HEART = 0;
    private final int TIP_STOMACH = 1;
    @BindView(R.id.listener_heart)
    TextView heartText;
    @BindView(R.id.listener_stomach)
    TextView stomachText;
    @BindView(R.id.pluse_tip)
    TextView pluseTip;
    @BindView(R.id.pluse_image)
    ImageView pluseImage;
    @Override
    public int getLayoutId() {
        return R.layout.activity_major_listener;
    }

    @Override
    protected IPresenter bindPresenter() {
        return null;
    }


    private void listener(){
        Handler handler = new Handler() {
            @SuppressLint("ResourceType")
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case TIP_HEART:
                        heartText.setBackgroundColor(getResources().getColor(R.color.unselect));
                        stomachText.setBackgroundColor(getResources().getColor(R.color.select));
                        pluseTip.setText(getString(R.string.stomach_info));
                        pluseImage.setImageDrawable(getResources().getDrawable(R.mipmap.stomach));
                        break;
                    case TIP_STOMACH:
                        Toast.makeText(MajorListenerActivity.this, getString(R.string.listener_tip_next), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MajorListenerActivity.this,MajorRequestActivity.class));
                        finish();
                        break;
                }
            }
        };
        AppApplication.getInstance().getExecutor()
                .setName("listener任务-胸腔")
                .setDeliver(new AndroidDeliver()).execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = TIP_HEART;
                handler.sendMessage(message);
            }
        });
        AppApplication.getInstance().getExecutor()
                .setName("listener任务-腹腔")
                .setDeliver(new AndroidDeliver()).execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(14000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = TIP_STOMACH;
                handler.sendMessage(message);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener();
    }
}
