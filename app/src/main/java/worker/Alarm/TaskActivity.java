package worker.Alarm;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.wenhaibo.olympic.R;

import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends Activity {
    private final static int SET = 1;// 线程操作标记
    private Button callSystemClock;
    private List<PackageInfo> allPackageInfos; // 取得系统安装所有软件信息
    private List<PackageInfo> userPackageInfos;// 取得自己安装的软件信息
    private List<PackageInfo> sysPackageInfos;// 取得系统安装的软件信息

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SET:
                    Toast.makeText(TaskActivity.this, "程序加载完成", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        new Thread(new SeachThread()).start();// 启动线程加载安装程序

        callSystemClock = (Button) super.findViewById(R.id.btn_Once_ADay);
        callSystemClock.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                call();//跳转

            }
        });
    }



    private void call(){
        String activityName = "";//activity名
        String packageName = "";// 得到程序包名
        String clockPackageName = "";// 闹钟包名
        for (int i = 0; i < sysPackageInfos.size(); i++) {
            PackageInfo packageInfo = sysPackageInfos.get(i);// 循环取出系统程序
            packageName = packageInfo.packageName;
            if (packageName.indexOf("clock") != -1) {
                if (!(packageName.indexOf("widget") != -1)) {
                    // 找到系统闹钟了
                    ActivityInfo activityInfo = packageInfo.activities[0];// 取出activity信息
                    activityName = activityInfo.name;// 取出activity名字
                    clockPackageName = packageName;
                }
            }
        }
        if((activityName != "") && (clockPackageName != "")){
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(clockPackageName, activityName));
            startActivity(intent);
        }else{
            Toast.makeText(this, "启动闹钟失败！", Toast.LENGTH_SHORT).show();
        }
    }


    // ***************--------*异步线程加载加载安装程序*--------------*******************//
    private class SeachThread extends Thread {
        @Override
        public void run() {
            allPackageInfos = getPackageManager().getInstalledPackages(
                    PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_ACTIVITIES); // 取得系统安装所有软件信息
            userPackageInfos = new ArrayList<PackageInfo>();// 定义用户安装软件信息包
            sysPackageInfos = new ArrayList<PackageInfo>();// 定义系统安装软件信息包

            for (int i = 0; i < allPackageInfos.size(); i++) {// 循环取出所有软件信息
                PackageInfo temp = allPackageInfos.get(i);
                ApplicationInfo appInfo = temp.applicationInfo;// 得到每个软件信息
                if ((appInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0
                        || (appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                    sysPackageInfos.add(temp);// 系统软件
                } else {
                    userPackageInfos.add(temp);// 是用户自己安装软件
                }
            }
            Message msg = new Message();// 查询完成，发送新消息
            msg.what = SET;// 操作标记
            mHandler.sendMessage(msg);
        }
    };
}

