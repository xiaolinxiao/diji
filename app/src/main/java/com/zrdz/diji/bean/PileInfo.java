package com.zrdz.diji.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/16.
 * 桩点信息
 */

public class PileInfo implements Serializable {
    public String experimentInformation;
    public String inSpectInstituTionName;
    public String jcjgId;
    public String max_Load;
    public String pile_Id;
    public String pile_Pid;
    public String startDate;

    public PileInfo(String experimentInformation, String inSpectInstituTionName, String jcjgId, String max_Load, String pile_Id, String pile_Pid, String startDate) {
        this.experimentInformation = experimentInformation;
        this.inSpectInstituTionName = inSpectInstituTionName;
        this.jcjgId = jcjgId;
        this.max_Load = max_Load;
        this.pile_Id = pile_Id;
        this.pile_Pid = pile_Pid;
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "PileInfo{" +
                "experimentInformation='" + experimentInformation + '\'' +
                ", inSpectInstituTionName='" + inSpectInstituTionName + '\'' +
                ", jcjgId='" + jcjgId + '\'' +
                ", max_Load='" + max_Load + '\'' +
                ", pile_Id='" + pile_Id + '\'' +
                ", pile_Pid='" + pile_Pid + '\'' +
                ", startDate='" + startDate + '\'' +
                '}';
    }
}
