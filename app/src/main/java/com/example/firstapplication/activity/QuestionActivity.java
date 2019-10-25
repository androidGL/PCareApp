package com.example.firstapplication.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapplication.R;
import com.example.firstapplication.base.SimpleBaseActivity;
import com.example.firstapplication.contract.QuestionContract;
import com.example.firstapplication.presenter.QuestionPresenter;
import butterknife.BindView;
import butterknife.OnTouch;

/**
 * @Author: gl
 * @CreateDate: 2019/10/17
 * @Description:
 */
public class QuestionActivity extends SimpleBaseActivity<QuestionPresenter> implements QuestionContract.View {
    @BindView(R.id.question_list)
    RecyclerView QuestionListView;

    @BindView(R.id.request_bottom)
    TextView bottomSpeak;

    @BindView(R.id.request_finish)
    TextView questionFinish;


    @Override
    public int getLayoutId() {
        return R.layout.activity_request_select;
    }

    @Override
    protected QuestionPresenter bindPresenter() {
        presenter = new QuestionPresenter((QuestionContract.View) getSelfActivity());
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //如果是UI互动形式
        if ("select".equals(getIntent().getExtras().getString("type"))) {
            bottomSpeak.setVisibility(View.GONE);
            questionFinish.setVisibility(View.VISIBLE);
            presenter.useClickType();
        }else {//否则是对话形式
            presenter.useSpeakType();
            bottomSpeak.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        presenter.startSpeak();
                    }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                        presenter.stopSpeak();
                    }
                    return true;
                }
            });
        }

    }
//    @OnTouch(R.id.request_bottom)
//    public boolean onTouch(View v, MotionEvent event) {
//        presenter.speakListener(event);
//        return false;
//    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    public void toNextTask(View view) {
        globalUserInfo.setRequest(true);
        toNextPage();
        finish();
    }

    @Override
    public void showToast(String toast) {

    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        QuestionListView.setLayoutManager(new LinearLayoutManager(this));
        QuestionListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void notifyAdapter() {

    }

    @Override
    protected void onDestroy() {
        presenter.destoty();
        super.onDestroy();
    }
}
