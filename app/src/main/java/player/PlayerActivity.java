package player;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;

import com.example.wenhaibo.olympic.R;
import com.example.wenhaibo.vister.translate.TranslateActivity;

import cookfood.MainApp;
import newnote.NoteActivity;
import player.health.HealthActivity;
import player.tingxing.MainActivity;

public class PlayerActivity extends Activity {
    /*private GridView GV;
    private TextView TV;
    private ImageView IV;
    private Mydapter mydapter;
    private int icon[] = {R.drawable.xinlvceshi, R.drawable.shijiantixing,R.drawable.zhuangtaitiaozheng,R.drawable.meishi,R.drawable.dancichazhao,R.drawable.jishiben,R.drawable.jishiben,R.drawable.jingdian,R.drawable.kaixin};
    private String word[] = {"健康检测", "赛程提醒", "状态调整", "健康饮食", "实时翻译", "训练心得", "知己知彼", "周边景点", "一键求医"};*/
    private Button btn1;private Button btn2;private Button btn3;
    private Button btn4;private Button btn5;private Button btn6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        btn1=(Button)findViewById(R.id.btn1);btn2=(Button)findViewById(R.id.btn2);
        btn3=(Button)findViewById(R.id.btn3);btn4=(Button)findViewById(R.id.btn4);
        btn5=(Button)findViewById(R.id.btn5);btn6=(Button)findViewById(R.id.btn6);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(PlayerActivity.this, HealthActivity.class);
                startActivity(intent1);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(PlayerActivity.this, MainActivity.class);
                startActivity(intent2);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(PlayerActivity.this, TranslateActivity.class);
                startActivity(intent2);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent(PlayerActivity.this, NoteActivity.class);
                startActivity(intent3);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(PlayerActivity.this, MainApp.class);
                startActivity(intent2);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(PlayerActivity.this);
                dialog.setTitle("YOU ARE NEED HELP");
                dialog.setMessage("紧急电话拨打,请确认你是否真的需要！！！");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent2 = new Intent(Intent.ACTION_CALL);
                        intent2.setData(Uri.parse("tel:120"));
                        if (ActivityCompat.checkSelfPermission(PlayerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(intent2);
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
       /* btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(PlayerActivity.this, HandlerActivity.class);
                startActivity(intent1);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7=new Intent(Intent.ACTION_VIEW);
                intent7.setData(Uri.parse("https://lvyou.baidu.com/zhangjiakou/?from=zhixin"));
                startActivity(intent7);
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(PlayerActivity.this);
                dialog.setTitle("YOU ARE NEED HELP");
                dialog.setMessage("紧急电话拨打,请确认你是否真的需要！！！");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent2 = new Intent(Intent.ACTION_CALL);
                        intent2.setData(Uri.parse("tel:120"));
                        if (ActivityCompat.checkSelfPermission(PlayerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(intent2);
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
        });*/
    }
}


