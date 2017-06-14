package com.zrdz.diji.bean;

/**
 * Created by Administrator on 2017/5/18.
 * 城市区信息
 */

public class DistricOfCityInfo {
    public String inspectInstitutionName;
    public String jcjgId;

    public DistricOfCityInfo(String inspectInstitutionName, String jcjgId) {
        this.inspectInstitutionName = inspectInstitutionName;
        this.jcjgId = jcjgId;
    }

    @Override
    public String toString() {
        return "DistricOfCityInfo{" +
                "inspectInstitutionName='" + inspectInstitutionName + '\'' +
                ", jcjgId='" + jcjgId + '\'' +
                '}';
    }
}
