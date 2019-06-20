package hotel.main.Information;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wenhaibo.olympic.R;

import java.util.List;

/**
 * Created by 刘明 on 2019/4/26.
 */
public class CheckInformation extends Activity implements View.OnClickListener {

private Button ibAdd,ibSearch;
private EditText etSearch;
private ListView lvItems;
private List<Bean> beans;
private Dao d;


@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_check);

        d = new Dao(this);
        beans = d.findAll();

        ibAdd = (Button) findViewById(R.id.ibAdd);
        //ibSearch = (Button) findViewById(R.id.ibSearch);
        lvItems = (ListView) findViewById(R.id.lvItems);
       // etSearch = (EditText) findViewById(R.id.etSearch);


        ibAdd.setOnClickListener(this);
       // ibSearch.setOnClickListener(this);

        lvItems.setAdapter((ListAdapter) new MyAdapter());

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
@ Override
public boolean onItemLongClick(AdapterView<?> arg0, View view, final int position, long arg3) {
        int id_1 = beans.get(position).getId();
        d.delete(id_1);
        beans = d.findAll();
        lvItems.setAdapter((ListAdapter) new MyAdapter());
        return true;
        }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

public void onItemClick(AdapterView<?> arg0, View arg1, int item,
        long position) {

        int id = beans.get(item).getId();
        Intent intent = new Intent(CheckInformation.this, FillInformation.class);
        intent.putExtra("item", String.valueOf(id));
        startActivity(intent);
        finish();

        }
        });

        ibAdd.setOnClickListener( new View.OnClickListener() {
@Override
public void onClick(View v) {
        Intent i = new Intent(CheckInformation.this,FillInformation.class);
        startActivity(i);
        }
        });

        }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

/*@Override
public void onClick(View v) {
        String str = etSearch.getText().toString();

        switch (v.getId()){
        case R.id.ibSearch:
        if(str != null) {
        Intent i = new Intent(CheckInformation.this, FillInformation.class);
        i.putExtra("search", str);
        startActivity(i);
        }
        else{
        Toast.makeText(CheckInformation.this,"没有查询到相关内容", Toast.LENGTH_SHORT).show();
        }
        }
        }
*/
    /*
    适配器：listview 和数据库之间
     */

private class MyAdapter extends BaseAdapter {

        /*
        控制listview中一共有多少个条目
         */

    @Override
    public int getCount() {
        return beans.size();//条目个数 == 集合的size
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
        Bean bean = beans.get(position);
        View view = View.inflate(CheckInformation.this,R.layout.list_item, null);
        TextView tvContent = (TextView) view.findViewById(R.id.tvContent);
        tvContent.setText("客户姓名:" + bean.getContent());

        TextView tvTittle = (TextView) view.findViewById(R.id.tvTittle);
        tvTittle.setText("备注:" + bean.getName());
        return view;

    }
}

    @Override
    protected void onResume() {
        beans = d.findAll();
        lvItems.setAdapter(new MyAdapter());
        super.onResume();
    }
    /*
    右上角菜单使用
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu,menu);//这里是调用menu文件夹中的main.xml，在登陆界面label右上角的三角里显示其他功能
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_add:
                Intent i = new Intent(CheckInformation.this,FillInformation.class);
                startActivity(i);
                return true;
        }

        return super.onOptionsItemSelected(item);
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

