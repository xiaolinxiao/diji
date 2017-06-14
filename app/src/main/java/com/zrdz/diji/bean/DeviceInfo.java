package com.zrdz.diji.bean;

/**
 * Created by Administrator on 2017/5/17.
 */

public class DeviceInfo {
    public String inSpectInstituTionName;
    public String equipmentName;//设备名称
    public String equipmentid;//设备编号
    public String equipmentpid;

    public String pile_Id1;
    public String projectName1;
    public String startDate1;
    public String endDate1;

    public String pile_Id2;
    public String projectName2;
    public String startDate2;
    public String endDate2;

    public String alertInfo;

    public DeviceInfo(String inSpectInstituTionName, String equipmentName, String equipmentid, String equipmentpid, String pile_Id1, String projectName1, String startDate1, String endDate1, String pile_Id2, String projectName2, String startDate2, String endDate2, String alertInfo) {
        this.inSpectInstituTionName = inSpectInstituTionName;
        this.equipmentName = equipmentName;
        this.equipmentid = equipmentid;
        this.equipmentpid = equipmentpid;
        this.pile_Id1 = pile_Id1;
        this.projectName1 = projectName1;
        this.startDate1 = startDate1;
        this.endDate1 = endDate1;
        this.pile_Id2 = pile_Id2;
        this.projectName2 = projectName2;
        this.startDate2 = startDate2;
        this.endDate2 = endDate2;
        this.alertInfo = alertInfo;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "inSpectInstituTionName='" + inSpectInstituTionName + '\'' +
                ", equipmentName='" + equipmentName + '\'' +
                ", equipmentid='" + equipmentid + '\'' +
                ", equipmentpid='" + equipmentpid + '\'' +
                ", pile_Id1='" + pile_Id1 + '\'' +
                ", projectName1='" + projectName1 + '\'' +
                ", startDate1='" + startDate1 + '\'' +
                ", endDate1='" + endDate1 + '\'' +
                ", pile_Id2='" + pile_Id2 + '\'' +
                ", projectName2='" + projectName2 + '\'' +
                ", startDate2='" + startDate2 + '\'' +
                ", endDate2='" + endDate2 + '\'' +
                ", alertInfo='" + alertInfo + '\'' +
                '}';
    }

    public DeviceInfo(String equipmentid, String equipmentName, String alertInfo) {
        this.equipmentid = equipmentid;
        this.equipmentName = equipmentName;
        this.alertInfo = alertInfo;
    }
}
