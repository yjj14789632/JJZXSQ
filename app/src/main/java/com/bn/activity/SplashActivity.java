package com.bn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

//import com.bn.Bean.User;
import com.bn.R;

import org.jetbrains.annotations.Nullable;

import java.util.Timer;
import java.util.TimerTask;

//import cn.bmob.v3.BmobUser;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //延时操作
        Timer timer = new Timer();
        timer.schedule(timetast,2000);

        //Bomb.initialize(this,"7fb4045727fc65d343446134d2389de0");
    }
    TimerTask timetast = new TimerTask() {
        @Override
        public void run() {
            //startActivity(new Intent(Splash.this, MainActivity.class));
            //如果已登录 跳转到主界面 没有登录跳转到登录界面
            /*BmobUser bmobUser = BmobUser.getCurrentUser(User.class);
            if (bmobUser!=null){
                startActivity(new Intent(Splash.this, MainActivity.class));
                finish();
            }else {
                startActivity(new Intent(Splash.this,Login.class));
                finish();
            }*/
            startActivity(new Intent(SplashActivity.this,LoginActivity.class));

        }
    };


}
