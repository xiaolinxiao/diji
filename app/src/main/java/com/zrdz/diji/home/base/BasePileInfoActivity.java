package com.zrdz.diji.home.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zrdz.diji.R;
import com.zrdz.diji.baseui.BaseBackTitleActivity;
import com.zrdz.diji.utils.LinkServiceUtils;
import com.zrdz.diji.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 基桩详情界面基类
 */
public abstract class BasePileInfoActivity extends BaseBackTitleActivity implements View.OnClickListener {

    private TextView text_pileno;
    private TextView text_pname;
    private TextView text_jcjg;
    private TextView text_detectionname;
    private TextView text_manphone;
    private TextView text_phead;
    private TextView text_headphone;
    private TextView text_piletype;
    private TextView text_testway;
    private TextView text_maxLoad;
    private TextView text_pileintensity;
    private TextView text_pilediameter;
    private TextView text_devicename;
    private TextView text_teststarttime;
    private TextView text_tallstarttime;
    private String managetype;//判断是哪个模块进来的 1:开始试验 2：中止试验&结束试验 3：正在试验
    public String testtype;//判断试验类型，1：中止试验 2：结束试验
    public TextView text_tall;
    public TextView text_lookpic;//查看图片
    public Button btn_fillpic;//补传图片
    public Button btn_test;//试验开始


    public String experiment_state;//试验状态
    public String pile_id;//桩号
    private String pid;//基桩pid
    private String startDate;//试验开始时间
    private TextView text_lookweight;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    String message = (String) msg.obj;
                    showToast(getString(R.string.getdatafail) + message);
                    break;
                case -1:
                    showToast(getString(R.string.getdatafail));
                    break;
                case 1:
                    String message1 = (String) msg.obj;
                    pasingJson(message1);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgument(R.layout.activity_pile_info_detail, getString(R.string.pileinfo));
    }

    private void initView() {
        text_pileno = (TextView) findViewById(R.id.pileinfo_pileno);
        text_pname = (TextView) findViewById(R.id.pileinfo_projectname);
        text_jcjg = (TextView) findViewById(R.id.pileinfo_jcjg);
        text_detectionname = (TextView) findViewById(R.id.pileinfo_detectionname);
        text_manphone = (TextView) findViewById(R.id.pileinfo_manphone);
        text_phead = (TextView) findViewById(R.id.pileinfo_projecthead);
        text_headphone = (TextView) findViewById(R.id.pileinfo_headphone);
        text_piletype = (TextView) findViewById(R.id.pileinfo_piletype);
        text_testway = (TextView) findViewById(R.id.pileinfo_testway);
        text_maxLoad = (TextView) findViewById(R.id.pileinfo_maxloadvalues);
        text_pileintensity = (TextView) findViewById(R.id.pileinfo_pileintensity);
        text_pilediameter = (TextView) findViewById(R.id.pileinfo_pilediameter);
        text_devicename = (TextView) findViewById(R.id.pileinfo_devicename);
        text_teststarttime = (TextView) findViewById(R.id.pileinfo_teststarttime);
        text_tallstarttime = (TextView) findViewById(R.id.pileinfo_tallstarttime);
        text_lookpic = (TextView) findViewById(R.id.pileinfo_text_lookpic);
        btn_fillpic = (Button) findViewById(R.id.pileinfo_btn_fillpic);
        btn_test = (Button) findViewById(R.id.pileinfo_btn_test);
        text_tall = (TextView) findViewById(R.id.pileinfo_text_tall30);
        text_lookweight = (TextView) findViewById(R.id.pileinfo_text_lookweight);
        text_lookpic.setOnClickListener(this);
        text_lookweight.setOnClickListener(this);
        btn_test.setOnClickListener(this);
        btn_fillpic.setOnClickListener(this);
    }


    /**
     * 获取数据
     */
    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LinkServiceUtils linkServiceUtils = LinkServiceUtils.getInstance();
                String pileInfo = linkServiceUtils.getJzPileUnbatchDetail(BasePileInfoActivity.this, pid);
                try {
                    JSONObject jsonObject = new JSONObject(pileInfo);
                    String result = jsonObject.getString("jzPileVo");
                    if (!"".equals(result) && null != result) {
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = result;
                        handler.sendMessage(msg);
                    } else {
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = "" + result;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = e.toString();
                    handler.sendMessage(msg);
                }

            }
        }).start();
    }

    /**
     * 对得到的数据解析并填充到对应的控件上
     *
     * @param message1
     */
    private void pasingJson(String message1) {
        try {
            LogUtils.e("==message==" + message1);
            JSONObject jsonObject = new JSONObject(message1);
            pile_id = jsonObject.getString("pile_Id");
            String projectName = jsonObject.getString("projectName");
            String jcjg = jsonObject.getString("inSpectInstituTionName");
            String manName = jsonObject.getString("inspectInstitutionUserNames");
            String manPhone = jsonObject.getString("inspectInstitutionUserPhone");
            String headname = jsonObject.getString("jc_user_names");
            String headPhone = jsonObject.getString("jc_phone");
            String pile_type = jsonObject.getString("pile_Type");
            String testway = jsonObject.getString("pile_Test_Methods");
            String max_load = jsonObject.getString("max_Load");
            String pile_intensity = jsonObject.getString("pile_Intensity");
            String pile_diameter = jsonObject.getString("pile_Diameter");
            String deviceName = jsonObject.getString("equipmentNames");
            String tallstarttime = jsonObject.getString("experiment_Datestr");
            String jwcount = jsonObject.getString("jwcount");
            experiment_state = jsonObject.getString("experiment_State");
            text_pileno.setText("" + pile_id);
            text_pname.setText("" + projectName);
            text_jcjg.setText("" + jcjg);
            text_detectionname.setText("" + manName);
            text_manphone.setText("" + manPhone);
            text_phead.setText("" + headname);
            text_headphone.setText("" + headPhone);
            text_piletype.setText("" + pile_type);
            text_testway.setText("" + testway);
            text_maxLoad.setText("" + max_load + "kN");
            text_pileintensity.setText("" + pile_intensity + "MPa");
            text_pilediameter.setText("" + pile_diameter + "mm");
            text_devicename.setText("" + deviceName);
            text_teststarttime.setText("" + startDate);
            text_tallstarttime.setText("" + tallstarttime);
            LogUtils.e("managetype==" + managetype);
            if ("1".equals(managetype)) {
                if ("0".equals(experiment_state) || "1".equals(experiment_state)) {
                    btn_fillpic.setVisibility(View.VISIBLE);
                    text_lookpic.setVisibility(View.VISIBLE);
                    btn_test.setText(R.string.restarttest);
                    text_tall.setVisibility(View.VISIBLE);
                }
            } else if ("2".equals(managetype)) {
                testtype = getIntent().getStringExtra("testtype");
                if ("1".equals(testtype))
                    btn_test.setText(R.string.topausetest);
                else
                    btn_test.setText(R.string.tostoptest);
                text_lookpic.setVisibility(View.VISIBLE);
            }
            if ("Y".equals(jwcount)) {
                text_lookweight.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            showToast(getString(R.string.pasedatafail) + e.toString());
        }
    }

    /**
     * 对pid和managetype赋值
     *
     * @param pid
     * @param managetype
     */
    private void setPidAndManageType(String pid, String managetype, String startDate) {
        this.pid = pid;
        this.managetype = managetype;
        this.startDate = startDate;
    }

    /**
     * 初始化数据 子类必须调用
     *
     * @param pid
     * @param managetype
     */
    public void initBaseArgument(String pid, String managetype, String startDate) {
        setPidAndManageType(pid, managetype, startDate);
        initView();
        getData();
    }
}
