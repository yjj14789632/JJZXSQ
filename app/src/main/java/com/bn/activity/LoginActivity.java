package com.bn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bn.R;
import com.bn.Util.NetInfoUtil;
import com.bn.Util.StrListChange;
import com.bn.Util.UserInfoUtil;

import org.jetbrains.annotations.Nullable;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView username,password;
    Button login;
    Button zhuce;
    private String Pass;
    String user,pwd,user_id;
    Handler handler;
    final int message=0;
    final int ToMainActivityHomeFragment=1;
    final int gettedUidMessage=2;
    private boolean isPswViszible=false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intiView();
    }
    public void intiView(){
        /*if(UserInfoUtil.GetUid()!=null){
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            return;
        }*/
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        zhuce = findViewById(R.id.zhuce);

        //登录监听
        login.setOnClickListener(this);
        //注册监听
        zhuce.setOnClickListener(this);
        handler=new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case message:
                        if(Pass==null){
                            Toast.makeText(getApplicationContext(),"账号错误",Toast.LENGTH_SHORT).show();
                        }else if(pwd.equals(Pass)){
                            Toast.makeText(getApplicationContext(),"登陆成功",Toast.LENGTH_SHORT).show();
                            Intent intent1=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent1);
                        }else if(pwd!=Pass){
                            Toast.makeText(getApplicationContext(),"密码错误",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case ToMainActivityHomeFragment:
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        break;
                    case gettedUidMessage:
                        UserInfoUtil.WriteUid(user_id);
                        Intent intent1=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent1);
                        break;
                }
            }
        };

    }
    @Override
    public void onClick(View v){
        switch (v.getId())
        {
            case R.id.login:
                user=username.getText().toString().trim();
                pwd=password.getText().toString().trim();

                mythread my=new mythread();
                my.start();
                try {
                    my.join();
                }catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.zhuce:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;


        }

    }
    private void setPswViszible() {
        //定义全局变量，isPswVisible ：密码是否显示
        isPswViszible = !isPswViszible;
        ImageView ivPswShow;
        EditText etWifiPsw = null;
        if (isPswViszible) {
            //设置图片，隐藏或者显示图片
            //ivPswShow.setImageResource(R.drawable.icon_xianshimima);
            //显示密码方法一
            HideReturnsTransformationMethod method2 = HideReturnsTransformationMethod.getInstance();
            etWifiPsw.setTransformationMethod(method2);
        } else {
            //设置图片，隐藏或者显示图片
            //ivPswShow.setImageResource(R.drawable.icon_buxianshimima);
            //隐藏密码方法一
            PasswordTransformationMethod method1 = PasswordTransformationMethod.getInstance();
            etWifiPsw.setTransformationMethod(method1);
        }
        //切换后将EditText光标置于末尾
        etWifiPsw.setSelection(etWifiPsw.getText().toString().length());

    }

    class mythread extends Thread{
        //private String user;
        //public mythfread(String user){this.user=user;}
        @Override
        public void run(){
            String[] str = NetInfoUtil.getUserPass(user);
            if(!str[0].isEmpty()) {
                user_id = str[0];
                Pass = str[1];
                handler.sendEmptyMessage(message);
            }
            else {
                Pass=null;
                handler.sendEmptyMessage(message);
            }

        }
    }


}
