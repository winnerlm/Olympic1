package com.example.wenhaibo.vister;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wenhaibo.olympic.R;
import com.example.wenhaibo.vister.weatherutil.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class PlayerJokeActivity extends AppCompatActivity {
    private Button button;
    private TextView textView;
    private List<App> jokes;
    private String content_1;
    //String responseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_joke);
        button= (Button) findViewById(R.id.btshow);
        textView=(TextView)findViewById(R.id.tvshow);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtil.sendOkHttpRequest("http://v.juhe.cn/joke/content/list.php?key=95afc54cc0bf09f40cf2e1e3880e42a3&page=2&pagesize=10&sort=asc&time=1418745237", new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData=response.body().string();
                        //
                         parseJSONWithJSONObject(responseData);


                        showResponse(content_1);

                        //parseJSONWithGSON(responseData);


                    }



                   private void parseJSONWithJSONObject(String responseData) {
                        try{
                            JSONObject jsonObject=new JSONObject(responseData);
                            JSONObject jsonObject1=jsonObject.getJSONObject("result");
                            JSONArray jsonArray=jsonObject1.getJSONArray("data");

                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject2=(JSONObject) jsonArray.get(i);
                                String  content=jsonObject.getString("content");
                                Log.e("Message",content);
                                String hashld =jsonObject.getString("hashld");
                                String unixtime=jsonObject.getString("unixtime");
                                String updatetime =jsonObject.getString("updatetime");
                                Log.e("123456",content);

                                content_1 = content;
                                App joke = new App();
                                joke.setContent(content);
                                joke.setHashld(hashld);
                                joke.setUnixtime(unixtime);
                                joke.setUpdatetime(updatetime);
                                jokes.add(joke);
                                Log.e("123456", String.valueOf(jokes.size()));





                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }



                    private void showResponse(final String content_1) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(content_1);

//
                            }
                        });
                    }
                });

            }
        });
    }
}
