package com.zrdz.diji.home.pilequery.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zrdz.diji.R;
import com.zrdz.diji.baseui.BaseFragment;

/**
 * 位移时间图界面
 */
public class DistimeFragment extends BaseFragment {


    public DistimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_distime, container, false);
    }

}
