package com.zrdz.diji.message.base;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.zrdz.diji.R;
import com.zrdz.diji.baseui.BaseBottomListActivity;

/**
 * 排名界面基类
 */
public class BaseRankActivity extends BaseBottomListActivity implements AdapterView.OnItemSelectedListener {
    public TextView text_time;
    public Button btn_rank_query;
    public Spinner spinner;
    public String quarter = "1";//季度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListViewVisible(true);
        setQueryViewVisible(false);
        setLLVisible(false);
        initBaseView();
    }

    private void initBaseView() {
        String flag = getIntent().getStringExtra("flag");
        String title = "";
        if ("credit".equals(flag)) {
            title = getString(R.string.credit_rank);
        } else {
            title = getString(R.string.warn_rank);
        }
        initBaseBottomListArgument(R.layout.activity_rank, title);
        text_time = (TextView) findViewById(R.id.rank_text_time);
        btn_rank_query = (Button) findViewById(R.id.rank_btn_query);
        spinner = (Spinner) findViewById(R.id.rank_spinner_type);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                quarter = "1";
                break;
            case 1:
                quarter = "2";
                break;
            case 2:
                quarter = "3";
                break;
            case 3:
                quarter = "4";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
