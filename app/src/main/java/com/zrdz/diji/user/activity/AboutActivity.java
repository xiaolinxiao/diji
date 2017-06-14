package com.zrdz.diji.user.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zrdz.diji.R;
import com.zrdz.diji.baseui.BaseBackTitleActivity;
import com.zrdz.diji.utils.CommonUtils;

/**
 * 关于界面
 */
public class AboutActivity extends BaseBackTitleActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgument(R.layout.activity_about, getString(R.string.aboutwe));
        initView();
    }

    private void initView() {
        TextView text_update = (TextView) findViewById(R.id.about_text_update);
        text_update.setOnClickListener(this);
        TextView text_version = (TextView)findViewById(R.id.about_text_version);
        String versionName = CommonUtils.getVersionName(this);
        text_version.setText("v"+versionName);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.about_text_update:
                //TODO 更新操作

                break;
        }
    }
}
