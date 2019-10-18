package com.example.firstapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapplication.R;
import com.example.firstapplication.adapter.QuestionSpeakAdapter;
import com.example.firstapplication.entity.MsgEntity;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends Activity {
//    @BindView(R.id.chat_recycleview)
    RecyclerView recyclerView;
    private String[] questions = getResources().getStringArray(R.array.list_question);
    private  List<MsgEntity> chatList = new ArrayList<>();
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_chat;
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView = findViewById(R.id.chat_recycleview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllMessage();
    }

    public void getAllMessage(){
        QuestionSpeakAdapter adapter = new QuestionSpeakAdapter(this,chatList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public void sendMsg(View view) {
        int id = (int) (Math.random()*(questions.length-1));
        chatList.add(new MsgEntity(questions[id],1));
    }
}
