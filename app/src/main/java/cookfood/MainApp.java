package cookfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.wenhaibo.olympic.R;

/**
 * Created by 刘明 on 2019/5/12.
 */
public class MainApp extends Activity implements View.OnClickListener {
    Button list = null;
    Button lm = null;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.cook_main);
        list = (Button) findViewById(R.id.foodlistbtn);
        lm = (Button) findViewById(R.id.yingyangbtn);
        list.setOnClickListener(this);
        lm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.foodlistbtn) {
            Intent intent = new Intent();
            intent.setClass(MainApp.this, FoodListView.class);
            startActivity(intent);
            //list.setBackgroundResource(R.drawable.btn_food_list_active);
        } else if (v.getId() == R.id.yingyangbtn) {
            Intent intent = new Intent();
            intent.setClass(MainApp.this, cook_book_MainActivity.class);
            startActivity(intent);
            //yingyangbtn.setBackgroundResource(R.drawable.btn_food_about_active);
        }
    }
}