package com.example.wenhaibo.vister;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.wenhaibo.olympic.R;

import player.PlayerActivity;
import worker.WorkerActivity;

/**
 * Created by 刘明 on 2019/4/8.
 */
public class DongAoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dongao);
        RadioGroup rdg=(RadioGroup)findViewById(R.id.rdg);
        RadioButton rbtn1=(RadioButton)findViewById(R.id.rbtn1);
        RadioButton rbtn2=(RadioButton)findViewById(R.id.rbtn2);
        rdg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.rbtn1){
                    Intent intent=new Intent(DongAoActivity.this,FunctionActivity.class);
                    startActivity(intent);
                }else if (i==R.id.rbtn2){
                    Intent intent=new Intent(DongAoActivity.this, PlayerActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(DongAoActivity.this, WorkerActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
