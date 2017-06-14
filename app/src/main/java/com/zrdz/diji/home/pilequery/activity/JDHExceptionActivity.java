package com.zrdz.diji.home.pilequery.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zrdz.diji.R;
import com.zrdz.diji.baseui.BaseBottomListActivity;
import com.zrdz.diji.bean.PileNoQueryInfo;
import com.zrdz.diji.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 机顶盒异常告知
 */
public class JDHExceptionActivity extends BaseBottomListActivity {
    private List<PileNoQueryInfo> list = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLLVisible(true);
        initBaseBottomListArgument(R.layout.activity_jdhexception, getString(R.string.jdhexception));
        initView();
        initData();
    }

    private void initView() {
        String flag = getIntent().getStringExtra("flag");
        LogUtils.e("flag==" + flag);
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            PileNoQueryInfo info = new PileNoQueryInfo("设备警示1", "5BA8B0053281479AA8D83D9C8A429A95",
                    "晋江市西滨中学新校区二期（办公楼、图书馆、报告厅、2#教师宿舍楼）工程", "福建省中正辉煌工程检测有限公司",
                    "晋江市建设工程行政执法大队", "2017-02-27 14:33", "2017-04-06 16:50:32");
            list.add(info);
        }
        adapter.notifyDataSetChanged();
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
            TextView text_starttime = (TextView) view.findViewById(R.id.item_pilenoquery_starttime);
            TextView text_talltime = (TextView) view.findViewById(R.id.item_pilenoquery_talltime);
            text_projectName.setText(info.projectName);
            text_pileNo.setText(info.pile_Id);
            text_jcjg.setText(info.inSpectInstituTionName);
            text_station.setText(info.districtIdName);
            text_starttime.setText(info.startDate);
            text_talltime.setText(info.startTime);
            return view;
        }
    }
}
