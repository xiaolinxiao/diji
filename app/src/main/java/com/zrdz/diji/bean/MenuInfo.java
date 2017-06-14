package com.zrdz.diji.bean;

/**
 * Created by Administrator on 2016/12/7.
 * homepage界面每个item，
 *
 */
public class MenuInfo {
    private String id;//菜单id
    private String name;//菜单名字
    private int icon_id;//菜单图标资源id

    public MenuInfo(String id, String name, int icon_id) {
        this.id = id;
        this.name = name;
        this.icon_id = icon_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIcon_id() {
        return icon_id;
    }

    public void setIcon_id(int icon_id) {
        this.icon_id = icon_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
