package com.zrdz.diji.bean;

import java.io.Serializable;

/**
 * --先试验在备案---
 * Created by Administrator on 2017/5/16.
 */

public class TestFirstInfo implements Serializable {

    public String inSpectInstituTionName;
    public String pile_Id;
    public String startDate;
    public String pile_Pid;

    public TestFirstInfo(String inSpectInstituTionName, String pile_Id, String startDate, String pile_Pid) {
        this.inSpectInstituTionName = inSpectInstituTionName;
        this.pile_Id = pile_Id;
        this.startDate = startDate;
        this.pile_Pid = pile_Pid;
    }

    @Override
    public String toString() {
        return "TestFirstInfo{" +
                "inSpectInstituTionName='" + inSpectInstituTionName + '\'' +
                ", pile_Id='" + pile_Id + '\'' +
                ", startDate='" + startDate + '\'' +
                ", pile_Pid='" + pile_Pid + '\'' +
                '}';
    }
}
