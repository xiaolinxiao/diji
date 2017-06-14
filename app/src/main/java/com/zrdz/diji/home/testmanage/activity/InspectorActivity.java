package com.zrdz.diji.home.testmanage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.zrdz.diji.R;
import com.zrdz.diji.baseui.BaseBackTitleActivity;
import com.zrdz.diji.bean.DistricOfCityInfo;
import com.zrdz.diji.home.testmanage.adapter.InspectorAdapter;
import com.zrdz.diji.utils.CommonUtils;
import com.zrdz.diji.utils.LogUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 监督员界面
 */
public class InspectorActivity extends BaseBackTitleActivity implements ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener {
    private ExpandableListView exListView;
    String[] citiName = {"福州", "莆田", "泉州", "厦门", "漳州", "龙岩", "三明", "南平", "宁德",
            "省总站"};
    String[] citiId = {"106", "107", "108", "109", "110", "111", "112", "113",
            "114", "999"};
    private List<LinkedHashMap<String, String>> cityMap;
    private int sign = -1;//
    private List<List<DistricOfCityInfo>> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgument(R.layout.activity_inspector, getString(R.string.jdyArray));
        initView();
        initData();
    }

    private void initView() {
        exListView = (ExpandableListView) findViewById(R.id.ExpandableListView01);
        exListView.setGroupIndicator(null);

        exListView.setOnChildClickListener(this);
        exListView.setOnGroupClickListener(this);
    }

    private void initData() {
        cityMap = new ArrayList();
        // expandablelistview的group数据，即城市列表
        for (int i = 0; i < citiName.length; i++) {
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
            map.put("name", citiName[i]);
            map.put("id", citiId[i]);
            cityMap.add(map);
        }
        //解析数据库信息
        String json = CommonUtils.readLocalJson(this);
        dataList = CommonUtils.parsingJson(json, this);
        for (int i = 0; i < dataList.size(); i++) {
            List<DistricOfCityInfo> districOfCityInfos = dataList.get(i);
            LogUtils.e("==" + districOfCityInfos.toString());
        }
        InspectorAdapter adapter = new InspectorAdapter(cityMap, dataList, this);
        exListView.setAdapter(adapter);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        String jid = dataList.get(groupPosition).get(childPosition).jcjgId;
        Bundle bundle = new Bundle();
        bundle.putString("jcjgId", jid);
        Intent intent = new Intent(this, InspectorDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        if (sign == -1) {
            // 展开被选的group
            exListView.expandGroup(groupPosition);
            // 设置被选中的group置于顶端
            exListView.setSelectedGroup(groupPosition);
            sign = groupPosition;
        } else if (sign == groupPosition) {
            exListView.collapseGroup(sign);
            sign = -1;
        } else {
            exListView.collapseGroup(sign);
            // 展开被选的group
            exListView.expandGroup(groupPosition);
            // 设置被选中的group置于顶端
            exListView.setSelectedGroup(groupPosition);
            sign = groupPosition;
        }
        return true;
    }
}
