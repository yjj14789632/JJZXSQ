package com.bn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bn.R;
import com.bn.Util.NetInfoUtil;
import com.mob.tools.utils.SharePrefrenceHelper;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.UserInterruptException;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    //private static final String TAG = "VerifyActivity";
    private static final String[] DEFAULT_COUNTRY = new String[]{"中国", "42", "86"};
    private static final int COUNTDOWN = 60;
    private static final String TEMP_CODE = "1319972";
    private static final String KEY_START_TIME = "start_time";
    private static final int REQUEST_CODE_VERIFY = 1001;
    private String currentId;
    private String currentPrefix;
    private EditText username,password,nickname,verify;
    private Button register;
    private TextView tvCode,tvCountry;
    private Toast toast;
    private Handler handler;
    private EventHandler eventHandler;
    private int currentSecond;
    private SharePrefrenceHelper helper;
    final int message=1;
    private String userinfo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        intiView();
        currentId = DEFAULT_COUNTRY[1];
        currentPrefix = DEFAULT_COUNTRY[2];
        tvCountry.setText(getString(R.string.smssdk_default_country)+"+"+DEFAULT_COUNTRY[2]);
        helper = new SharePrefrenceHelper(this);
        helper.open("sms_sp");
        register.setEnabled(false);
        tvCode.setEnabled(false);

    }
    public void intiView(){
        username = findViewById(R.id.username);
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //手机号输入大于5位，获取验证码按钮可点击
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvCode.setEnabled(username.getText() != null && username.getText().length() > 5);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password = findViewById(R.id.password);
        password.setOnClickListener(this);
        nickname = findViewById(R.id.nickname);
        nickname.setOnClickListener(this);
        register = findViewById(R.id.register);
        register.setOnClickListener(this);
        verify = findViewById(R.id.verify);
        verify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //验证码输入6位并且手机大于5位，验证按钮可点击
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                register.setEnabled(verify.getText() != null && verify.getText().length() >= 4 && username.getText() != null && username.getText().length() > 5&&nickname.getText()!=null&&password.getText()!=null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvCode = findViewById(R.id.tvCode);
        tvCode.setOnClickListener(this);
        tvCountry = findViewById(R.id.tvCountry);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 0:
                        if (tvCode != null) {
                            if (currentSecond > 0) {
                                tvCode.setText(getString(R.string.smssdk_get_code) + " (" + currentSecond + "s)");
                                tvCode.setEnabled(false);
                                currentSecond--;
                                handler.sendEmptyMessageDelayed(0, 1000);
                            } else {
                                tvCode.setText(R.string.smssdk_get_code);
                                tvCode.setEnabled(true);
                            }
                        }
                        break;
                    case message:
                        userinfo=username.getText().toString().trim()+"<#>"+nickname.getText().toString().trim()+"<#>"+password.getText().toString().trim();
                        new Thread(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        System.out.println("78845454545");
                                        NetInfoUtil.insertUser(userinfo);
                                    }
                                }
                        ).start();
                        toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

                        break;
                }


                }

        };
        eventHandler = new EventHandler() {
            public void afterEvent(final int event, final int result, final Object data) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //提交验证成功，跳转成功页面，否则toast提示
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                handler.sendEmptyMessage(message);

                            } else {
                                toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE || event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {
                    System.out.println("44444444444444444444444444");
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            System.out.println("788888888888888888888");
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                System.out.println("333333333555555555555556");
                                currentSecond = COUNTDOWN;
                                handler.sendEmptyMessage(0);
                                helper.putLong(KEY_START_TIME, System.currentTimeMillis());
                            } else {
                                if (data != null && (data instanceof UserInterruptException)) {
                                    // 由于此处是开发者自己决定要中断发送的，因此什么都不用做
                                    return;
                                }

                            }
                        }
                    });
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
    }



    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tvCode:
                System.out.println("7885151545454654");
                long startTime = helper.getLong(KEY_START_TIME);
                if(System.currentTimeMillis() - startTime < COUNTDOWN * 1000)
                {
                    toast.makeText(RegisterActivity.this,"操作频繁",Toast.LENGTH_SHORT).show();
                }else {
                    System.out.println("1123455566");
                    SMSSDK.getVerificationCode(currentPrefix,username.getText().toString().trim());
                }
                break;
            case R.id.register:
                SMSSDK.submitVerificationCode(currentPrefix,username.getText().toString().trim(),verify.getText().toString().trim());
                break;
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        SMSSDK.unregisterEventHandler(eventHandler);
    }

}
