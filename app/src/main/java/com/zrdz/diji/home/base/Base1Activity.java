package com.zrdz.diji.home.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lzy.okhttputils.model.HttpParams;
import com.zrdz.diji.MyApplication;
import com.zrdz.diji.R;
import com.zrdz.diji.baseui.BaseBottomListActivity;
import com.zrdz.diji.bean.TestFirstInfo;
import com.zrdz.diji.callback.DialogCallback;
import com.zrdz.diji.home.pilequery.activity.TestFirstActivity;
import com.zrdz.diji.utils.CommonUtils;
import com.zrdz.diji.utils.Const;
import com.zrdz.diji.view.MyDatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 基桩管理的第一种基类,继承BaseBottomListActivity
 * 是未备案与先实验后备案的父类
 */
public class Base1Activity extends BaseBottomListActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    public List<TestFirstInfo> list = new ArrayList<>();
    public MyAdapter adapter;
    public int flag = 0;
    private String module = "";
    private int curPage = 0;
    private String endTime = "";
    private String startTime = "";
    private String pileId = "";
    private EditText text_pileno;
    private TextView text_starttime;
    private TextView text_endtime;
    private boolean isQuery = false;//查询标志

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 子类必须调用
     *
     * @param title
     */
    public void initBase1View(String title, int flag) {
        initBaseBottomListArgument(R.layout.activity_test_first, title);
        this.flag = flag;
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        TextView text_company = (TextView) findViewById(R.id.base1_text_company);
        text_pileno = (EditText) findViewById(R.id.base1_edit_pileno);
        text_starttime = (TextView) findViewById(R.id.base1_text_starttime);
        text_endtime = (TextView) findViewById(R.id.base1_text_endtime);
        btn_query.setOnClickListener(this);
        btn_reset.setOnClickListener(this);
        text_starttime.setOnClickListener(this);
        text_endtime.setOnClickListener(this);
        text_company.setText(MyApplication.user_name);
        pullToRefreshListView.setOnRefreshListener(new refreshListener());
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);//两端刷新
        initData();
    }

    /**
     * 刷新操作
     */
    class refreshListener implements PullToRefreshBase.OnRefreshListener2<ListView> {
        @Override
        public void onPullStartToRefresh(PullToRefreshBase<ListView> refreshView) {
            curPage = 0;
            reset();
            initData();
        }

        @Override
        public void onPullEndToRefresh(PullToRefreshBase<ListView> refreshView) {
            curPage++;
            initData();
        }
    }

    /**
     * 测试数据
     */
    private void initData() {
        if (1 == flag) {
            module = Const.NOBACKUPPILE__MODULE;
        } else {
            module = Const.TESTFIRST_MODULE;
        }
        pileId = text_pileno.getText().toString().trim();
        startTime = text_starttime.getText().toString().trim();
        endTime = text_endtime.getText().toString().trim();
        HttpParams httpParams = new HttpParams();
        httpParams.put("jcjg_Id", MyApplication.user_code);
        httpParams.put("endDate", endTime);
        httpParams.put("startDate", startTime);
        httpParams.put("page_begin", "" + curPage);
        httpParams.put("pile_Id", pileId);
        httpParams.put("page_end", "" + (curPage + 1));
        if (isQuery) {
            list.clear();
        }
        CommonUtils.httpGet(this, Const.MAIN_URL_222, module, httpParams,
                new DialogCallback<List<TestFirstInfo>>(this, new TypeToken<List<TestFirstInfo>>() {
                }.getType()) {
                    @Override
                    public void onResponse(boolean isFromCache, List<TestFirstInfo> testFirstInfos, Request request, @Nullable Response response) {
                        if (null == testFirstInfos) {
                            showToast(getString(R.string.nomoredata));
                        } else {
                            list.addAll(testFirstInfos);
                        }
                        adapter.notifyDataSetChanged();
                        isQuery = false;
                        pullToRefreshListView.onRefreshComplete();
                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                        showToast(getString(R.string.datapostfail));
                        isQuery = false;
                        pullToRefreshListView.onRefreshComplete();
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, TestFirstActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.baselist_query:
                isQuery = true;
                initData();
                break;
            case R.id.baselist_reset:
                reset();
                break;
            case R.id.base1_text_starttime:
                Calendar c = Calendar.getInstance();
                MyDatePickerDialog datePickerDialog = new MyDatePickerDialog(this, 0, new MyDatePickerDialog.OnDateSetListener() {
                    private String monthStr;
                    private String dayStr;

                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth) {
                        if ((startMonthOfYear + 1) < 10)
                            monthStr = "0" + (startMonthOfYear + 1);
                        else
                            monthStr = "" + (startMonthOfYear + 1);
                        if ((startDayOfMonth + 1) < 10)
                            dayStr = "0" + startDayOfMonth;
                        else
                            dayStr = "" + startDayOfMonth;
                        text_starttime.setText(startYear + "-" + monthStr + "-" + dayStr);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true, 1);
                datePickerDialog.show();
                break;
            case R.id.base1_text_endtime:
                Calendar c1 = Calendar.getInstance();
                MyDatePickerDialog datePickerDialog1 = new MyDatePickerDialog(this, 0, new MyDatePickerDialog.OnDateSetListener() {
                    private String monthStr;
                    private String dayStr;

                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth) {
                        if ((startMonthOfYear + 1) < 10)
                            monthStr = "0" + (startMonthOfYear + 1);
                        else
                            monthStr = "" + (startDayOfMonth + 1);
                        if ((startDayOfMonth + 1) < 10)
                            dayStr = "0" + startDayOfMonth;
                        else
                            dayStr = "" + startDayOfMonth;
                        text_endtime.setText(startYear + "-" + monthStr + "-" + dayStr);
                    }
                }, c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DATE), true, 1);
                datePickerDialog1.show();
                break;
        }
    }

    /**
     * 重置过滤条件
     */
    private void reset() {
        text_pileno.setText("");
        text_starttime.setHint(getString(R.string.starttime));
        text_endtime.setHint(getString(R.string.endtime));
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
            TestFirstInfo info = list.get(position);
            View inflate = getLayoutInflater().inflate(R.layout.item_testfirst, null);
            TextView name = (TextView) inflate.findViewById(R.id.item_testfirst_companyname);
            TextView pileNo = (TextView) inflate.findViewById(R.id.item_testfirst_pileno);
            TextView time = (TextView) inflate.findViewById(R.id.item_testfirst_starttime);
            name.setText(info.inSpectInstituTionName);
            pileNo.setText(info.pile_Id);
            time.setText(info.startDate);
            return inflate;
        }
    }
}
