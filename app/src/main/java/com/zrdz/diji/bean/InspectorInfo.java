package com.zrdz.diji.bean;

/**
 * Created by Administrator on 2017/5/18.
 * 监督员
 */

public class InspectorInfo {
    public String jcjgName;
    public String userName;
    public String userPhone;


    public InspectorInfo(String jcjgName, String userName, String userPhone) {
        this.jcjgName = jcjgName;
        this.userName = userName;
        this.userPhone = userPhone;
    }

    @Override
    public String toString() {
        return "InspectorInfo{" +
                "jcjgName='" + jcjgName + '\'' +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                '}';
    }
}
