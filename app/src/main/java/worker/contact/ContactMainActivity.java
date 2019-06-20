package worker.contact;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wenhaibo.olympic.R;

import java.util.List;

import worker.WorkerActivity;


public class ContactMainActivity extends Activity implements View.OnClickListener {
    private ListView lv_contact;
    private Button btn_addcontact,btn_else_addcontact;
    private List<Person> persons;
    private Person person1;
    private Change change;
    public int MID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_activity_main_contact);
        change = new Change(this);
        persons = change.findAll();

        lv_contact = (ListView) findViewById(R.id.lv_contact);
        btn_addcontact = (Button) findViewById(R.id.btn_addcontact);
        btn_else_addcontact = (Button)findViewById(R.id.btn_else_addcontact);
        //设置监听和适配器
        btn_else_addcontact.setOnClickListener(this);
        btn_addcontact.setOnClickListener(this);
        lv_contact.setAdapter(new MyAdapter());


        //为listview添加点击事件
        //轻点
        lv_contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                int id = persons.get(arg2).getId();
                person1 = change.find(id);

                String number = person1.getNumber().toString().trim();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + number));
                startActivity(intent);
                Toast.makeText(ContactMainActivity.this, "拨打电话", Toast.LENGTH_SHORT).show();


            }
        });

        //添加长按点击
        lv_contact.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("选择操作");
                menu.add(0, 0, 0, "编辑");
                menu.add(0, 1, 0, "删除");
            }
        });
    }

    //给菜单项添加事件
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
         /*在这里MID意思是  获取到当前listview 表里面的位置 如果直接进行change.delete(position);
        只是将表的一行删掉了行顺序由123456变成23456 但是虽然删掉了但是没删掉的表的顺序
        但是数据库里面的id却从123456 变成 12345 所以会出现跳行删除以及删除不了现象*/
        MID = info.position;
        int list_id = persons.get(MID).getId();
        Log.i("高宇", "ID :" + 66 + "arg" + list_id);
        switch (item.getItemId()) {
            case 0:
                //编辑操作
                //  int id = persons.get(arg2).getId();
                Intent intent = new Intent();
                //传值将获取的id传过去
                intent.putExtra("extra", String.valueOf(list_id));
                intent.setClass(ContactMainActivity.this, AddContactActivity.class);
                startActivity(intent);
                finish();
                break;

            case 1:
                // 删除操作
                change.delete(list_id);
                persons = change.findAll();
                lv_contact.setAdapter(new MyAdapter());

                Log.i("高宇", "ID :" + 79 + "arg" + list_id);
                Toast.makeText(ContactMainActivity.this, "联系人已删除", Toast.LENGTH_SHORT).show();
            default:
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addcontact:
                Intent i2 = new Intent(ContactMainActivity.this, AddContactActivity.class);
                startActivity(i2);
                break;
            case R.id.btn_else_addcontact:
                Intent i3 = new Intent(ContactMainActivity.this, ElseContactActivity.class);
                startActivity(i3);
                break;
        }


    }

    //listview适配器
    private class MyAdapter extends BaseAdapter {
        private static final String TAG = "MyAdapter";

        @Override
        public int getCount() {
            return persons.size();//条目个数
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
            //得到某位置对应的person对象
            Person person = persons.get(position);
            View view = View.inflate(ContactMainActivity.this, R.layout.contact_list, null);
            //在view对象中寻找孩子的id
            /*TextView tv_id = (TextView) view.findViewById(R.id.tv_id);
            tv_id.setText("No:" + person.getId());*/

            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_name.setText("姓名:" + person.getName());

            TextView tv_number = (TextView) view.findViewById(R.id.tv_number);
            tv_number.setText("电话:" + person.getNumber());
            return view;


        }
    }

    @Override
    protected void onResume() {
        //
        persons = change.findAll();
        lv_contact.setAdapter(new MyAdapter());
        super.onResume();
    }

    //退出保存
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //确认对话框  退出
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i2 = new Intent(ContactMainActivity.this, WorkerActivity.class);
            startActivity(i2);
            finish();

        }
        return super.onKeyDown(keyCode, event);
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
