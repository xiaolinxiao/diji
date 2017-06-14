package com.zrdz.diji.home.testmanage.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.zrdz.diji.MyApplication;
import com.zrdz.diji.R;
import com.zrdz.diji.home.base.BasePileInfoActivity;
import com.zrdz.diji.utils.CommonUtils;
import com.zrdz.diji.utils.LinkServiceUtils;
import com.zrdz.diji.utils.LogUtils;

/**
 * 基桩详情
 */
public class PileInfoDetailActivity extends BasePileInfoActivity {

    private String managetype;
    private String pid;
    private String result;
    private ProgressDialog progressDialog;
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    String message = (String) msg.obj;
                    showToast(getString(R.string.getdatafail) + message);
                    if (null != progressDialog)
                        progressDialog.dismiss();
                    break;
                case -1:
                    showToast(getString(R.string.getdatafail));
                    if (null != progressDialog)
                        progressDialog.dismiss();
                    break;
                case 1://图片地址请求成功
                    if (null != progressDialog)
                        progressDialog.dismiss();
                    Intent intent = new Intent(PileInfoDetailActivity.this, (Class<?>) msg.obj);
                    intent.putExtra("photopath", result);
                    intent.putExtra("pid", pid);
                    startActivity(intent);
                    break;
                case 2:
                    progressDialog.dismiss();
                    showToast(getString(R.string.nopic));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pid = getIntent().getStringExtra("pid");
        LogUtils.e("==pid==" + pid);
        String startDate = getIntent().getStringExtra("startDate");
        managetype = getIntent().getStringExtra("managetype");
        initBaseArgument(pid, managetype, startDate);
    }

    private void initData(final Class<?> cls) {
        progressDialog = CommonUtils.initProgressDialog(this);
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                LinkServiceUtils linkServiceUtils = new LinkServiceUtils();
                result = linkServiceUtils.linkqueryPhotoPath(PileInfoDetailActivity.this, pid);
                if (null == result) {
                    myHandler.sendEmptyMessage(-1);
                } else if ("".equals(result)) {
                    myHandler.sendEmptyMessage(2);
                } else {
                    Message message = new Message();
                    message.obj = cls;
                    message.what = 1;
                    myHandler.sendMessage(message);
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pileinfo_btn_test:
                if ("1".equals(managetype)) {//开始试验查看
                    if ("0".equals(experiment_state)) {
                        showToast(getString(R.string.stoptestfirst));
                    } else if ("1".equals(experiment_state)) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String result = CommonUtils.checkUser(MyApplication.user_mobile, pid);
                                if ("true".equals(result)) {
                                    //TODO 进入重新开始试验界面
                                    startActivity(new Intent(PileInfoDetailActivity.this, StartTestActivity.class));
                                } else if (null == result) {
                                    myHandler.sendEmptyMessage(-1);
                                } else if ("false".equals(result)) {
                                    Message msg = new Message();
                                    msg.what = 0;
                                    msg.obj = getString(R.string.exp_no_permission);
                                    myHandler.sendMessage(msg);
                                    return;
                                } else {
                                    Message msg = new Message();
                                    msg.what = 0;
                                    msg.obj = result;
                                    myHandler.sendMessage(msg);
                                }
                            }
                        }).start();
                    }
                } else if ("2".equals(managetype)) {//中止试验查看
                    if ("0".equals(experiment_state)) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String result = CommonUtils.checkUser(MyApplication.user_mobile, pid);
                                if ("true".equals(result)) {
                                    Intent intent = new Intent(PileInfoDetailActivity.this, InfoSubmitActivity.class);
                                    intent.putExtra("testtype", testtype);
                                    intent.putExtra("pileno", pile_id);
                                    startActivity(intent);
                                } else if (null == result) {
                                    myHandler.sendEmptyMessage(-1);
                                } else {
                                    Message msg = new Message();
                                    msg.what = 0;
                                    msg.obj = getString(R.string.exp_no_permission);
                                    myHandler.sendMessage(msg);
                                }
                            }
                        }).start();
                    } else {
                        showToast(getString(R.string.alreadypaus));
                    }
                }
                break;
            case R.id.pileinfo_text_lookpic://进入查看图片界面
                initData(LookPicActivity.class);
                break;
            case R.id.pileinfo_btn_fillpic://图片补传
                initData(FillPicActivity.class);
                break;
            case R.id.pileinfo_text_lookweight:
                Intent intent = new Intent(this, ShowWeightActivity.class);
                intent.putExtra("pid", pid);
                intent.putExtra("flag", "look");
                startActivity(intent);
                break;
        }
    }
}
