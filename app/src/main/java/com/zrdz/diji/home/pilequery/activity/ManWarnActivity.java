package com.zrdz.diji.home.pilequery.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zrdz.diji.R;
import com.zrdz.diji.baseui.BaseBottomListActivity;
import com.zrdz.diji.bean.ManInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 人员管理
 */
public class ManWarnActivity extends BaseBottomListActivity {
    private List<ManInfo> list = new ArrayList();
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBaseBottomListArgument(R.layout.activity_device_warn, getString(R.string.staffwarninginfo));
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
        initData();
    }

    private void initData() {
        for (int i = 0; i < 30; i++) {
            ManInfo manInfo = new ManInfo("同一人员同一时间在不同工程出现", "王小二", "福建省中正辉煌工程检测有限公司");
            list.add(manInfo);
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
            ManInfo manInfo = list.get(position);
            View inflate = getLayoutInflater().inflate(R.layout.item_man, null);
            TextView text_company = (TextView) inflate.findViewById(R.id.item_man_campany);
            TextView text_name = (TextView) inflate.findViewById(R.id.item_man_name);
            TextView text_info = (TextView) inflate.findViewById(R.id.item_man_info);

            text_company.setText(manInfo.inSpectInstituTionName);
            text_name.setText(manInfo.personName);
            text_info.setText(manInfo.alertInfo);
            return inflate;
        }
    }
}
