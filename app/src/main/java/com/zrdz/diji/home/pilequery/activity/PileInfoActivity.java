package com.zrdz.diji.home.pilequery.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.zrdz.diji.R;
import com.zrdz.diji.baseui.BaseActivity;
import com.zrdz.diji.home.pilequery.fragment.DistimeFragment;
import com.zrdz.diji.home.pilequery.fragment.PileinfoFragment;
import com.zrdz.diji.home.pilequery.fragment.QSFragment;
import com.zrdz.diji.home.pilequery.fragment.TestInfoFragment;

/**
 * 桩点信息界面
 */
public class PileInfoActivity extends BaseActivity {
    private BottomBar mBottomBar;
    private FragmentTransaction transaction;
    private PileinfoFragment pileinfoFragment;
    private TestInfoFragment testInfoFragment;
    private QSFragment qsFragment;
    private DistimeFragment distimeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomBar = BottomBar.attach(this, savedInstanceState);
//        // 千万注意：这个方法要用在 setItemsFromMenu 之前，也就是tab还没有设置之前要先调用，不然会报错
        mBottomBar.noTabletGoodness();

        mBottomBar.setItems(R.menu.bottombar_pileinfo);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                switch (menuItemId) {
                    case R.id.pile_dot_info:
                        if (null == pileinfoFragment) {
                            pileinfoFragment = new PileinfoFragment();
                        }
                        replaceFragment(pileinfoFragment);
                        break;
                    case R.id.test_info:
                        if (null == testInfoFragment) {
                            testInfoFragment = new TestInfoFragment();
                        }
                        replaceFragment(testInfoFragment);
                        break;
                    case R.id.diagram:
                        if (null == qsFragment) {
                            qsFragment = new QSFragment();
                        }
                        replaceFragment(qsFragment);
                        break;
                    case R.id.displacement_time:
                        if (null == qsFragment) {
                            distimeFragment = new DistimeFragment();
                        }
                        replaceFragment(distimeFragment);
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
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        transaction.replace(R.id.main_fragment_layout, fragment);
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存BottomBar的状态
        mBottomBar.onSaveInstanceState(outState);
    }
}
