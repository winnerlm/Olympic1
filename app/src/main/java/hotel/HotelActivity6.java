package hotel;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.wenhaibo.olympic.R;

/**
 * Created by 刘明 on 2019/4/26.
 */
public class HotelActivity6 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel5);
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
        webView.loadUrl("https://m.elong.com/hotel/?semwp=common&ref=tymbaidu&semtcid_exact=69a12e27-52dd-402a-8235-6bfa015c6beb&semtcid=b858e9a4-9080-448a-bc45-b88f8c000189&dlbshow=1");
    }
}
