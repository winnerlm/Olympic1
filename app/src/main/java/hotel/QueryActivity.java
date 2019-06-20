package hotel;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.wenhaibo.olympic.R;

import java.util.Calendar;

public class QueryActivity extends Activity {
    private Spinner hspinner;
    private Button sub_button;
    private EditText et_date;
    private Calendar calendar;
    private int mYear;
    private int mMonth;
    private int mDay;
    private ArrayAdapter hadapter;
    private String path = null;
    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query1);
        savehosinfo.saveHospitalInfo(this);
        hspinner = (Spinner) findViewById(R.id.spinner);
        sub_button = (Button) findViewById(R.id.sub_button);
        et_date = (EditText) findViewById(R.id.et_date);

        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(QueryActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int month, int day) {
                                // TODO Auto-generated method stub
                                mYear = year;
                                mMonth = month;
                                mDay = day;
                                // 更新EditText控件日期 小于10加0
                                et_date.setText(new StringBuilder()
                                        .append(mYear)
                                        .append("-")
                                        .append((mMonth + 1) < 10 ? "0"
                                                + (mMonth + 1) : (mMonth + 1))
                                        .append("-")
                                        .append((mDay < 10) ? "0" + mDay : mDay));
                            }

                        },mYear ,mMonth ,mDay);
                datePickerDialog.show();

            }
        });
        hadapter = ArrayAdapter.createFromResource(this, R.array.lm, android.R.layout.simple_spinner_item);
        //设置下拉列表的风格
        hadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter2 添加到spinner中
        hspinner.setAdapter(hadapter);
        //添加事件Spinner事件监听

        hspinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 1:
                        sub_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(QueryActivity.this,HotelActivity.class);
                                startActivity(intent);
                            }
                        });
                        break;
                    case 2:
                        sub_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(QueryActivity.this,HotelActivity1.class);
                                startActivity(intent);
                            }
                        });
                        break;
                    case 3:
                        sub_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(QueryActivity.this,HotelActivity2.class);
                                startActivity(intent);
                            }
                        });
                        break;
                    case 4:
                        sub_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(QueryActivity.this,HotelActivity3.class);
                                startActivity(intent);
                            }
                        });
                        break;
                    case 5:
                        sub_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(QueryActivity.this,HotelActivity4.class);
                                startActivity(intent);
                            }
                        });
                        break;
                    case 6:
                        sub_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(QueryActivity.this,HotelActivity5.class);
                                startActivity(intent);
                            }
                        });
                        break;
                    default:
                        sub_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(QueryActivity.this,HotelActivity6.class);
                                startActivity(intent);
                            }
                        });
                        break;
                }
               /* Intent intent=new Intent(QueryActivity.this,HotelActivity.class);
                startActivity(intent);
                Toast.makeText(QueryActivity.this, "您预约的是:" + adapterView.getItemAtPosition(i), Toast.LENGTH_LONG).show();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private class HosItemSelectedListener implements OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            String[] hospitals = getResources().getStringArray(R.array.lm);
//            Toast.makeText(MainActivity.this, "您预约的是:" + hospitals[pos], Toast.LENGTH_LONG).show();
            switch (hospitals[pos]) {
                case "张家口威尼斯大酒店":
                    path = "https://www.hbbfyfy.com/index.html";
                    break;
                case "张家口国际大酒店":
                    path = "http://www.251yy.com.cn/";
                    break;
                case "张家口容辰国际假日酒店":
                    path = "http://www.zjkdyyy.com/";
                    break;
                case "三只熊度假酒店公寓(崇礼容辰店)":
                    path = "http://www.zjksfybjy.com/cn/index.asp";
                    break;
                case "张家口冠奥假日酒店":
                    path = "http://deyy.zjknews.com/";
                    break;
                case "张家口国宾东升大酒店":
                    path = "http://www.eye123.com/";
                    break;
                default:
                    break;
            }
        }

        public void onNothingSelected(AdapterView<?> parent) {
            Toast.makeText(QueryActivity.this, "请您选择一个酒店入住！", Toast.LENGTH_LONG).show();
        }

        public void click(View v) {
            //设置动作为android.intent.action.VIEW
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(path));
            startActivity(intent);
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






