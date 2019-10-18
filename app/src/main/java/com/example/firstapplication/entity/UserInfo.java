package com.example.firstapplication.entity;

import androidx.annotation.NonNull;

/**
 * @Author: gl
 * @CreateDate: 2019/10/18
 * @Description:
 */
public class UserInfo {
    private String id;
    private String name;
    private int age;
    private String type;
    private String doctorName;
    private boolean isLook = false;
    private boolean isListener = false;
    private boolean isRequest = false;
    private boolean isPulse = false;

    public UserInfo(String id, String name, int age, String type, String doctorName) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.type = type;
        this.doctorName = doctorName;
    }

    public boolean isLook() {
        return isLook;
    }

    public void setLook(boolean look) {
        isLook = look;
    }

    public boolean isListener() {
        return isListener;
    }

    public void setListener(boolean listener) {
        isListener = listener;
    }

    public boolean isRequest() {
        return isRequest;
    }

    public void setRequest(boolean request) {
        isRequest = request;
    }

    public boolean isPulse() {
        return isPulse;
    }

    public void setPulse(boolean pulse) {
        isPulse = pulse;
    }

    @NonNull
    @Override
    public String toString() {
        return "name:"+name+ "  ,age";
    }
}
