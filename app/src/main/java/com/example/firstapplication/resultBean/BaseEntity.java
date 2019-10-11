package com.example.firstapplication.resultBean;

/**
 * @author gelan
 * @date 2019/10/9
 * Description:处理结果的基类
 */

public class BaseEntity {
    private int code;
    private boolean success;
    private String error;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
