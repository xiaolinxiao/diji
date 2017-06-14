package com.zrdz.diji.bean;

/**
 * Created by Administrator on 2017/6/12.
 * 图片bean类
 */

public class Photo {
    public String filePath;
    public String photoTime;
    public String againUp;
    public String place;

    public Photo(String filePath, String againUp, String photoTime,String place) {
        this.filePath = filePath;
        this.againUp = againUp;
        this.photoTime = photoTime;
        this.place = place;
    }
}
