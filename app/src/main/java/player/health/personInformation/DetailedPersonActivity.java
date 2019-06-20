package player.health.personInformation;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.wenhaibo.olympic.R;


public class DetailedPersonActivity extends Activity implements View.OnClickListener {
    private EditText person_name, person_age, person_height, person_weight, person_address, person_status;
    private Button btn_person_save;
    private RadioGroup rg_btn_sex;
    private static final String TEMP_INFO = "temp_info";

    //定义保存的全局变量
    private String name, age, height, weight, address, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prson_activity_detailed);
        IntUi();
        IntEvent();
    }

    private void IntEvent() {
        //点击事件
        btn_person_save.setOnClickListener(this);

        //获取到输入的值
        name = person_name.getText().toString();
        age = person_age.getText().toString();
        height = person_height.getText().toString();
        weight = person_weight.getText().toString();
        address = person_address.getText().toString();
        status = person_status.getText().toString();

        //获得SharedPreferences实例
        SharedPreferences sp = getSharedPreferences(TEMP_INFO, MODE_PRIVATE);
        //从SharedPreferences获得备忘录的内容
        //名字
        String content = sp.getString("info_content", "");
        //在EditText中显示备忘录内容
        person_name.setText(content);
        //年龄
        String content1 = sp.getString("info_content1", "");
        person_age.setText(content1);
        //身高
        String content2 = sp.getString("info_content2", "");
        person_height.setText(content2);
        //体重
        String content3 = sp.getString("info_content3", "");
        person_weight.setText(content3);
        //住址
        String content4 = sp.getString("info_content4", "");
        person_address.setText(content4);
        //状态
        String content5 = sp.getString("info_content5", "");
        person_status.setText(content5);
        //性别
        rg_btn_sex.check(sp.getInt("info_content6", -1));
    }

    private void IntUi() {
        //EditText
        person_name = (EditText) findViewById(R.id.person_name);
        person_age = (EditText) findViewById(R.id.person_age);
        person_height = (EditText) findViewById(R.id.person_height);
        person_weight = (EditText) findViewById(R.id.person_weight);
        person_address = (EditText) findViewById(R.id.person_address);
        person_status = (EditText) findViewById(R.id.person_status);
        //btn
        btn_person_save = (Button) findViewById(R.id.btn_person_save);
        //radiogroup
        rg_btn_sex = (RadioGroup) findViewById(R.id.rg_btn_sex);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_person_save:
                //editor
                SharedPreferences.Editor editor = getSharedPreferences(TEMP_INFO, MODE_PRIVATE).edit();
                //将EditText中的文本内容添加到编辑器
                editor.putString("info_content", person_name.getText().toString());
                editor.putString("info_content1", person_age.getText().toString());
                editor.putString("info_content2", person_height.getText().toString());
                editor.putString("info_content3", person_weight.getText().toString());
                editor.putString("info_content4", person_address.getText().toString());
                editor.putString("info_content5", person_status.getText().toString());
                editor.putInt("info_content6", rg_btn_sex.getCheckedRadioButtonId());
                //提交编辑器内容
                editor.commit();
                finish();
                break;
        }
    }
}
