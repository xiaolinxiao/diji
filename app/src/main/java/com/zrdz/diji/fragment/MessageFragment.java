package com.zrdz.diji.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zrdz.diji.R;
import com.zrdz.diji.message.activity.CreditRankActivity;
import com.zrdz.diji.message.activity.WarnRankActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment implements View.OnClickListener {


    private View inflate;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null == inflate) {
            inflate = inflater.inflate(R.layout.fragment_message, container, false);
            initView();
        }
        return inflate;
    }

    private void initView() {
        TextView text_credit = (TextView) inflate.findViewById(R.id.credit_rank);
        TextView text_warn = (TextView) inflate.findViewById(R.id.warn_rank);
        text_credit.setOnClickListener(this);
        text_warn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.credit_rank:
                Intent intent = new Intent(getActivity(), CreditRankActivity.class);
                intent.putExtra("flag", "credit");
                startActivity(intent);
                break;
            case R.id.warn_rank:
                Intent intent1 = new Intent(getActivity(), WarnRankActivity.class);
                intent1.putExtra("flag", "warn");
                startActivity(intent1);
                break;
        }
    }
}
