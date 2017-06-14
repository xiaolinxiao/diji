package com.zrdz.diji.home.pilequery.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zrdz.diji.R;
import com.zrdz.diji.baseui.BaseBottomListActivity;
import com.zrdz.diji.bean.DeviceInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备管理
 */
public class DeviceWarnActivity extends BaseBottomListActivity {
    private List<DeviceInfo> list = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBaseBottomListArgument(R.layout.activity_device_warn, getString(R.string.devicewarninginfo));
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
        initData();

    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            DeviceInfo info = new DeviceInfo("JZ004-31", "位移传感器", "同一设备同一时间在不同桩号出现");
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
            DeviceInfo info = list.get(position);
            View inflate = getLayoutInflater().inflate(R.layout.item_device, null);
            TextView text_name = (TextView) inflate.findViewById(R.id.item_device_name);
            TextView text_id = (TextView) inflate.findViewById(R.id.item_device_id);
            TextView text_info = (TextView) inflate.findViewById(R.id.item_device_info);
            text_name.setText(info.equipmentName);
            text_id.setText(info.equipmentid);
            text_info.setText(info.alertInfo);
            return inflate;
        }
    }


}
