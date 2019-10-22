package com.example.firstapplication.model;

import com.example.firstapplication.contract.MajorPulseContract;

/**
 * @Author: gl
 * @CreateDate: 2019/10/22
 * @Description:
 */
public class MajorPulseModel implements MajorPulseContract.Model {
    @Override
    public String getPulseInfo() {
        return " 检查项目：脉象 \n脉象状态：细弱 \n脉象类别：虚脉";
    }
}
