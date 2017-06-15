package com.zrdz.diji;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.zrdz.diji.baseui.BaseActivity;
import com.zrdz.diji.fragment.HomeFragment;
import com.zrdz.diji.fragment.MessageFragment;
import com.zrdz.diji.fragment.UserFragment;
import com.zrdz.diji.user.activity.LoginActivity;
import com.zrdz.diji.utils.CommonUtils;
import com.zrdz.diji.utils.Const;
import com.zrdz.diji.utils.LogUtils;
/**
 *界面入口
 */
public class MainActivity extends BaseActivity {

    private BottomBar mBottomBar;
    private HomeFragment homeFragment;
    private UserFragment userFragment;
    private MessageFragment messageFragment;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mBottomBar = BottomBar.attach(this, savedInstanceState);
//        // 千万注意：这个方法要用在 setItemsFromMenu 之前，也就是tab还没有设置之前要先调用，不然会报错
        mBottomBar.noTabletGoodness();

        mBottomBar.setItems(R.menu.bottombar_menu);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                switch (menuItemId) {
                    case R.id.bottomBarItem0:
                        if (null == homeFragment) {
                            homeFragment = new HomeFragment();
                        }
                        replaceFragment(homeFragment);
                        break;
                    case R.id.bottomBarItem1:
                        if (null == userFragment) {
                            userFragment = new UserFragment();
                        }
                        replaceFragment(userFragment);
                        break;
                    case R.id.bottomBarItem2:
                        if (null == messageFragment) {
                            messageFragment = new MessageFragment();
                        }
                        replaceFragment(messageFragment);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                //重选事件，当前已经选择了这个，又点了这个tab。首页刷新页面
            }
        });

        //添加初始Fragment
//        defaultFragment(null == mMainFragment ? mMainFragment = MainFragment.newInstance() : mMainFragment);
        // 当点击不同按钮的时候，设置不同的颜色
        // 可以用以下三种方式来设置颜色.测试后发现，只有在数量大于等于4的时候，颜色才会有出现
//        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorPrimary));
//        mBottomBar.mapColorForTab(1, ContextCompat.getColor(this, R.color.colorAccent));
//        mBottomBar.mapColorForTab(2, ContextCompat.getColor(this, R.color.green));
        boolean isAuto = (boolean) CommonUtils.getSp(this, Const.USER_LOGINAUTO, false);
        if (isAuto) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        transaction.replace(R.id.main_fragment_layout, fragment);
//        transaction.hide();
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存BottomBar的状态
        mBottomBar.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        LogUtils.e("==onStop==");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        LogUtils.e("==onDestroy==");
        super.onDestroy();
    }
}
