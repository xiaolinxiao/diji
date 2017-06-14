package com.zrdz.diji.bean;

/**
 * Created by Administrator on 2017/6/8.
 * 信用排名bean类
 */

public class CreditRankInfo {
    public String name;
    public String code;
    public String rank;
    public String score;

    public CreditRankInfo(String name, String code, String rank, String score) {
        this.name = name;
        this.code = code;
        this.rank = rank;
        this.score = score;
    }
}
