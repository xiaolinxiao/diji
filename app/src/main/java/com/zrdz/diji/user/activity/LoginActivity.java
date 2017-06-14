package com.zrdz.diji.user.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.lzy.okhttputils.model.HttpParams;
import com.zrdz.diji.MyApplication;
import com.zrdz.diji.R;
import com.zrdz.diji.baseui.BaseBackTitleActivity;
import com.zrdz.diji.bean.UserInfo;
import com.zrdz.diji.callback.DialogCallback;
import com.zrdz.diji.utils.CommonUtils;
import com.zrdz.diji.utils.Const;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends BaseBackTitleActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private EditText edit_name;
    private EditText edit_pwd;
    private Button btn_sure;
    private CheckBox cb_remenber;
    private String nameStr = "";
    private String pwdStr = "";
    private boolean isSave = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgument(R.layout.activity_login, getString(R.string.login));
        initView();
    }

    private void initView() {
        edit_name = (EditText) findViewById(R.id.login_edit_name);
        edit_pwd = (EditText) findViewById(R.id.login_edit_pwd);
        btn_sure = (Button) findViewById(R.id.login_btn);
        cb_remenber = (CheckBox) findViewById(R.id.login_cb);
        btn_sure.setOnClickListener(this);

        cb_remenber.setOnCheckedChangeListener(this);
        nameStr = (String) CommonUtils.getSp(this, Const.USER_NAME, "");
        pwdStr = (String) CommonUtils.getSp(this, Const.USER_PWD, "");
        boolean isSave = (boolean) CommonUtils.getSp(this, Const.USER_ISREMENBER, false);
        cb_remenber.setChecked(isSave);
        Log.e("========", "namestr==" + nameStr + "==psw==" + pwdStr);
        edit_name.setText(nameStr);
        edit_pwd.setText(pwdStr);
        boolean isLoginAuto = (Boolean) CommonUtils.getSp(this, Const.USER_LOGINAUTO, false);
        if (isLoginAuto) {
            requestData();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.login_btn:
                nameStr = edit_name.getText().toString().trim();
                pwdStr = edit_pwd.getText().toString().trim();
                if (null == nameStr || "".equals(nameStr) || null == pwdStr || "".equals(pwdStr)) {
                    showToast(getString(R.string.pleaseinputagain));
                } else {
                    requestData();
                }
                break;
        }

    }

    /**
     * 登录请求
     */
    private void requestData() {
        HttpParams params = new HttpParams();
        params.put("username", nameStr);
        params.put("password", pwdStr);
        CommonUtils.httpGet(this, Const.MAIN_URL, Const.LOGIN_MODULE, params, new DialogCallback<UserInfo>(this, UserInfo.class) {

            @Override
            public void onResponse(boolean isFromCache, UserInfo userInfo, Request request, @Nullable Response response) {
                showToast(getString(R.string.loginsuccess));
                Log.e("userinfo", "==" + userInfo.toString());
                if (isSave) {
                    Log.e("USER_NAME", "===" + nameStr);
                    Log.e("USER_PWD", "===" + pwdStr);
                    CommonUtils.saveBySp(LoginActivity.this, Const.USER_LOGINAUTO, true);
                    CommonUtils.saveBySp(LoginActivity.this, Const.USER_ISREMENBER, isSave);
                    CommonUtils.saveBySp(LoginActivity.this, Const.USER_NAME, nameStr);
                    CommonUtils.saveBySp(LoginActivity.this, Const.USER_PWD, pwdStr);
                    CommonUtils.saveBySp(LoginActivity.this, Const.USER_PID, userInfo.pid);
                    CommonUtils.saveBySp(LoginActivity.this, Const.USER_CODE, userInfo.code);
                    CommonUtils.saveBySp(LoginActivity.this, Const.USER_ROLETYPE, userInfo.roleType);
                    CommonUtils.saveBySp(LoginActivity.this, Const.USER_USESRTYPE, userInfo.userType);
                    CommonUtils.saveBySp(LoginActivity.this, Const.USER_ID, userInfo.id);
                }
                MyApplication.user_loginname = userInfo.loginName;
                MyApplication.user_code = userInfo.code;
                MyApplication.user_name = userInfo.name;
                MyApplication.user_pid = userInfo.pid;
                MyApplication.user_roleType = userInfo.roleType;
                MyApplication.user_userType = userInfo.userType;
                MyApplication.user_id = userInfo.id;
                MyApplication.user_mobile = userInfo.mobile;
                MyApplication.isLogin = true;
                finish();
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                if (null != e)
                    showToast(getString(R.string.loginfail) + e.toString());
                else
                    showToast(getString(R.string.loginfail) + getString(R.string.connectfail));
            }
        });
    }

    /**
     * 记住密码账号
     *
     * @param compoundButton
     * @param isCheck
     */
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
        Log.e("onCheckedChanged", "======" + isCheck);
        isSave = isCheck;
    }
}
