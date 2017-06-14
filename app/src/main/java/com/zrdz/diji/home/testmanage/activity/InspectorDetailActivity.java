package com.zrdz.diji.home.testmanage.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zrdz.diji.R;
import com.zrdz.diji.baseui.BaseBackTitleActivity;
import com.zrdz.diji.bean.InspectorInfo;
import com.zrdz.diji.utils.CommonUtils;
import com.zrdz.diji.utils.LinkServiceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 监督员名单
 */
public class InspectorDetailActivity extends BaseBackTitleActivity {

    private List<InspectorInfo> dataList = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    dialog.dismiss();
                    showToast(getString(R.string.notdata));
                    break;
                case 1:
                    List<InspectorInfo> inspectorInfos = (List<InspectorInfo>) msg.obj;
                    dataList.addAll(inspectorInfos);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                    break;
                case -1:
                    dialog.dismiss();
                    showToast(getString(R.string.datapostfail));
                    break;


            }


        }
    };
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgument(R.layout.activity_inspector_detail, getString(R.string.jdyArray));
        initView();
    }

    private void initView() {
        String jcjgId = getIntent().getStringExtra("jcjgId");
        ListView listView = (ListView) findViewById(R.id.inspector_listview);
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
        getData(jcjgId);
    }

    /**
     * 获取数据
     *
     * @param id
     */
    public void getData(final String id) {
        initDialog();
        new Thread(new Runnable() {

            @Override
            public void run() {
                LinkServiceUtils linkServiceUtils = LinkServiceUtils.getInstance();
                String s = linkServiceUtils.linkquerySupervisorName(InspectorDetailActivity.this, id);
                if ("".equals(s)) {
                    Message msg = new Message();
                    msg.what = 0;
                    handler.sendMessage(msg);
                } else if (null == s) {
                    Message msg = new Message();
                    msg.what = -1;
                    handler.sendMessage(msg);
                } else {
                    List<InspectorInfo> inspectorInfos = CommonUtils.JsonPeopleList(InspectorDetailActivity.this, s);
                    Message msg = new Message();
                    if (null == inspectorInfos) {
                        msg.what = 0;
                        handler.sendMessage(msg);
                    } else {
                        msg.what = 1;
                        msg.obj = inspectorInfos;
                        handler.sendMessage(msg);
                    }
                }
            }
        }).start();
    }

    class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            InspectorInfo info = dataList.get(position);
            View inflate = getLayoutInflater().inflate(R.layout.item_inspector, null);
            TextView text_name = (TextView) inflate.findViewById(R.id.item_inspector_name);
            TextView text_address = (TextView) inflate.findViewById(R.id.item_inspector_address);
            TextView text_phone = (TextView) inflate.findViewById(R.id.item_inspector_phone);
            text_name.setText(info.userName);
            text_address.setText(info.jcjgName);
            text_phone.setText(info.userPhone);
            return inflate;
        }
    }
}
