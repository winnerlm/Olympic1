package worker.contact;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wenhaibo.olympic.R;


public class ElseContactActivity extends Activity implements View.OnClickListener{
    private Button btn_call,btn_contact;
    private EditText et_phone,et_phone_display;
    private String username,usernumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_activity_else_contact);
        btn_call = (Button) findViewById(R.id.btn_call);
        btn_contact = (Button) findViewById(R.id.btn_contact);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_phone_display = (EditText) findViewById(R.id.et_phone_display);

        btn_call.setOnClickListener(this);
        btn_contact.setOnClickListener(this);

    }


    // 3、获取联系人的点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_contact:
                startActivityForResult(new Intent(
                        Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), 0);
                break;
            case R.id.btn_call:
                String number = et_phone.getText().toString().trim();
                if (number.length()>0){
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + number));
                    startActivity(intent);
                }else{
                    Toast.makeText(ElseContactActivity.this,"请先获取联系人！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ContentResolver reContentResolverol = getContentResolver();
            Uri contactData = data.getData();
            @SuppressWarnings("deprecation")
            Cursor cursor = managedQuery(contactData, null, null, null, null);
            cursor.moveToFirst();
            username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null,
                    null);
            while (phone.moveToNext()) {
                usernumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Log.e("gaoyu_display","内容是"+username +usernumber);
                et_phone_display.setText(username);
                et_phone.setText(usernumber);

            }

        }
    }

}
