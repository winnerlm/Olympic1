package worker.renwuchaxun;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
        setContentView(R.layout.activity_query);
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
        hadapter = ArrayAdapter.createFromResource(this, R.array.hosinfo, android.R.layout.simple_spinner_item);
        //设置下拉列表的风格
        hadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter2 添加到spinner中
        hspinner.setAdapter(hadapter);
        //添加事件Spinner事件监听
        hspinner.setOnItemSelectedListener(new HosItemSelectedListener());
    }

    private class HosItemSelectedListener implements OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            String[] hospitals = getResources().getStringArray(R.array.hosinfo);
//            Toast.makeText(MainActivity.this, "您预约的是:" + hospitals[pos], Toast.LENGTH_LONG).show();
            switch (hospitals[pos]) {
                case "河北北方学院附属第一医院":
                    path = "https://www.hbbfyfy.com/index.html";
                    break;
                case "中国人民解放军第二五一医院":
                    path = "http://www.251yy.com.cn/";
                    break;
                case "张家口市第一医院":
                    path = "http://www.zjkdyyy.com/";
                    break;
                case "张家口市妇幼保健院":
                    path = "http://www.zjksfybjy.com/cn/index.asp";
                    break;
                case "张家口市第二医院":
                    path = "http://deyy.zjknews.com/";
                    break;
                case "张家口市第四医院":
                    path = "http://www.eye123.com/";
                    break;
                default:
                    break;
            }
        }

        public void onNothingSelected(AdapterView<?> parent) {
            Toast.makeText(QueryActivity.this, "请您选择一所就诊医院！", Toast.LENGTH_LONG).show();
        }

        public void click(View v) {
            //设置动作为android.intent.action.VIEW
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(path));
            startActivity(intent);
        }
    }
}






