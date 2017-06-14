package com.zrdz.diji.home.testmanage.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.zrdz.diji.MyApplication;
import com.zrdz.diji.R;
import com.zrdz.diji.baseui.BaseBottomListActivity;
import com.zrdz.diji.bean.DistricOfCityInfo;
import com.zrdz.diji.bean.PileNoQueryInfo;
import com.zrdz.diji.utils.CommonUtils;
import com.zrdz.diji.utils.Const;
import com.zrdz.diji.utils.LinkServiceUtils;
import com.zrdz.diji.utils.LogUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * 试验管理的基桩查询界面
 */
public class QueryActivity extends BaseBottomListActivity implements View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ListView>, AdapterView.OnItemSelectedListener {
    private List<PileNoQueryInfo> list = new ArrayList();
    private MyAdapter adapter;
    private String testtype;//试验类型 终止实验和结束试验有
    private String managetype;//管理类型
    private String districIDs = "";//监督站id
    private String strStartTime = "";
    private EditText edit_project;
    private TextView edit_station;
    private ProgressDialog progressDialog;//进度条

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    adapter.notifyDataSetChanged();
                    showToast(getString(R.string.notdata));
                    progressDialog.dismiss();
                    pullToRefreshListView.onRefreshComplete();
                    break;
                case 1:
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                    pullToRefreshListView.onRefreshComplete();
                    break;

                case 2://获取监督站列表
                    final List<DistricOfCityInfo> districList = (List<DistricOfCityInfo>) msg.obj;
                    creatDialogforstation(districList);
                    break;
                case -1:
                    String message = (String) msg.obj;
                    showToast(message);
                    break;
            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String flag = getIntent().getStringExtra("flag");
        LogUtils.e("flag==" + flag);
        if ("testing".equals(flag)) {
            setLLVisible(true);
        }
        initBaseBottomListArgument(R.layout.activity_device_warn, getString(R.string.pilenoquery));
        initView();
        getData();
    }

    private void initView() {
        managetype = getIntent().getStringExtra("managetype");
        if ("2".equals(managetype)) {
            testtype = getIntent().getStringExtra("testtype");
        }
        if ("3".equals(managetype)) {
            spinner_starttime.setOnItemSelectedListener(this);
        }
        edit_project = (EditText) findViewById(R.id.query_project);
        edit_station = (TextView) findViewById(R.id.query_station);
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
        edit_station.setOnClickListener(this);
        btn_query.setOnClickListener(this);
        btn_reset.setOnClickListener(this);
        pullToRefreshListView.setOnRefreshListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PileNoQueryInfo info = list.get(position - 1);
                Intent intent = new Intent(QueryActivity.this, PileInfoDetailActivity.class);
                intent.putExtra("pid", info.pile_pid);
                intent.putExtra("managetype", managetype);
                intent.putExtra("testtype", testtype);
                intent.putExtra("startDate", info.startDate);//试验开始时间
                startActivity(intent);
            }
        });
    }

    /**
     * 获取数据
     */
    private void getData() {
        if (null == progressDialog) {
            CommonUtils utils = new CommonUtils();
            progressDialog = utils.initProgressDialog(this);
        }
        progressDialog.show();
        final String project = edit_project.getText().toString().trim();
        new Thread(new Runnable() {
            @Override
            public void run() {
                LinkServiceUtils linkServiceUtils = LinkServiceUtils.getInstance();
                String pileUnbatchInfo = linkServiceUtils.getJzPileUnbatchInfoNew(QueryActivity.this, MyApplication.user_code,
                        MyApplication.user_userType, project, districIDs, managetype, strStartTime, MyApplication.user_pid);
                List<PileNoQueryInfo> pileNoQueryInfos = CommonUtils.paringPlieJsonStr(pileUnbatchInfo);
                list.clear();
                if (null == pileNoQueryInfos) {
                    Message msg = new Message();
                    msg.what = 0;
                    handler.sendMessage(msg);
                } else {
                    list.addAll(pileNoQueryInfos);
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
            }
        }).start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.query_station:
                createCityDialog(this);
                break;
            case R.id.baselist_query:
                getData();
                break;
            case R.id.baselist_reset:
                districIDs = "";//监督站id
                edit_station.setText("");
                edit_station.setHint(R.string.qualitystation1);
                edit_project.setText("");
                edit_project.setHint(R.string.projectname1);
                break;
        }
    }

    @Override
    public void onPullStartToRefresh(PullToRefreshBase<ListView> refreshView) {
        getData();
    }

    @Override
    public void onPullEndToRefresh(PullToRefreshBase<ListView> refreshView) {

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                strStartTime = "";
                break;
            case 1:
                strStartTime = "Y";
                break;
            case 2:
                strStartTime = "D";
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PileNoQueryInfo info = list.get(position);
            View view = getLayoutInflater().inflate(R.layout.item_pilenoquery, null);
            TextView text_projectName = (TextView) view.findViewById(R.id.item_pilenoquery_projectname);
            TextView text_pileNo = (TextView) view.findViewById(R.id.item_pilenoquery_pileno);
            TextView text_jcjg = (TextView) view.findViewById(R.id.item_pilenoquery_jcjg);
            TextView text_station = (TextView) view.findViewById(R.id.item_pilenoquery_station);
            RelativeLayout ll1 = (RelativeLayout) view.findViewById(R.id.item_pilenoquery_rl1);
            RelativeLayout ll2 = (RelativeLayout) view.findViewById(R.id.item_pilenoquery_rl2);
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.GONE);
            text_projectName.setText(info.projectName);
            text_pileNo.setText(info.pile_Id);
            text_jcjg.setText(info.inSpectInstituTionName);
            text_station.setText(info.districtIdName);
            return view;
        }
    }

    /**
     * 生成dialog
     *
     * @param context
     */
    public void createCityDialog(final Context context) {
        Dialog dialog = new AlertDialog.Builder(context).setTitle(R.string.pleaseselectstation)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setNegativeButton(R.string.cacel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setItems(Const.CITYARRAY, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int which) {
                        if (context.getResources().getStringArray(R.array.fuzhoujiushi)
                                [which].equals(context.getResources().getString(R.string.PromptCityFuZhou))) {
                            getDistricList("106");
                        } else if (context.getResources().getStringArray(R.array.fuzhoujiushi)[which]
                                .equals(context.getResources().getString(R.string.PromptCityPuTian))) {
                            getDistricList("107");
                        } else if (context.getResources().getStringArray(R.array.fuzhoujiushi)[which]
                                .equals(context.getResources().getString(R.string.PromptCityQuanZhou))) {
                            getDistricList("108");
                        } else if (context.getResources().getStringArray(
                                R.array.fuzhoujiushi)[which]
                                .equals(context.getResources().getString(R.string.PromptCityXiMen))) {
                            getDistricList("109");
                        } else if (context.getResources().getStringArray(R.array.fuzhoujiushi)[which]
                                .equals(context.getResources().getString(R.string.PromptCityZhangZhou))) {
                            getDistricList("110");
                        } else if (context.getResources().getStringArray(R.array.fuzhoujiushi)[which]
                                .equals(context.getResources().getString(R.string.PromptCityLongYan))) {
                            getDistricList("111");
                        } else if (context.getResources().getStringArray(R.array.fuzhoujiushi)[which]
                                .equals(context.getResources().getString(R.string.PromptCitySanMing))) {
                            getDistricList("112");
                        } else if (context.getResources().getStringArray(R.array.fuzhoujiushi)[which]
                                .equals(context.getResources().getString(R.string.PromptCityNanPin))) {
                            getDistricList("113");
                        } else if (context.getResources().getStringArray(R.array.fuzhoujiushi)[which]
                                .equals(context.getResources().getString(R.string.PromptCityNingDe))) {
                            getDistricList("114");
                        } else if (context.getResources().getStringArray(R.array.fuzhoujiushi)[which].equals(getString(R.string.PromptCityZZ))) {
                            getDistricList("999");
                        }

                    }
                }).create();
        dialog.show();
    }

    /**
     * 获取监督站列表
     *
     * @param id
     */
    public void getDistricList(final String id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LinkServiceUtils linkServiceUtils = LinkServiceUtils.getInstance();
                String result = linkServiceUtils.linkqueryDistrictName(id);
                try {
                    List<DistricOfCityInfo> districOfCityInfos = CommonUtils.paringStation(result);
                    if (districOfCityInfos.size() <= 0) {
                        Message message = new Message();
                        message.what = 0;
                        handler.sendMessage(message);
                    } else {
                        Message message = new Message();
                        message.what = 2;
                        message.obj = districOfCityInfos;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Message message = new Message();
                    message.what = -1;
                    message.obj = e.getMessage();
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    /**
     * 生成市区下的站点dialog
     *
     * @param districList
     */
    private void creatDialogforstation(final List<DistricOfCityInfo> districList) {
        final String[] districArray = new String[districList.size()];
        for (int i = 0; i < districList.size(); i++) {
            DistricOfCityInfo info = districList.get(i);
            String infoStr = info.inspectInstitutionName;
            districArray[i] = infoStr;
        }
        AlertDialog dialog = new AlertDialog.Builder(QueryActivity.this).setTitle(R.string.pleaseselectstation).setIcon(android.R.drawable.ic_dialog_info)
                .setNegativeButton(R.string.cacel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setItems(districArray, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DistricOfCityInfo info = districList.get(which);
                        districIDs = info.jcjgId + ";";
                        edit_station.setText(districArray[which]);
                    }
                }).create();
        dialog.show();
    }

}
