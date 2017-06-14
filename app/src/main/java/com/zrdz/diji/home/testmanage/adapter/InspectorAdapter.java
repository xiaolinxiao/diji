package com.zrdz.diji.home.testmanage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zrdz.diji.R;
import com.zrdz.diji.bean.DistricOfCityInfo;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 监督员名单适配器
 * Created by Administrator on 2017/5/18.
 */

public class InspectorAdapter extends BaseExpandableListAdapter {
    private List<LinkedHashMap<String, String>> groupData;// 保存传回来的group数据
    private List<List<DistricOfCityInfo>> childData;// 保存JSON传回来的数据
    private Context context;

    public InspectorAdapter(List<LinkedHashMap<String, String>> groupData, List<List<DistricOfCityInfo>> childData, Context context) {
        this.groupData = groupData;
        this.childData = childData;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childData.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_expand_child, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textView1);
            holder.image = (ImageView) convertView.findViewById(R.id.imageView1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String text = groupData.get(groupPosition).get("name");
        holder.name.setText(text);
        if (isExpanded) {
            holder.image.setBackgroundResource(R.drawable.expand_img);
        } else {
            holder.image.setBackgroundResource(R.drawable.unexpand_img);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_expand_child, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textView1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(childData.get(groupPosition).get(childPosition).inspectInstitutionName);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class ViewHolder {
        ImageView image;
        TextView name;
    }
}
