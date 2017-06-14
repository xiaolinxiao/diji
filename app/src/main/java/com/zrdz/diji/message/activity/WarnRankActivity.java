package com.zrdz.diji.message.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lzy.okhttputils.model.HttpParams;
import com.zrdz.diji.MyApplication;
import com.zrdz.diji.R;
import com.zrdz.diji.bean.WarnRankInfo;
import com.zrdz.diji.callback.DialogCallback;
import com.zrdz.diji.message.base.BaseRankActivity;
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
 * 警示排名
 */
public class WarnRankActivity extends BaseRankActivity implements View.OnClickListener {
    private String year;
    private List<WarnRankInfo> list = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        Calendar c = Calendar.getInstance();
        text_time.setText("" + c.get(Calendar.YEAR));
        text_time.setOnClickListener(this);
        btn_rank_query.setOnClickListener(this);
        adapter = new MyAdapter();
        baseListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rank_text_time:
                Calendar c = Calendar.getInstance();
                MyDatePickerDialog datePickerDialog = new MyDatePickerDialog(this, 0, new MyDatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth) {
                        year = "" + startYear;
                        text_time.setText("" + year);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), false, 1);
                datePickerDialog.show();
                break;
            case R.id.rank_btn_query:
                getData();
                break;
        }
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
            WarnRankInfo warnRankEntiny = list.get(position);
            View inflate = getLayoutInflater().inflate(R.layout.item_warnrank, null);
            TextView text_name = (TextView) inflate.findViewById(R.id.tv_project_name);
            TextView text_time = (TextView) inflate.findViewById(R.id.tv_project_time);
            TextView text_rank = (TextView) inflate.findViewById(R.id.tv_project_rank);
            TextView text_total = (TextView) inflate.findViewById(R.id.tv_project_total);
            text_name.setText(warnRankEntiny.companyName);
            text_time.setText(warnRankEntiny.year + "年第" + warnRankEntiny.quarter + "季度");
            text_rank.setText(warnRankEntiny.rankNo + "名");
            text_total.setText(warnRankEntiny.total);
            return inflate;
        }
    }

    /**
     * 获取数据
     */
    public void getData() {
        year = text_time.getText().toString().trim();
        HttpParams httpParams = new HttpParams();
        httpParams.put("year", year);
        httpParams.put("quarter", quarter);
        String string = MyApplication.user_code;
        httpParams.put("jcjg_id", string);
        CommonUtils.httpGet(this, Const.MAIN_URL, Const.WARNRANK_MOUDLE, httpParams, new DialogCallback<List<WarnRankInfo>>(this, new TypeToken<List<WarnRankInfo>>() {
        }.getType()) {
            @Override
            public void onResponse(boolean isFromCache, List<WarnRankInfo> warnRankInfos, Request request, @Nullable Response response) {
                list.clear();
                if (warnRankInfos.size() <= 0) {
                    Toast.makeText(WarnRankActivity.this, getString(R.string.notdata), Toast.LENGTH_SHORT).show();
                } else {
                    list.addAll(warnRankInfos);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                Toast.makeText(WarnRankActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
