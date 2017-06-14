package com.zrdz.diji.baseui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zrdz.diji.R;

import java.util.List;

/**
 * Created by ASUS on 2017/4/17.
 */

public class BaseListAdapter extends BaseAdapter {

    private List<String> mList;
    private Context mContext;

    public BaseListAdapter(List<String> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }


    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList == null ? null : mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mList == null ? 0 : i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_text, null);
        TextView text = (TextView) inflate.findViewById(R.id.item_text);
        text.setText(mList.get(i));
        return inflate;
    }
}
