package com.zrdz.diji.home.pilequery.activity;

import android.os.Bundle;

import com.zrdz.diji.R;
import com.zrdz.diji.home.base.Base1Activity;
import com.zrdz.diji.utils.Const;

/**
 * 未备案界面
 */
public class NoRecordActivity extends Base1Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBase1View(getString(R.string.notrecordpileno), Const.FLAG_NORECORD);
    }

}
