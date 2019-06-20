package worker.contact;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.wenhaibo.olympic.R;


public class EditActivity extends Activity implements View.OnClickListener{
    private EditText etNei, etRong;
    private ImageButton ibGai;
    private Change change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_activity_edit);
        change = new Change(getBaseContext());

        ibGai = (ImageButton) findViewById(R.id.ibGai);
        etNei = (EditText) findViewById(R.id.etNei);
        etRong = (EditText) findViewById(R.id.etRong);
        ibGai.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
