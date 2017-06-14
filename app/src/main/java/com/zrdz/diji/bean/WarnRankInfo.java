package com.zrdz.diji.bean;

/**
 * Created by Administrator on 2017/6/8.
 * 警示排名bean类
 */

public class WarnRankInfo {
    public String companyName;
    public String cjg_id;
    public String year;
    public String quarter;
    public String total;
    public String rankNo;

    public WarnRankInfo(String companyName, String cjg_id, String year, String quarter, String total, String rankNo) {
        this.companyName = companyName;
        this.cjg_id = cjg_id;
        this.year = year;
        this.quarter = quarter;
        this.total = total;
        this.rankNo = rankNo;
    }

    @Override
    public String toString() {
        return "WarnRankInfo{" +
                "companyName='" + companyName + '\'' +
                ", cjg_id='" + cjg_id + '\'' +
                ", year='" + year + '\'' +
                ", quarter='" + quarter + '\'' +
                ", total='" + total + '\'' +
                ", rankNo='" + rankNo + '\'' +
                '}';
    }
}
