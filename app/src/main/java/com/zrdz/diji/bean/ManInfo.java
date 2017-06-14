package com.zrdz.diji.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/16.
 * 人员警示
 */

public class ManInfo implements Serializable {
    public String inSpectInstituTionName;
    public String personName;
    public String alertInfo;
    public String inspectinstitutionuserpid;

    public String pile_Id1;
    public String projectName1;
    public String startDate1;
    public String endDate1;

    public String pile_Id2;
    public String projectName2;
    public String startDate2;
    public String endDate2;

    public ManInfo(String inSpectInstituTionName, String personName, String alertInfo, String inspectinstitutionuserpid, String pile_Id1, String projectName1, String startDate1, String endDate1, String pile_Id2, String projectName2, String startDate2, String endDate2) {
        this.inSpectInstituTionName = inSpectInstituTionName;
        this.personName = personName;
        this.alertInfo = alertInfo;
        this.inspectinstitutionuserpid = inspectinstitutionuserpid;
        this.pile_Id1 = pile_Id1;
        this.projectName1 = projectName1;
        this.startDate1 = startDate1;
        this.endDate1 = endDate1;
        this.pile_Id2 = pile_Id2;
        this.projectName2 = projectName2;
        this.startDate2 = startDate2;
        this.endDate2 = endDate2;
    }

    public ManInfo(String alertInfo, String personName, String inSpectInstituTionName) {
        this.alertInfo = alertInfo;
        this.personName = personName;
        this.inSpectInstituTionName = inSpectInstituTionName;
    }

    @Override
    public String toString() {
        return "ManInfo{" +
                "inSpectInstituTionName='" + inSpectInstituTionName + '\'' +
                ", personName='" + personName + '\'' +
                ", alertInfo='" + alertInfo + '\'' +
                ", inspectinstitutionuserpid='" + inspectinstitutionuserpid + '\'' +
                ", pile_Id1='" + pile_Id1 + '\'' +
                ", projectName1='" + projectName1 + '\'' +
                ", startDate1='" + startDate1 + '\'' +
                ", endDate1='" + endDate1 + '\'' +
                ", pile_Id2='" + pile_Id2 + '\'' +
                ", projectName2='" + projectName2 + '\'' +
                ", startDate2='" + startDate2 + '\'' +
                ", endDate2='" + endDate2 + '\'' +
                '}';
    }
}
