package com.example.wenhaibo.vister;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.wenhaibo.olympic.R;

import player.PlayerActivity;
import worker.WorkerActivity;


public class MainActivity extends AppCompatActivity {


    public    FunctionActivity f1 ;
    public   WorkerActivity f2;
    public   PlayerActivity f3;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    //mTextMessage.setText(R.string.title_home);
                    // Intent intent =new Intent(MainActivity.this,FunctionActivity.class);
                    //startActivity(intent);
                    //获取到FragmentManager，在V4包中通过getSupportFragmentManager，
                    //在系统中原生的Fragment是通过getFragmentManager获得的。
                    FragmentManager FM = getFragmentManager();
                    //2.开启一个事务，通过调用beginTransaction方法开启。
                    FragmentTransaction MfragmentTransaction =FM.beginTransaction();
                    //把自己创建好的fragment创建一个对象
                    FunctionActivity f1 = new FunctionActivity();
                    //向容器内加入Fragment，一般使用add或者replace方法实现，需要传入容器的id和Fragment的实例。
                    //MfragmentTransaction.replace(R.id.buju1,f1);
                    //提交事务，调用commit方法提交。
                    MfragmentTransaction.commit();


                    return true;
                case R.id.navigation_dashboard:
                    // mTextMessage.setText(R.string.title_dashboard);
                    // Intent intent1 =new Intent(MainActivity.this, WorkerActivity.class);
                    //startActivity(intent1);
                    //获取到FragmentManager，在V4包中通过getSupportFragmentManager，
                    //在系统中原生的Fragment是通过getFragmentManager获得的。

                    // f1.getActivity().onBackPressed();
                    FragmentManager FM1 = getFragmentManager();
                    //2.开启一个事务，通过调用beginTransaction方法开启。
                    FragmentTransaction MfragmentTransaction1 =FM1.beginTransaction();
                    //把自己创建好的fragment创建一个对象
                    WorkerActivity f2 = new WorkerActivity();
                    //向容器内加入Fragment，一般使用add或者replace方法实现，需要传入容器的id和Fragment的实例。
                    //FragmentTransaction remove = MfragmentTransaction1.remove( f1 );
                    // MfragmentTransaction1.remove( new FunctionActivity() );

                    //MfragmentTransaction1.replace(R.id.buju1,f2);
                    //提交事务，调用commit方法提交。
                    MfragmentTransaction1.commit();

                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_notifications);
                    //Intent intent2 =new Intent(MainActivity.this, PlayerActivity.class);
                    // startActivity(intent2);
                    FragmentManager FM2 = getFragmentManager();
                    //2.开启一个事务，通过调用beginTransaction方法开启。
                    FragmentTransaction MfragmentTransaction2 =FM2.beginTransaction();
                    //把自己创建好的fragment创建一个对象
                    PlayerActivity f3 = new PlayerActivity();
                    //向容器内加入Fragment，一般使用add或者replace方法实现，需要传入容器的id和Fragment的实例。

                   // MfragmentTransaction2.replace(R.id.buju1,f3);
                    //提交事务，调用commit方法提交。
                    MfragmentTransaction2.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
