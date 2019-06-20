package hotel;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.wenhaibo.olympic.R;
/**
 * Created by 刘明 on 2018/11/23.
 */
public class HotelActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
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
        webView.loadUrl("https://hotel.baidu.com/h5/detail.html?&uid=f878ad5bbb49cba7b020a8e3&industry=hotel&from=wise_card_accurate&src_from=wise_card_accurate&third_party=wise_card_accurate&word=%E5%BC%A0%E5%AE%B6%E5%8F%A3%E5%A8%81%E5%B0%BC%E6%96%AF%E5%A4%A7%E9%85%92%E5%BA%97&vmgdb=001001030000&vmgdb=001001030000#/");
    }
}