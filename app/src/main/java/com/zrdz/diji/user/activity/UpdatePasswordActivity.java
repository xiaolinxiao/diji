package com.zrdz.diji.user.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lzy.okhttputils.model.HttpParams;
import com.zrdz.diji.MyApplication;
import com.zrdz.diji.R;
import com.zrdz.diji.baseui.BaseBackTitleActivity;
import com.zrdz.diji.callback.DialogCallback;
import com.zrdz.diji.utils.CommonUtils;
import com.zrdz.diji.utils.Const;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class UpdatePasswordActivity extends BaseBackTitleActivity implements View.OnClickListener {


    private EditText edit_oldpsd;
    private EditText edit_newpsw;
    private EditText edit_newpswagain;
    private String oldStr;
    private String newStr;
    private String newagainStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgument(R.layout.activity_update_password, getString(R.string.updatepwd));
        initView();
    }

    private void initView() {
        edit_oldpsd = (EditText) findViewById(R.id.edit_oldpwd);
        edit_newpsw = (EditText) findViewById(R.id.edit_newpwd);
        edit_newpswagain = (EditText) findViewById(R.id.edit_newpwdagain);
        Button btn_sure = (Button) findViewById(R.id.update_sure);
        btn_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        oldStr = edit_oldpsd.getText().toString().trim();
        newStr = edit_newpsw.getText().toString().trim();
        newagainStr = edit_newpswagain.getText().toString().trim();
        Log.e("==update==", "==" + oldStr + "===" + newStr + "===" + newagainStr);
        if (oldStr == null || oldStr.equals("") || newStr == null || newStr.equals("") || newagainStr == null || newagainStr.equals("")) {
            showToast(getString(R.string.errorinput));
        } else {
            if (newStr.equals(newagainStr)) {
                updatePwdAction();
            } else {
                showToast(getString(R.string.newpswnodifferent));
            }
        }
    }

    /**
     * 修改密码
     */
    private void updatePwdAction() {
        HttpParams params = new HttpParams();
        params.put("id", MyApplication.user_id);
        params.put("oldPassword", oldStr);
        params.put("newPassword", newStr);
        CommonUtils.httpGet(this, Const.MAIN_URL, Const.UPDATEPWD_MODULE, params, new DialogCallback<String>(this, String.class) {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                showToast("" + s);
                finish();
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                showToast(e.getMessage());
            }
        });
    }
}
