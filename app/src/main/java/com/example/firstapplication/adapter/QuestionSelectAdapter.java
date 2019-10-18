package com.example.firstapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapplication.R;
import com.example.firstapplication.entity.QuestionEnity;

import java.util.List;

/**
 * @Author: gl
 * @CreateDate: 2019/10/17
 * @Description:
 */
public class QuestionSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<QuestionEnity> questionList;

    public QuestionSelectAdapter(Context context, List<QuestionEnity> questionList) {
        this.context = context;
        this.questionList = questionList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_question_select,parent,false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof QuestionViewHolder) {
            QuestionEnity questionEnity = questionList.get(position);
            List<String> answerList = questionEnity.getAnswerList();
            ((QuestionViewHolder) holder).question.setText(questionEnity.getQuestion());
            for (String item:answerList){
                ((QuestionViewHolder) holder).answerList.addView(new QueationRadioButton(context,item));
            }
        }

    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }
    private class QuestionViewHolder extends RecyclerView.ViewHolder{

        private TextView question;
        private RadioGroup answerList;
        public QuestionViewHolder(View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.question);
            answerList = itemView.findViewById(R.id.answer_group);
        }
    }
    private class QueationRadioButton extends AppCompatRadioButton {

        public QueationRadioButton(Context context,String name) {
            super(context);
            setText(name);
            setTextColor(getResources().getColor(R.color.text_color));
        }
    }
}
