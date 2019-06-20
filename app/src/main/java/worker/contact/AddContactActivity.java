package worker.contact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wenhaibo.olympic.R;


public class AddContactActivity extends Activity implements View.OnClickListener {
    private Button btn_conservation;
    private EditText et_name;
    private EditText et_number;
    private Change change;
    private Person data;
    private int result;
    private String tmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_activity_add_contact);
        change = new Change(getBaseContext());
        btn_conservation = (Button) findViewById(R.id.btn_conservation);

        et_name = (EditText) findViewById(R.id.et_name);
        et_number = (EditText) findViewById(R.id.et_number);
        //传值
        Intent intent = getIntent();
        tmp = intent.getStringExtra("extra");
        if (tmp != null) {
            result = Integer.valueOf(tmp);
            data = change.find(result);
            et_number.setText(data.getNumber());
            et_name.setText(data.getName());
        }
        btn_conservation.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_conservation:
                String name = et_name.getText().toString();
                String number = et_number.getText().toString();
                //若tmp值不空 则进行以下操作
                if (tmp != null) {
                    //利用id找到所对应的位置  并更改
                    data.setId(result);
                    data.setName(name);
                    data.setNumber(number);
                    change.update(data);
                    Toast.makeText(AddContactActivity.this, "编辑成功" , Toast.LENGTH_SHORT).show();
                    Intent i2 = new Intent(AddContactActivity.this, worker.contact.ContactMainActivity.class);
                    startActivity(i2);
                    finish();
                } else {

                    if (name.length()>0 && number.length()>0 ) {

                        change.add(name, number);
                        finish();
                    } else {
                        Toast.makeText(AddContactActivity.this, "请输入正确的格式", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
