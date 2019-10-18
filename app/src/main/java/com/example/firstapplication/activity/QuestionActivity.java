package com.example.firstapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapplication.R;
import com.example.firstapplication.adapter.QuestionSelectAdapter;
import com.example.firstapplication.adapter.QuestionSpeakAdapter;
import com.example.firstapplication.base.SimpleBaseActivity;
import com.example.firstapplication.entity.MsgEntity;
import com.example.firstapplication.entity.QuestionEnity;
import com.example.firstapplication.utils.SpeechToTextUtil;
import com.example.firstapplication.utils.TextToSpeechUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @Author: gl
 * @CreateDate: 2019/10/17
 * @Description:
 */
public class QuestionActivity extends SimpleBaseActivity {
    @BindView(R.id.question_list)
    RecyclerView QuestionListView;

    @BindView(R.id.request_bottom)
    TextView bottomSpeak;

    @BindView(R.id.request_finish)
    TextView questionFinish;
    private  RecyclerView.Adapter selectAdapter;
    private List<String> queationTitleList = new ArrayList<>();
    private List<MsgEntity> msgEntityList = new ArrayList<>();
    private TextToSpeechUtil textToSpeechUtil = new TextToSpeechUtil();
    private SpeechToTextUtil speechToTextUtil;

    @Override
    public int getLayoutId() {
        return R.layout.activity_request_select;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取问题列表
        queationTitleList.addAll(Arrays.asList(getResources().getStringArray(R.array.list_question)));
        //如果是UI互动形式
        if ("select".equals(getIntent().getExtras().getString("type"))) {
            bottomSpeak.setVisibility(View.GONE);
            List<QuestionEnity> questionList = new ArrayList<QuestionEnity>();
            String[] list_answer = getResources().getStringArray(R.array.list_answer);
            for (int i=0;i<queationTitleList.size();i++){
                List<String> list = new ArrayList<>();
                list.addAll(Arrays.asList(list_answer[i].split("，")));
                questionList.add(new QuestionEnity(queationTitleList.get(i),list,true));
            }
            selectAdapter = new QuestionSelectAdapter(this, questionList);
            questionFinish.setVisibility(View.VISIBLE);
        }else {//否则是对话形式
            int num = (int) Math.random()*(queationTitleList.size()-1);
            textToSpeechUtil.textToSpeak(this,queationTitleList.get(num));
            msgEntityList.add(new MsgEntity(queationTitleList.get(num),2));
            selectAdapter = new QuestionSpeakAdapter(this,msgEntityList);

            speechToTextUtil=new SpeechToTextUtil();
            bottomSpeak.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) { // 按下
                        speechToTextUtil.startSpeak(QuestionActivity.this, new SpeechToTextUtil.Callback() {
                            @Override
                            public void success(String result) {
                                msgEntityList.add(new MsgEntity(result,1));
                                int num = (int) Math.random()*(queationTitleList.size()-1);
                                textToSpeechUtil.textToSpeak(QuestionActivity.this,queationTitleList.get(num));
                                msgEntityList.add(new MsgEntity(queationTitleList.get(num),2));
                                selectAdapter.notifyDataSetChanged();
                            }
                        });
                    } else if (event.getAction() == MotionEvent.ACTION_UP) { // 松开
                        speechToTextUtil.stopSpeak();
                    }
                    return false;
                }
            });


        }
        QuestionListView.setLayoutManager(new LinearLayoutManager(this));
        QuestionListView.setAdapter(selectAdapter);
        selectAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if ("select".equals(getIntent().getExtras().getString("type")))
            return;
        if(null != speechToTextUtil) speechToTextUtil.destotySpeechToSpeak();
        if(null != textToSpeechUtil) textToSpeechUtil.destoty();
        textToSpeechUtil = null;
    }

    public void toNextTask(View view) {
        startActivity(new Intent(this,MajorPulseActivity.class));
    }
}
