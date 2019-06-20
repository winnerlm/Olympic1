package com.example.wenhaibo.vister.buy.TeChan;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.wenhaibo.olympic.R;

/**
 * Created by 刘明 on 2019/4/19.
 */
public class TeChanActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_techan);
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String
                    url) {
                view.loadUrl(url); // 根据传入的参数再去加载新的网页
                return true; // 表示当前WebView可以处理打开新网页的请求，不用借助
            }
        });
        webView.loadUrl("https://www.xiangha.com/techan/zhangjiakoushi?xhdl=sogou_aladdin");
    }
}