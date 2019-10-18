package com.example.firstapplication.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: gl
 * @CreateDate: 2019/10/17
 * @Description:问题的实体类
 */
public class QuestionEnity {

    //问题
    private String question;
    //答案列表
    private List<String> answerList;
    //患者的答案列表
    private List<String> resultList;
    //是否单选
    private boolean isRadio;

    public QuestionEnity(String question, List<String> answerList, boolean isRadio) {
        this.question = question;
        this.answerList = answerList;
        this.isRadio = isRadio;
        this.resultList = new ArrayList<String>();
    }
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<String> answerList) {
        this.answerList = answerList;
    }

    public boolean isRadio() {
        return isRadio;
    }

    public void setRadio(boolean radio) {
        isRadio = radio;
    }
    public void addResultList(String item){
        this.resultList.add(item);
    }

}
