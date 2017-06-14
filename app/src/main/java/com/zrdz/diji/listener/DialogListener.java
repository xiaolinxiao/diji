package com.zrdz.diji.listener;

import android.content.DialogInterface;

/**
 * Created by ASUS on 2017/4/17.
 * <p>
 * dialog点击监听
 */

public interface DialogListener {
    public void isSure(DialogInterface dialogInterface);

    public void isCannel(DialogInterface dialog);
}
