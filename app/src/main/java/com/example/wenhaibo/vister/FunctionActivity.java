package com.example.wenhaibo.vister;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.example.wenhaibo.olympic.R;
import com.example.wenhaibo.vister.buy.ScanActivity;
import com.example.wenhaibo.vister.match.MatchActivity;
import com.example.wenhaibo.vister.translate.TranslateActivity;


public class FunctionActivity extends FragmentActivity {
    /*private GridView GV;
    private MyAdapter MyAdapter;
    private ImageView IV;
    private TextView TV;
    private int[] icon = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i};
    private String[] Tex = {"提前预订", "周边美食", "定位导航", "一键求医", "项目简介", "纪念品购买?", "赛事情况", "天气预告","实时翻译"};*/

    private Button btn1;private Button btn2;private Button btn3;
    private Button btn4;private Button btn5;private Button btn6;
    private Button call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);

        btn1=(Button)findViewById(R.id.btn1);btn2=(Button)findViewById(R.id.btn2);
        btn3=(Button)findViewById(R.id.btn3);btn4=(Button)findViewById(R.id.btn4);
        btn5=(Button)findViewById(R.id.btn5);btn6=(Button)findViewById(R.id.btn6);
        call=(Button)findViewById(R.id.call);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(FunctionActivity.this, hotel.main.MainActivity.class);
                startActivity(intent1);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(FunctionActivity.this, stop.MainActivity.class);
                startActivity(intent2);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent(FunctionActivity.this, SportListActivity.class);
                startActivity(intent3);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent(FunctionActivity.this, ScanActivity.class);
                startActivity(intent3);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(FunctionActivity.this, MatchActivity.class);
                startActivity(intent1);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent(FunctionActivity.this, TranslateActivity.class);
                startActivity(intent3);
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(FunctionActivity.this);
                dialog.setTitle(" HELP ME！！！");
                dialog.setMessage("紧急电话拨打,请确认你是否需要！！！");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent3 = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:" + "120");
                        intent3.setData(data);
                        startActivity( intent3);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
    }
}
