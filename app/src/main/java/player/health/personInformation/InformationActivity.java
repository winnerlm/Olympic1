package player.health.personInformation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.wenhaibo.olympic.R;

import java.util.List;

import player.PlayerActivity;
import player.health.personInformation.zhexian.HandlerActivity;

public class InformationActivity extends Activity implements View.OnClickListener{
    private RelativeLayout rl_personInfo;
    private ListView lv_rate_record;
    private List<Person> persons;
    public int MID;
    private  Information_SQL_adapter Sql = new Information_SQL_adapter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_activity_information);

        //健康统计图
        Button btn_quxian=(Button)findViewById(R.id.btn_quxian);
        btn_quxian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InformationActivity.this, HandlerActivity.class);
                startActivity(intent);
            }
        });

        InUi();
        InEvent();


    }
    private void InUi() {
        rl_personInfo = (RelativeLayout)findViewById(R.id.rl_personInfo);
        lv_rate_record = (ListView)findViewById(R.id.lv_rate_record);
    }



    private void InEvent() {
        //上面个人信息点击事件
        rl_personInfo.setOnClickListener(this);

        //list适配
        Information_list_adapter MyAdapter = new Information_list_adapter(this);
        lv_rate_record.setAdapter(MyAdapter);

        //获取所有数据
        persons = Sql.findAll();

        //list长按事件
        lv_rate_record.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("操作");
                menu.add(0, 1, 0, "删除");
            }
        });

    }


    //给菜单项添加事件
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        MID = info.position;

        Log.e("gaoyu","+MID的值是"+MID);
        int list_id = persons.get(MID).getId();
        Log.i("高宇", "ID :" + 66 + "arg" + list_id);
        switch (item.getItemId()) {
            case 1:
                // 删除操作
                Sql.delete(list_id);
                persons = Sql.findAll();
                Information_list_adapter MyAdapter = new Information_list_adapter(this);
                lv_rate_record.setAdapter(MyAdapter);

            default:
                break;
        }

        return super.onContextItemSelected(item);
    }


    //跳转详细信息
    @Override
    public void onClick(View v) {
        switch (v.getId()){
                    case R.id.rl_personInfo:
                        Intent i2=new Intent(InformationActivity.this,DetailedPersonActivity.class);
                        startActivity(i2);
                        break;

                }

    }
    //退出保存
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //确认对话框  退出
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i2 = new Intent(InformationActivity.this, PlayerActivity.class);
            startActivity(i2);
            finish();

        }
        return super.onKeyDown(keyCode, event);
    }

}
