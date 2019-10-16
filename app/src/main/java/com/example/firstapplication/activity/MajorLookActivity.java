package com.example.firstapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.firstapplication.R;
import com.example.firstapplication.base.AppApplication;
import com.example.firstapplication.base.SimpleBaseActivity;
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
public class MajorLookActivity extends SimpleBaseActivity {

    private final int TIP_FACE = 0;
    private final int TIP_TOUGUE = 1;
    @BindView(R.id.look_tip)
    TextView tip;
    @BindView(R.id.look_container)
    ImageView containerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_major_look;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case TIP_FACE:
                        tip.setText(getString(R.string.look_tip2));
                        containerView.setImageDrawable(getDrawable(R.mipmap.tougue));
                        break;
                    case TIP_TOUGUE:
                        tip.setText(getString(R.string.look_tip_next));
                        Toast.makeText(MajorLookActivity.this, getString(R.string.look_tip_next), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MajorLookActivity.this,MajorListenerActivity.class));
                        break;
                }
            }
        };
        AppApplication.getInstance().getExecutor()
                .setName("look任务-面部")
                .setDeliver(new AndroidDeliver()).execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = 0;
                handler.sendMessage(message);
            }
        });

        AppApplication.getInstance().getExecutor()
                .setName("look任务-舌部")
                .setDeliver(new AndroidDeliver())
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(6000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                });
    }

}
