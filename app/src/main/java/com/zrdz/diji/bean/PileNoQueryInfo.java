package com.zrdz.diji.bean;

/**
 * Created by Administrator on 2017/5/17.
 * 桩号查询
 */

public class PileNoQueryInfo {
    /*---------查询----start---------------*/
    public String pile_Id = "";
    public String pile_pid = "";
    public String projectName = "";
    public String inSpectInstituTionName = "";
    public String districtIdName = "";
    public String startDate = "";
    public String startTime = "";// 开始告知时间
    /*---------查询----end----------------*/


    public String jiancefangfa = "";
    public String zhuanghao = "";
    public String yujizuidahezaizhi = "";
    public String zhuangdeqiangdu = "";
    public String zhuangdezhijin = "";
    public String yiqibianhao = "";
    public String renyuanxingming = "";
    public String kaishishiyanshijian = "";//
    public String zhiliangjianduzhanid = "";
    public String gongchengdizhi = "";
    public String jiancerenyuanshoujihao = "";
    public String shebeimingcheng = "";
    public String experiment_State = "";
    public String data_Full = "";
    public String endTime = "";
    public String equipmentNames = "";
    public String type = "";
    public String pile_Exception = "";
    public String jcjgId = "";
    public String secondLoad = "";
    public String reasonif = "";
    public String alertInfo = "";
    public String jsReason = "";
    public String yichangleixing = "";
    public String yichangshuoming = "";
    public String plan_Start_Date = "";

    public String zhongzhishijian = "";
    public String zhongzhijishu = "";
    public String zhongzhihezaizhi = "";
    public String zhongzhichenjiangliang = "";
    public String zhongzhiyuanyin = "";

    public String compactId = "";// 合同ID

    public String plan_Start_Datestr = "";

    public String isKsLoad;

    public String jieshugaozhishijian;
    public String fuzeren;
    public String fuzerenhaoma;
    public String messageId = "";// 推送消息ID;

    public String pile_intensity;// 强度
    public String shiyan_finish_date;// 试验结束时间
    public String gaozhi_finish_date;// 告知结束时间
    public String pile_test_method;// 检测方法
    public String jwcount = "";// 是否存在压重堆载表格数据
    public String finish_state;// 是否结束状态

    public PileNoQueryInfo(String pile_Id, String pile_pid, String projectName, String inSpectInstituTionName, String districtIdName, String startDate, String startTime) {
        this.pile_Id = pile_Id;
        this.pile_pid = pile_pid;
        this.projectName = projectName;
        this.inSpectInstituTionName = inSpectInstituTionName;
        this.districtIdName = districtIdName;
        this.startDate = startDate;
        this.startTime = startTime;
    }
}
