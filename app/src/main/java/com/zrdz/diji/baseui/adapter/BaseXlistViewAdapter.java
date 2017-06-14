package com.zrdz.diji.baseui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zrdz.diji.bean.NorResponseInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2017/4/17.
 */

public class BaseXlistViewAdapter extends BaseAdapter {
    private List<List<NorResponseInfo>> dataList = new ArrayList<>();
    private Context context;

    public BaseXlistViewAdapter(Context context, List<List<NorResponseInfo>> dataList) {
        this.dataList = dataList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList == null ? null : dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return dataList == null ? 0 : i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        return null;
    }
}
