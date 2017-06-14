package com.zrdz.diji.home.testmanage.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zrdz.diji.R;
import com.zrdz.diji.baseui.BaseBackTitleActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 信息提交界面
 */
public class InfoSubmitActivity extends BaseBackTitleActivity implements View.OnClickListener {

    private String pileno;//桩号
    private TextView text_pileno;
    private TextView text_time;
    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String testtype = getIntent().getStringExtra("testtype");
        pileno = getIntent().getStringExtra("pileno");
        String title = "";
        if ("1".equals(testtype)) {
            title = getString(R.string.topausetest);
        } else {
            title = getString(R.string.tostoptest);
        }
        initArgument(R.layout.activity_info_submit, title);
        initView();
    }

    private void initView() {
        text_pileno = (TextView) findViewById(R.id.infosub_text_pileno);
        text_time = (TextView) findViewById(R.id.infosub_text_time);
        edit = (EditText) findViewById(R.id.infosub_edit);
        Button btn_sub = (Button) findViewById(R.id.infosub_btn_sub);
        Button btn_cancle = (Button) findViewById(R.id.infosub_btn_cancle);

        btn_sub.setOnClickListener(this);
        btn_cancle.setOnClickListener(this);
        text_pileno.setText(pileno);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date(System.currentTimeMillis());
        String timeStr = format.format(currentDate);
        text_time.setText(timeStr);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.infosub_btn_cancle:
                finish();
                break;
            case R.id.infosub_btn_sub:
                String pileNo = text_pileno.getText().toString().trim();
                String timeStr = text_time.getText().toString().trim();
                String whyStr = edit.getText().toString().trim();
                if ("".equals(whyStr)) {
                    showToast(getString(R.string.nonullwhy));
                }else{

                }
                break;
        }
    }
}
