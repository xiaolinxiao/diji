package com.zrdz.diji.utils;

import android.content.Context;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by Administrator on 2017/5/18.
 * 连接webservice
 */

public class LinkServiceUtils {
    private static LinkServiceUtils linkServiceUtils;

    /**
     * 初始化
     *
     * @return
     */
    public static LinkServiceUtils getInstance() {
        if (null == linkServiceUtils) {
            linkServiceUtils = new LinkServiceUtils();
        }
        return linkServiceUtils;
    }

    private String METHODNAME = "";

    /**
     * 查询质量监督站监督员信息
     *
     * @param jgjcid
     * @return
     */
    public String linkquerySupervisorName(Context context, String jgjcid) {
        String phoneid = CommonUtils.getPhoneID(context);
        METHODNAME = "getJdzTree";
        String show = "";
        SoapObject request = new SoapObject(Const.NAMESPACE, METHODNAME);
        request.addProperty("in0", phoneid);
        request.addProperty("in1", jgjcid);
        show = linkresultJz(request);
        return show;
    }

    /**
     * 查询基桩信息
     *
     * @param context
     * @param jdjgId
     * @param mRole
     * @param mProjectName
     * @param districIDs
     * @param manageType
     * @param startTime
     * @return
     */
    public String getPileUnbatchInfo(Context context, String jdjgId, String mRole, String mProjectName,
                                     String districIDs, String manageType, String startTime) {
        String phoneid = CommonUtils.getPhoneID(context);
        METHODNAME = "getJzPileUnbatchInfo";
        String show = "";
        SoapObject request = new SoapObject(Const.NAMESPACE, METHODNAME);
        request.addProperty("in0", phoneid);
        request.addProperty("in1", jdjgId);
        request.addProperty("in2", mRole);
        request.addProperty("in3", "");
        request.addProperty("in4", mProjectName);
        request.addProperty("in5", districIDs);
        request.addProperty("in6", "1");
        request.addProperty("in7", manageType);
        request.addProperty("in8", startTime);
        show = linkresultJz(request);
        return show;
    }

    /**
     * 查询基桩信息根据登录pid
     *
     * @return
     */
    public String getJzPileUnbatchInfoNew(Context context, String jdjgId, String mRole, String mProjectName,
                                          String districIDs, String manageType, String startTime, String userId) {
        String phoneid = CommonUtils.getPhoneID(context);
        METHODNAME = "getJzPileUnbatchInfoNew";
        String show = null;
        SoapObject request = new SoapObject(Const.NAMESPACE, METHODNAME);
        request.addProperty("in0", phoneid);
        request.addProperty("in1", jdjgId);
        request.addProperty("in2", mRole);
        request.addProperty("in3", "");
        request.addProperty("in4", mProjectName);
        request.addProperty("in5", districIDs);
        request.addProperty("in6", "1");
        request.addProperty("in7", manageType);
        request.addProperty("in8", startTime);
        request.addProperty("in9", userId);
        show = linkresultJz(request);
        return show;
    }

    /**
     * 查询质量监督站
     *
     * @param id 市id
     * @return
     */
    public String linkqueryDistrictName(String id) {
        METHODNAME = "queryDistrictName";
        String show = "";
        SoapObject request = new SoapObject(Const.NAMESPACE, METHODNAME);
        request.addProperty("in0", id);
        show = linkresultJz(request);
        return show;
    }

    /**
     * 获取基桩详情
     *
     * @param context 上下文
     * @param pid     pid
     * @return
     */

    public String getJzPileUnbatchDetail(Context context, String pid) {
        String phoneid = CommonUtils.getPhoneID(context);
        METHODNAME = "getJzPileUnbatchDetail";
        String show = "";
        SoapObject request = new SoapObject(Const.NAMESPACE, METHODNAME);
        request.addProperty("in0", phoneid);
        request.addProperty("in1", pid);
        show = linkresultJz(request);
        return show;
    }

    /**
     * 检验用户权限
     *
     * @param phoneNum
     * @param pid
     * @return
     */
    public String checkAccount(String phoneNum, String pid) {
        METHODNAME = "checkAccountByJzpile_pid";
        String show = null;
        SoapObject request = new SoapObject(Const.NAMESPACE, METHODNAME);
        request.addProperty("in0", phoneNum);
        request.addProperty("in1", pid);
        show = linkresultJz(request);
        return show;
    }

    /**
     * 根据桩号查询相片路径(以及getJzPileInfobyLoadDetail接口也用这个)
     *
     * @param context
     * @param pid
     * @return
     */
    public String linkqueryPhotoPath(Context context, String pid) {
        String phoneid = CommonUtils.getPhoneID(context);
        METHODNAME = "queryJzPhotoPathPlace";
        String show = null;
        SoapObject request = new SoapObject(Const.NAMESPACE, METHODNAME);
        request.addProperty("in0", phoneid);
        request.addProperty("in1", pid);
        show = linkresultJz(request);
        return show;
    }

    /**
     * 基桩开始实验时拍照前确认图片是否上传过
     * <p/>
     * 同上面相比多了一个place,为位置图片(1.机顶盒静载仪连接情况2.配重正面图3.配重侧面图4.配重上方图5.基桩静载检测相关记录表)
     * 传 1 2 3 4 5
     *
     * @return
     */
    public String addPhoto(String phoneNumber, String pile_Pid, String version, String place) {
        METHODNAME = "addJzPhotoNew";
        String show = null;
        SoapObject request = new SoapObject(Const.NAMESPACE, METHODNAME);
        request.addProperty("in0", phoneNumber);
        request.addProperty("in1", pile_Pid);
        request.addProperty("in2", version);
        request.addProperty("in3", "1");
        request.addProperty("in4", place);
        show = linkresultJz(request);
        return show;
    }

    /**
     * 基桩重新开始实验
     *
     * @param context
     * @param jgjcId
     * @param startExp
     * @return
     */
    public String linkstartAndConfirmPile(Context context, String jgjcId, String startExp) {
        String phoneid = CommonUtils.getPhoneID(context);
        METHODNAME = "startAndConfirmJzPile2";
        String show;
        SoapObject request = new SoapObject(Const.NAMESPACE, METHODNAME);
        request.addProperty("in0", phoneid);
        request.addProperty("in1", jgjcId);
        request.addProperty("in2", startExp);
        show = linkresultJz(request);
        return show;
    }

    /**
     * 获取压重堆载记录
     */
    public String queryJZ_WRecord(String pid) {
        METHODNAME = "queryJZ_WRecord";
        String show = null;
        SoapObject request = new SoapObject(Const.NAMESPACE, METHODNAME);
        request.addProperty("in0", pid);
        show = linkresultJz(request);
        return show;
    }

    /**
     * 连接webservice 请求数据
     *
     * @param request
     * @return
     */
    private String linkresultJz(SoapObject request) {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        envelope.bodyOut = request;
        envelope.setOutputSoapObject(request);
        LogUtils.e("LinkWebSerVice，请求参数======" + request);
        String show = "";
        try {
            HttpTransportSE transport = new HttpTransportSE(Const.SYGL_JZ_URL, 5000);
            String str = Const.NAMESPACE + METHODNAME;
            transport.call(str, envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            show = result.getProperty(0).toString();
            LogUtils.e("LinkWebSerVice,后台返回的数据====" + show);
        } catch (Exception e) {
            e.printStackTrace();
            show = null;
        }
        return show;
    }
}
