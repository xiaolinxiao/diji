package com.zrdz.diji.bean;

import java.io.Serializable;

/**
 * Created by ASUS on 2017/4/17.
 * 用户信息
 */

public class UserInfo implements Serializable {
    @Override
    public String toString() {
        return "UserInfo{" +
                "loginName='" + loginName + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", pid='" + pid + '\'' +
                ", roleType='" + roleType + '\'' +
                ", userType='" + userType + '\'' +
                ", id='" + id + '\'' +
                ", mobile='" + id + '\'' +
                '}';
    }

    /*loginName:登录名
           code: 查询机构
           name: 机构名称
           pid:监管pid
           roleType: 用户角色
           userType:用户类型
           id:用户id
           mobile :电话
           */
    public String loginName;
    public String code;
    public String name;
    public String pid;
    public String roleType;
    public String userType;
    public String id;
    public String mobile;


}
