package com.zrdz.diji.message.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okhttputils.callback.StringCallback;
import com.lzy.okhttputils.model.HttpParams;
import com.zrdz.diji.MyApplication;
import com.zrdz.diji.R;
import com.zrdz.diji.bean.CreditRankInfo;
import com.zrdz.diji.message.base.BaseRankActivity;
import com.zrdz.diji.utils.CommonUtils;
import com.zrdz.diji.utils.Const;
import com.zrdz.diji.utils.LogUtils;
import com.zrdz.diji.view.MyDatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 信用排名界面
 */
public class CreditRankActivity extends BaseRankActivity implements View.OnClickListener {
    private List<CreditRankInfo> list = new ArrayList<>();
    private String year;
    private String code;//组织机构码
    private ProgressDialog progressDialog = null;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        text_time.setOnClickListener(this);
        adapter = new MyAdapter();
        baseListView.setAdapter(adapter);
        btn_rank_query.setOnClickListener(this);
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
        list.clear();
        String codeStr = MyApplication.user_code;
        code = codeStr.substring(2, codeStr.length());
        HttpParams httpParams = new HttpParams();
        httpParams.put("KeyCode", "AP0001");//AP0001必传入 固定值
        httpParams.put("Year", year);
        httpParams.put("Quarter", quarter);
        httpParams.put("OrgCode", code);
        CommonUtils.httpGet(this, Const.RANK_CREDIT, "", httpParams, new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                LogUtils.e("======" + s);
                try {
                    if (null == s || "[]".equals(s) || "".equals(s)) {
                        Toast.makeText(CreditRankActivity.this, getString(R.string.notdata), Toast.LENGTH_SHORT).show();
                    } else {
                        JSONObject jsonObject = new JSONObject(s);
                        String result = jsonObject.getString("Result");
                        if (null == result || "".equals(result)) {
                            Toast.makeText(CreditRankActivity.this, getString(R.string.notdata), Toast.LENGTH_SHORT).show();
                        } else {
                            if ("true".equals(result)) {
                                JSONArray content = jsonObject.getJSONArray("Content");
                                for (int i = 0; i < content.length(); i++) {
                                    JSONObject obj = (JSONObject) content.get(i);
                                    String qyName = obj.getString("企业名称");
                                    String zzCode = obj.getString("组织机构代码");
                                    String zfzScore = obj.getString("总分值");
                                    String pmRank = obj.getString("排名");
                                    CreditRankInfo entity = new CreditRankInfo(qyName, zzCode, pmRank, zfzScore);
                                    list.add(entity);
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                progressDialog.dismiss();
                Toast.makeText(CreditRankActivity.this, R.string.getdatafail, Toast.LENGTH_SHORT).show();
            }
        });


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
            CreditRankInfo creaditRankEntity = list.get(position);
            View inflate = getLayoutInflater().inflate(R.layout.item_creditrank, null);
            TextView text_name = (TextView) inflate.findViewById(R.id.tv_name);
            TextView text_score = (TextView) inflate.findViewById(R.id.tv_totalscore);
            TextView text_rank = (TextView) inflate.findViewById(R.id.tv_rank);
            text_name.setText("" + creaditRankEntity.name);
            text_score.setText("" + creaditRankEntity.score);
            text_rank.setText("" + creaditRankEntity.rank);
            return inflate;
        }
    }
}
