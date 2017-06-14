package com.zrdz.diji.baseui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lzy.okhttputils.model.HttpParams;
import com.zrdz.diji.R;
import com.zrdz.diji.bean.NorResponseInfo;
import com.zrdz.diji.callback.DialogCallback;
import com.zrdz.diji.utils.CommonUtils;

import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 底部带listview基类
 */
public class BaseBottomListActivity extends BaseBackTitleActivity {

    public Button btn_query;
    public Button btn_reset;
    public ListView listView;//pulltorefresh得到的listview
    public ListView baseListView;//底部listview
    public LinearLayout ll;
    private boolean isVisible = false;//ll是否可见
    private boolean listViewIsVisible = false;//listview是否可见
    private boolean queryViewVisible = true;//查询控件是否可见
    public PullToRefreshListView pullToRefreshListView;
    public Spinner spinner_starttime;//告知时间选择

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 界面初始化
     * 子类需要调用该方法
     *
     * @param layoutId
     * @param title
     */
    public void initBaseBottomListArgument(int layoutId, String title) {
        initArgument(R.layout.activity_base_bottom_list, title);
        initBaseBottomListView(layoutId);
    }

    /**
     * 初始化控件，在传入的值不为0时，
     * 填充顶部界面
     */
    private void initBaseBottomListView(final int layoutId) {
        final LinearLayout ll_base = (LinearLayout) findViewById(R.id.baselist_ll);
        btn_query = (Button) findViewById(R.id.baselist_query);
        btn_reset = (Button) findViewById(R.id.baselist_reset);
        ll = (LinearLayout) findViewById(R.id.ll);
        baseListView = (ListView) findViewById(R.id.baselist_listview);
        spinner_starttime = (Spinner) findViewById(R.id.baselist_spinner_starttime);
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.baselist_pulltorefreshlistview);
        listView = pullToRefreshListView.getRefreshableView();
        if (layoutId != 0) {  //在oncreate、onstart、onresume时界面还没加载出来，此时去获取宽高为0
            final LinearLayout ll_content = (LinearLayout) findViewById(R.id.baselist_topcontent);
            View view = getLayoutInflater().inflate(layoutId, null);
            //手动去获取视图高度
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(w, h);
            int height = view.getMeasuredHeight();
            Log.e("==height==", "==" + height);
            //添加视图
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            ll_content.addView(view, params);
            //获取ll的初始可视状态
            if (isVisible) {
                ll.setVisibility(View.VISIBLE);
            }
            btn_hide.setVisibility(View.VISIBLE);
            btn_hide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int visibility = ll_content.getVisibility();
                    //这里除了正在试验的基桩查询和机顶盒异常告知模块，其他模块并没有告知这个控件
                    if (visibility == View.VISIBLE) {
                        ll_content.setVisibility(View.GONE);
                        ll_base.setVisibility(View.GONE);
                        if (isVisible)
                            ll.setVisibility(View.GONE);
                        btn_hide.setText(R.string.show);
                    } else {
                        ll_content.setVisibility(View.VISIBLE);
                        ll_base.setVisibility(View.VISIBLE);
                        if (isVisible)
                            ll.setVisibility(View.VISIBLE);
                        btn_hide.setText(R.string.hide);
                    }
                }
            });
        } else {
            ll.setVisibility(View.GONE);
            ll_base.setVisibility(View.GONE);
        }
        if (listViewIsVisible) {
            baseListView.setVisibility(View.VISIBLE);
            pullToRefreshListView.setVisibility(View.GONE);
        } else {
            baseListView.setVisibility(View.GONE);
            pullToRefreshListView.setVisibility(View.VISIBLE);
        }
        if (queryViewVisible) {
            ll_base.setVisibility(View.VISIBLE);
            btn_hide.setVisibility(View.VISIBLE);
        } else {
            ll_base.setVisibility(View.GONE);
            btn_hide.setVisibility(View.GONE);
        }
    }

    /**
     * 联网请求数据
     * 需要获取数据子类调用
     *
     * @param url      rul
     * @param urlModel 模块
     * @param params   传入参数
     */
    public void requestData(String url, String urlModel, HttpParams params) {
        CommonUtils.httpGet(this, url, urlModel, params, new DialogCallback<List<NorResponseInfo>>(this, NorResponseInfo.class) {
                    @Override
                    public void onResponse(boolean isFromCache, List<NorResponseInfo> norResponseInfos, Request request, @Nullable Response response) {
                        Log.e("==requestData==", "==" + response.toString());
                        upDateUi(norResponseInfos);
                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                        showToast(getString(R.string.datapostfail) + e.toString());
                    }
                }

        );
    }

    /**
     * 对ui更新
     */
    private void upDateUi(List list) {
    }

    /**
     * 設置时间告知控件是否可见
     *
     * @param isVisible
     */
    public void setLLVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    /**
     * 设置listview是否可见
     *
     * @param listViewIsVisible
     */
    public void setListViewVisible(boolean listViewIsVisible) {
        this.listViewIsVisible = listViewIsVisible;
    }

    /**
     * 设置查询控件是否可见
     *
     * @param isVisible
     */
    public void setQueryViewVisible(boolean isVisible) {
        this.queryViewVisible = isVisible;
    }
}
