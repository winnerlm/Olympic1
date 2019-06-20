package worker;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.example.wenhaibo.olympic.R;
import com.example.wenhaibo.vister.translate.TranslateActivity;

import newnote.NoteActivity;
import worker.JianCe.UI.MainActivity;
import worker.chatroom.activity.ChatroomActivity;
import worker.renwu.MyListviewActivity;

public class WorkerActivity extends Activity {
   /* private GridView GV;
    private ImageView IV;
    private TextView TV;
    private int picture []={R.drawable.gongzuoxinde,R.drawable.gongzuotixing,R.drawable.yijianqiuyi,R.drawable.file,R.drawable.gongzuoxinde,R.drawable.zhuangtaitiaozheng,R.drawable.shijiantixing,R.drawable.dancichazhao};
    private String msn[]={"任务查询","任务提醒","一键求医","文件传输","记事备忘","实时通信","现场监测","实时翻译",};*/

    private Button btn1;private Button btn2;private Button btn3;
    private Button btn4;private Button btn5;private Button btn6;
    //private Button btn7;private Button btn8;private Button btn9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);

        btn1=(Button)findViewById(R.id.btn1);btn2=(Button)findViewById(R.id.btn2);
        btn3=(Button)findViewById(R.id.btn3);btn4=(Button)findViewById(R.id.btn4);
        btn5=(Button)findViewById(R.id.btn5);btn6=(Button)findViewById(R.id.btn6);
       // btn7=(Button)findViewById(R.id.btn7);btn8=(Button)findViewById(R.id.btn8);
        //btn9=(Button)findViewById(R.id.btn9);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(WorkerActivity.this, MyListviewActivity.class);
                startActivity(intent1);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(WorkerActivity.this);
                dialog.setTitle("YOU ARE NEED HELP");
                dialog.setMessage("紧急电话拨打,请确认你是否真的需要！！！");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent2 = new Intent(Intent.ACTION_CALL);
                        intent2.setData(Uri.parse("tel:120"));
                        if (ActivityCompat.checkSelfPermission(WorkerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(WorkerActivity.this, NoteActivity.class);
                startActivity(intent2);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent(WorkerActivity.this, ChatroomActivity.class);
                startActivity(intent3);
                /*Intent intent =new Intent(WorkerActivity.this, StartSendActivity.class);//文件传输
                startActivity(intent);*/
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(WorkerActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6=new Intent(WorkerActivity.this, TranslateActivity.class);
                startActivity(intent6);
            }
        });
        /*btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(WorkerActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6=new Intent(WorkerActivity.this, TranslateActivity.class);
                startActivity(intent6);
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(WorkerActivity.this);
                dialog.setTitle("YOU ARE NEED HELP");
                dialog.setMessage("紧急电话拨打,请确认你是否真的需要！！！");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent2 = new Intent(Intent.ACTION_CALL);
                        intent2.setData(Uri.parse("tel:120"));
                        if (ActivityCompat.checkSelfPermission(WorkerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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



