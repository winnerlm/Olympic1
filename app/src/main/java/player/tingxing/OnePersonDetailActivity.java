package player.tingxing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.wenhaibo.olympic.R;

import java.util.List;

public class OnePersonDetailActivity extends AppCompatActivity {
    private final String TAG = "OnePersonDetailActivity";

    public static void actionStart(Context context, String name){
        Intent intent = new Intent(context, OnePersonDetailActivity.class);
        intent.putExtra("PersonName", name);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_person_detail);

        Intent intent = getIntent();
        String name = intent.getStringExtra("PersonName");
        Log.d(TAG, name);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(name);

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow);

        toolbar.setNavigationOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                OnePersonDetailActivity.this.onBackPressed();
//                Toast.makeText(OnePersonDetailActivity.this, "clicking the toolbar!", Toast.LENGTH_SHORT).show();

                }
            }
        );

        OnePersonDetailMemoBuilder builder = new OnePersonDetailMemoBuilder();
        List<Memo> memo_list = builder.buildFromAssetFile(this, "preset_persons.xml", name);

        if(memo_list.size() != 0){
            ImageView profile_image = (ImageView)findViewById(R.id.imageView_profile);
            profile_image.setImageDrawable(memo_list.get(0).getImage());
        }

        OnePersonDetailAdapter adapter = new OnePersonDetailAdapter(OnePersonDetailActivity.this, R.layout.one_person_detail, memo_list);
        ListView listView = (ListView)findViewById(R.id.listView_oneperson);
        listView.setAdapter(adapter);
    }
}
