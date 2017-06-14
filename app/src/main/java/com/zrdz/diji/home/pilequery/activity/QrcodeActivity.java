package com.zrdz.diji.home.pilequery.activity;

import android.os.Bundle;

import com.zrdz.diji.R;
import com.zrdz.diji.baseui.BaseBackTitleActivity;

/**
 * 二维码检索
 */
public class QrcodeActivity extends BaseBackTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgument(R.layout.activity_qrcode, getString(R.string.qrcode));
    }
}
