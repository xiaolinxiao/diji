package com.zrdz.diji.home.pilequery.activity;


import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zrdz.diji.R;
import com.zrdz.diji.baseui.BaseBottomListActivity;
import com.zrdz.diji.bean.PileInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 桩点信息查询界面
 */
public class PileQueryActivity extends BaseBottomListActivity {
    private List<PileInfo> list = new ArrayList<>();
    private MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBaseBottomListArgument(R.layout.activity_pile_query, getString(R.string.pileinformationquery));
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
        initData();
    }

    /**
     * 测试用数据
     */
    private void initData() {
        for (int i = 0; i < 30; i++) {
            PileInfo pileInfo = new PileInfo("无", "福建省中正辉煌工程检测有限公司", "JC550961535", "4400", "2017J047-76",
                    "8F7DBF808F4248B891D1AE4403538A80", "");
            list.add(pileInfo);
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
            PileInfo pileInfo = list.get(position);
            View inflate = getLayoutInflater().inflate(R.layout.item_pile, null);
            TextView text_pileNo = (TextView) inflate.findViewById(R.id.item_pile_pileno);
            TextView text_maxload = (TextView) inflate.findViewById(R.id.item_pile_maxload);
            TextView text_testinfo = (TextView) inflate.findViewById(R.id.item_pile_testinfo);
            TextView text_starttime = (TextView) inflate.findViewById(R.id.item_pile_starttime);
            text_pileNo.setText(pileInfo.pile_Id);
            text_maxload.setText(pileInfo.max_Load);
            text_testinfo.setText(pileInfo.experimentInformation);
            text_starttime.setText(pileInfo.startDate);
            return inflate;
        }
    }
}
