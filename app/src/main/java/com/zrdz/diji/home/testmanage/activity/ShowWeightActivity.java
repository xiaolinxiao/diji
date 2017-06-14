package com.zrdz.diji.home.testmanage.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.zrdz.diji.R;
import com.zrdz.diji.baseui.BaseActivity;
import com.zrdz.diji.utils.LinkServiceUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 压重堆载界面
 */
public class ShowWeightActivity extends BaseActivity implements View.OnClickListener {
    private WebView mWebView;
    private String pid;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case -1:
                    break;
                case 0:
                    break;
                case 1:
                    String data = (String) msg.obj;
                    mWebView.loadUrl("javascript:renderNow('" + data + "')");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_weight);
        initView();
    }

    private void initView() {
        pid = getIntent().getStringExtra("pid");
        String flag = getIntent().getStringExtra("flag");
        mWebView = (WebView) findViewById(R.id.webView_table);
        Button btn = (Button) findViewById(R.id.button_save);
        if ("look".equals(flag)) {
            btn.setText(getString(R.string.back));
        }
        btn.setOnClickListener(this);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
//        mWebView.addJavascriptInterface(new WebviewInterface(), "android");
        // 限制在WebView中打开网页，而不用默认浏览器
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Thread postTask = new Thread(new postTableTask());
                postTask.start();
            }
        });
        mWebView.loadUrl("file:///android_asset/table_show.html");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_save:
                finish();
                break;
        }
    }

    class postTableTask implements Runnable {

        public void run() {
            LinkServiceUtils linkWebSerVice = new LinkServiceUtils();
            String result = linkWebSerVice.queryJZ_WRecord(pid);
            if (null == result) {
                handler.sendEmptyMessage(0);
            } else {
                try {
                    JSONObject obj = new JSONObject(result);
                    String data = obj.getString("toollist");
                    if (getString(R.string.nodata).equals(data)) {
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = data;
                        handler.sendMessage(msg);
                    } else {
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = data;
                        handler.sendMessage(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
