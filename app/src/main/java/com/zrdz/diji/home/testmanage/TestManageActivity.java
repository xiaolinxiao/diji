package com.zrdz.diji.home.testmanage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zrdz.diji.R;
import com.zrdz.diji.baseui.BaseBackTitleActivity;
import com.zrdz.diji.baseui.adapter.BaseListAdapter;
import com.zrdz.diji.home.testmanage.activity.InspectorActivity;
import com.zrdz.diji.home.testmanage.activity.QueryActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 实验管理模块总入口
 */
public class TestManageActivity extends BaseBackTitleActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgument(R.layout.activity_pile_manage, getString(R.string.piletestmanagement));
        initView();
    }

    private void initView() {
        ListView listView = (ListView) findViewById(R.id.pile_listview);
        List<String> list = new ArrayList<>();
        list.add(getString(R.string.teststart));
        list.add(getString(R.string.querytestinfo));
        list.add(getString(R.string.teststop));
        list.add(getString(R.string.testover));
        list.add(getString(R.string.queryman));
        listView.setAdapter(new BaseListAdapter(list, this));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0://基桩开始试验告知
                Intent intent = new Intent(this, QueryActivity.class);
                intent.putExtra("managetype", "1");
                intent.putExtra("flag", "starttest");
                startActivity(intent);
                break;
            case 1://正在试验的基桩信息查询
                Intent intent1 = new Intent(this, QueryActivity.class);
                intent1.putExtra("managetype", "3");
                intent1.putExtra("flag", "testing");
                startActivity(intent1);
                break;
            case 2://中止试验
                Intent intent2 = new Intent(this, QueryActivity.class);
                intent2.putExtra("managetype", "2");
                intent2.putExtra("testtype", "1");
                startActivity(intent2);
                break;
            case 3:
                Intent intent3 = new Intent(this, QueryActivity.class);
                intent3.putExtra("testtype", "2");
                intent3.putExtra("managetype", "2");
                startActivity(intent3);
                break;
            case 4:
                Intent intent5 = new Intent(this, InspectorActivity.class);
                startActivity(intent5);
                break;
        }
    }
}
