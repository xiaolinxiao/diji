package com.zrdz.diji.home.testmanage.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zrdz.diji.MyApplication;
import com.zrdz.diji.R;
import com.zrdz.diji.baseui.BaseBottomListActivity;
import com.zrdz.diji.utils.LinkServiceUtils;

/**
 * 开始试验
 */
public class StartTestActivity extends BaseBottomListActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgument(R.layout.activity_start_test, getString(R.string.starttest));
        initView();
    }

    private void initView() {
        Button btn_sure = (Button) findViewById(R.id.start_btn_sure);
        Button btn_cancle = (Button) findViewById(R.id.start_btn_cancel);
        btn_cancle.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_btn_sure:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LinkServiceUtils linkServiceUtils = new LinkServiceUtils();
                        String result = linkServiceUtils.linkstartAndConfirmPile(StartTestActivity.this, MyApplication.user_code, "");


                    }
                }).start();
                break;
            case R.id.start_btn_cancel:
                finish();
                break;
        }
    }
}
