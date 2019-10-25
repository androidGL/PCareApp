package com.example.firstapplication.presenter;

import android.app.Activity;
import android.view.MotionEvent;

import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapplication.R;
import com.example.firstapplication.activity.QuestionActivity;
import com.example.firstapplication.adapter.QuestionSelectAdapter;
import com.example.firstapplication.adapter.QuestionSpeakAdapter;
import com.example.firstapplication.base.BasePresenter;
import com.example.firstapplication.base.IPresenter;
import com.example.firstapplication.contract.QuestionContract;
import com.example.firstapplication.entity.MsgEntity;
import com.example.firstapplication.entity.QuestionEnity;
import com.example.firstapplication.utils.SpeechToTextUtil;
import com.example.firstapplication.utils.TextToSpeechUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: gl
 * @CreateDate: 2019/10/25
 * @Description:
 */
public class QuestionPresenter extends BasePresenter<QuestionContract.View> implements QuestionContract.Presenter {

    private List<String> queationTitleList = new ArrayList<>();
    private List<MsgEntity> msgEntityList = new ArrayList<>();
    private SpeechToTextUtil speechToTextUtil;
    private  RecyclerView.Adapter selectAdapter;
    private Activity activity;
    public QuestionPresenter(QuestionContract.View view) {
        super(view);
        activity = (Activity) view;
        init();
    }

    @Override
    public void useClickType() {
        List<QuestionEnity> questionList = new ArrayList<QuestionEnity>();
        String[] list_answer =activity .getResources().getStringArray(R.array.list_answer);
        for (int i=0;i<queationTitleList.size();i++){
            List<String> list = new ArrayList<>();
            list.addAll(Arrays.asList(list_answer[i].split("，")));
            questionList.add(new QuestionEnity(queationTitleList.get(i),list,true));
        }
        setSpeakStr(queationTitleList.get(0));
        selectAdapter = new QuestionSelectAdapter(activity, questionList);
        getView().setAdapter(selectAdapter);
    }

    @Override
    public void useSpeakType() {
        int num = (int) Math.random()*(queationTitleList.size()-1);
        setSpeakStr(queationTitleList.get(num));
        msgEntityList.add(new MsgEntity(queationTitleList.get(num),2));
        selectAdapter = new QuestionSpeakAdapter(activity,msgEntityList);
        getView().setAdapter(selectAdapter);
        speechToTextUtil=new SpeechToTextUtil();
    }

    @Override
    public void startSpeak() {
        speechToTextUtil.startSpeak(activity, new SpeechToTextUtil.Callback() {
            @Override
            public void success(String result) {
                msgEntityList.add(new MsgEntity(result,1));
                int num = (int) Math.random()*(queationTitleList.size()-1);
                setSpeakStr(queationTitleList.get(num));
                msgEntityList.add(new MsgEntity(queationTitleList.get(num),2));
                selectAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void stopSpeak() {
        speechToTextUtil.stopSpeak();
    }


    @Override
    public void setSpeakStr(String s) {
        TextToSpeechUtil.textToSpeak(activity,s);
    }

    @Override
    public void destoty() {
        if(null != speechToTextUtil) speechToTextUtil.destotySpeechToSpeak();
        TextToSpeechUtil.destoty();
    }

    private  void init(){
        queationTitleList.addAll(Arrays.asList(activity.getResources().getStringArray(R.array.list_question)));
    }
}
