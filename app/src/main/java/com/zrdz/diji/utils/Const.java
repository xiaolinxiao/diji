package com.zrdz.diji.utils;

/**
 * Created by ASUS on 2017/4/17.
 * 存放常量值
 */

public class Const {

    //外网测试
    public static final String MAIN_URL_222 = "http://222.79.8.25:8083";
    public static final String MAIN_URL_192 = "http://192.168.123.196:8088";
    /*--------------信用排名--------------------------*/
    public static final String RANK_CREDIT = "http://120.35.29.43:98/zjxypj/WebServices/XydjService.ashx";

    //222服务器 试验管理
    public static final String SYGL_UPLOAD_URL = "http://222.79.8.25:8083/sospDev/com/zr/inspectuser/inspectuserAction!uploadImageNew.do";
    public static final String SYGL_PHOTO_URL = "http://222.79.8.25:8083/sospDev/";
    public static final String SYGL_URL = "http://222.79.8.25:8083/sospDev/service/androidWebService";
    public static final String SYGL_JZ_URL = "http://222.79.8.25:8083/sospDev/service/jzAndroidWebService";
    public static final String SYGL_HOST_URL = "http://http://117.27.135.8:8083/sospweb/com/zr/pileCRating/quarterPile!statistic_project_pile.do?key=zr88230232&curPage=1&pageSize=100";


    //登录外网测试url
    //public static final String LOGIN_NEW_222 = "http://oa.zrdzkj.com:9015/djgcjc/web/app";
    //修改密码
    public static final String PUBLIC_URL_222 = "http://222.79.8.25:8083/sospDev/com/zr/pubmobile";
    public static final String MAIN_URL_PAY_222 = "http://oa.zrdzkj.com:9015/fwpt/pay/app";
    public static final String MAIN_URL_PAY_WEIXIN_222 = "http://oa.zrdzkj.com:9015/fwpt/pay";


    /*------------------------模块-----------------------*/
    public static final String LOGIN_MODULE = "/djgcjc/web/app/user";//登录模块
    public static final String UPDATEPWD_MODULE = "/djgcjc/web/app/modifyPwd";
    public static final String PILEDOTNOINFO_MODULE = "/com/zr/mobile_jz/jzbatch/jz_batchAction!queryJzPileExperimentation.do";//桩号查询
    public static final String TESTFIRST_MODULE = "/sospDev/com/zr/mobile_jz/exper_frist/exp_fristAction!queryExperimentBackup.do";
    public static final String NOBACKUPPILE__MODULE = "/com/zr/mobile_jz/nobackup_exper/nobackup_experAction!queryNoBackupExperiment.do";//未备案
    public static final String DEVICEWARN_MODULE = "/sospDev/com/zr/mobile_jz/equipmentAlert/equipmentAlertAction!queryEquipmentAlert.do";
    public static final String WARNRANK_MOUDLE = "/sospDev/com/zr/mobile_jz/statistics/jzmStatisticsAction!findAllStartNotifyRank.do";
    public static final String PHOTO_MOUDLE = "/sospDev/";
    public static final String UPLOADPHOTO_MODULE="/sospDev/com/zr/inspectuser/inspectuserAction!uploadImageNew.do";//图片补传

    public static final String MAIN_URL = MAIN_URL_222;
    /*-----------key---------------*/
    public static final String USER_NAME = "user_name";
    public static final String USER_PWD = "user_pwd";
    public static final String USER_PID = "user_pid";
    public static final String USER_ID = "user_id";
    public static final String USER_CODE = "user_code";
    public static final String USER_ROLETYPE = "user_roleType";
    public static final String USER_USESRTYPE = "user_userType";
    public static final String USER_ISREMENBER = "user_remenb";//记住密码
    public static final String USER_LOGINAUTO = "user_loginauto";//自动登录标志

    /*----------常量----------------*/
    public static final String NAMESPACE = "http://webservice.zr.com/";
    public static final String[] CITYARRAY = new String[]{"福州市", "莆田市", "泉州市", "厦门市", "漳州市", "龙岩市", "三明市", "南平市", "宁德市", "省总站"};
    public static final int FLAG_TESTFIRST = 0;//先实验后备案标志
    public static final int FLAG_NORECORD = 1;//未备案查询
    public static final int CAMERA_REQUEST = 0;//摄像头请求码
}
