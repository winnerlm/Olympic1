package hotel.main.Information;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wenhaibo.olympic.R;


public class FillInformation extends AppCompatActivity implements View.OnClickListener{

	private Button ibSave;
	private EditText etTittle,etContent;
	private Dao d;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//无title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//全屏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.information_spell);

		d = new Dao(getBaseContext());

        /*
        绑定id
         */

		ibSave = (Button) findViewById(R.id.ibSave);
		etTittle = (EditText) findViewById(R.id.etTittle);
		etContent = (EditText) findViewById(R.id.etContent);


		ibSave.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		String content = etContent.getText().toString();
		String tittle =  etTittle.getText().toString();
		switch (v.getId()) {
			case R.id.ibSave:
				d.add(tittle,content);
				finish();
				break;
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


