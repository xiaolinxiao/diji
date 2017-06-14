package com.zrdz.diji.bean;

/**
 * Created by ASUS on 2017/4/17.
 * 在listview里的item对应的简单视图 textview
 */

public class NorResponseInfo {
    /*--先试验在备案---*/
    public String inSpectInstituTionName;
    public String pile_Id;
    public String startDate;
    public String pile_Pid;
    /*---基桩报告详情  -  试验信息-------*/
    public String pile_Type;
    public String pile_Test_Methods;
    public String equipmentNames;
    public String inspectInstitutionUserNames;
    public String jzresult;
    /*---人员警示信息---------*/
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
    /*---人员详情-----------*/
    public String inspectInstitutionUserName;
    public String carNo;
    public String phone;
    public String sex;
    public String enduction;
    public String state;
    public String entryDate;
    /*--桩点（号）信息查询--*/
    public String max_Load;
    public String experimentInformation;
    public String jcjgId;

    /**
     * 先实验后备案
     *
     * @param inSpectInstituTionName
     * @param pile_Id
     * @param startDate
     * @param pile_Pid
     */
    public NorResponseInfo(String inSpectInstituTionName, String pile_Id, String startDate, String pile_Pid, String jcjgId, String experimentInformation) {
        this.inSpectInstituTionName = inSpectInstituTionName;
        this.pile_Id = pile_Id;
        this.startDate = startDate;
        this.pile_Pid = pile_Pid;
        this.jcjgId = jcjgId;
        this.experimentInformation = experimentInformation;
    }

}
