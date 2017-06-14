package com.zrdz.diji.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zrdz.diji.MyApplication;
import com.zrdz.diji.R;
import com.zrdz.diji.baseui.BaseFragment;
import com.zrdz.diji.listener.DialogListener;
import com.zrdz.diji.user.activity.AboutActivity;
import com.zrdz.diji.user.activity.LoginActivity;
import com.zrdz.diji.user.activity.UpdatePasswordActivity;
import com.zrdz.diji.utils.CommonUtils;
import com.zrdz.diji.utils.Const;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends BaseFragment implements View.OnClickListener {


    private View inflate;
    private TextView text_login;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null == inflate) {
            inflate = inflater.inflate(R.layout.fragment_user, container, false);
            initView();
        }
        return inflate;
    }

    private void initView() {
        text_login = (TextView) inflate.findViewById(R.id.user_tv_login);
        TextView text_updatepwd = (TextView) inflate.findViewById(R.id.user_tv_updatepwd);
        TextView text_about = (TextView) inflate.findViewById(R.id.user_tv_about);
        text_about.setOnClickListener(this);
        text_login.setOnClickListener(this);
        text_updatepwd.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.isLogin) {
            text_login.setText(R.string.logout);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_tv_updatepwd:
                if (MyApplication.isLogin) {
                    Intent intent2 = new Intent(getActivity(), UpdatePasswordActivity.class);
                    startActivity(intent2);
                } else {
                    showToast(getString(R.string.pleaseloginfirst));
                }
                break;
            case R.id.user_tv_login:
                if (MyApplication.isLogin) {
                    AlertDialog dialog = CommonUtils.createDialog(getActivity(), getString(R.string.prompt), getString(R.string.logoutnow), new DialogListener() {
                        @Override
                        public void isSure(DialogInterface dialog) {
                            dialog.dismiss();
                            updateUi();
                        }

                        @Override
                        public void isCannel(DialogInterface dialog) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } else {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent1);
                }
                break;
            case R.id.user_tv_about:
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
                break;
        }

    }

    /**
     * 对ui更新
     */
    private void updateUi() {
        text_login.setText(getString(R.string.login));
        MyApplication.isLogin = false;
        CommonUtils.saveBySp(getActivity(), Const.USER_LOGINAUTO, false);
    }
}
