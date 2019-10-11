package com.example.firstapplication.resultBean;

/**
 * @author gelan
 * @date 2019/10/9
 * Description:
 */

public class UserInfo extends BaseEntity {
    private String name;
    private int age;
    private String phoneNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

}
