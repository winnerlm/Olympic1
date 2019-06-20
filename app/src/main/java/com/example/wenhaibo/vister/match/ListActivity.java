package com.example.wenhaibo.vister.match;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wenhaibo.olympic.R;
import com.example.wenhaibo.vister.FunctionActivity;

/**
 * Created by 刘明 on 2019/4/18.
 */
public class ListActivity extends Activity {
    private ListView LV;
    private MyAdapter MyAdapter;
    private ImageView IV;
    private TextView TV;


    private String tet[]={"短道速滑","速度滑冰","花样滑冰","冰球","冰壶","俯式冰橇","自由式滑雪","单板滑雪","无舵雪橇","有舵雪橇","高山滑雪","跳台滑雪","越野滑雪","北欧两项","冬季两项",};
    private int[] icon={R.drawable.u,R.drawable.u1,R.drawable.u2,R.drawable.u3,R.drawable.u4,R.drawable.u5,R.drawable.u6,R.drawable.u7,R.drawable.u8,R.drawable.u9,R.drawable.u10,R.drawable.u11,R.drawable.u12,R.drawable.u13,R.drawable.u14};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        LV=(ListView)findViewById(R.id.LV);
        LV.setAdapter(new MyAdapter());

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Back:{
                Intent intent=new Intent(ListActivity.this,FunctionActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.refresh:{
                Intent intent=new Intent(ListActivity.this,ListActivity.class);
                startActivity(intent);
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *
     */
    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return icon.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=View.inflate(ListActivity.this,R.layout.items,null);
            ImageView  IV=(ImageView) view.findViewById(R.id.IV);
            IV.setBackgroundResource(icon[position]);
            //TV.setBackgroundResource(Integer.parseInt(tet[position]));
            TV=(TextView)view.findViewById(R.id.TV);
            TV.setText(tet[position]);

            return view;
        }
    }
    long firstTime = 0;
    //双击退出
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_BACK){
            long secondTime = System.currentTimeMillis();
            if (secondTime-firstTime>2000){
                Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
                return true;
            }else {
                finish();
            }
        }
        return super.onKeyUp(keyCode, event);
    }

}
