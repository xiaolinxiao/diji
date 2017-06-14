package com.zrdz.diji.home.pilequery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zrdz.diji.R;
import com.zrdz.diji.baseui.BaseBackTitleActivity;
import com.zrdz.diji.baseui.adapter.BaseListAdapter;
import com.zrdz.diji.home.pilequery.activity.DeviceWarnActivity;
import com.zrdz.diji.home.pilequery.activity.JDHExceptionActivity;
import com.zrdz.diji.home.pilequery.activity.ManWarnActivity;
import com.zrdz.diji.home.pilequery.activity.NoRecordActivity;
import com.zrdz.diji.home.pilequery.activity.PileQueryActivity;
import com.zrdz.diji.home.pilequery.activity.TestFirstActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 基桩查询模块总界面
 */
public class PileManageActivity extends BaseBackTitleActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgument(R.layout.activity_pile_manage, getString(R.string.pilequery));
        initView();
    }

    private void initView() {
        ListView listView = (ListView) findViewById(R.id.pile_listview);
        List<String> list = new ArrayList<>();
        list.add(getString(R.string.firstest));
        list.add(getString(R.string.notrecordpileno));
        list.add(getString(R.string.pileinformationquery));
        list.add(getString(R.string.devicewarninginfo));
        list.add(getString(R.string.staffwarninginfo));
        list.add(getString(R.string.jdhexception));
        listView.setAdapter(new BaseListAdapter(list, this));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                startActivity(new Intent(this, TestFirstActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, NoRecordActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, PileQueryActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, DeviceWarnActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, ManWarnActivity.class));
                break;
            case 5:
                Intent intent4 = new Intent(this, JDHExceptionActivity.class);
                startActivity(intent4);
                break;
        }
    }
}
