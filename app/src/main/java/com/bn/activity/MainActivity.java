package com.bn.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bn.R;
import com.bn.Util.Constant;
import com.bn.MainFragment.HomeFragment;
import com.bn.MainFragment.MeFragment;
import com.bn.MainFragment.SocialFragment;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {
    RadioGroup group;//获得按钮组
    RadioButton main;//获得主界面的按钮
    FragmentManager fragmentManager;//管理fragment
    HomeFragment homeFragment;
    SocialFragment socialFragment;
    MeFragment meFragment;
    public static Activity activity;
    //数据库
    static public SharedPreferences sp;
    //切换的Fragment
    int  selectFragment=0;
    //从SharedPreferences获取是否已经登录
    public static long firstTime=0;//第一次按下返回键的时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity=this;
        //获取管理员
        fragmentManager = getSupportFragmentManager();
        //获取按钮组和按钮
        group = (RadioGroup) this.findViewById(R.id.main_bottom_button);
        main = (RadioButton) this.findViewById(R.id.main_home);
        //默认选中主界面
        main.setChecked(true);
        //切换到主界面
        changeFragment(0);
        group.setOnCheckedChangeListener(this);
    }

    private void changeFragment(int num) {
        //拿到FragmentTransaction，FragmentTransaction对fragment进行添加,移除,替换,以及执行其他动作。
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        //筛选掉空的Fragment
        //保证每次都可以正常切换
        hideFragment(beginTransaction);
        switch (num) {
            case 0: selectFragment=0;
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    beginTransaction.add(R.id.main_content, homeFragment);
                } else {
                    beginTransaction.show(homeFragment);
                }
                break;
            case 1:selectFragment=1;
                if (socialFragment == null) {
                    socialFragment = new SocialFragment();
                    beginTransaction.add(R.id.main_content, socialFragment);
                } else {
                    beginTransaction.show(socialFragment);
                }
                break;
            case 2:selectFragment=2;
                if (meFragment == null) {
                    meFragment = new MeFragment();
                    beginTransaction.add(R.id.main_content, meFragment);
                } else {
                    beginTransaction.show(meFragment);
                }
                break;
        }
        beginTransaction.commit(); //提交
    }

    private void hideFragment(FragmentTransaction beginTransaction) {
        if (homeFragment != null) {
            beginTransaction.hide(homeFragment);
        }
        if (socialFragment != null) {
            beginTransaction.hide(socialFragment);
        }
        if (meFragment != null) {
            beginTransaction.hide(meFragment);
        }
    }
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.main_home:
                changeFragment(0);
                break;
            case R.id.main_social:
                changeFragment(1);
                break;
            case R.id.main_me:
                changeFragment(2);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //如果按下的是返回键
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            Toast.makeText(MainActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run()
                {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    startActivity(intent);
                    System.exit(0);
                }
            }, 2000);
            return super.onKeyDown(keyCode, event);
        }
        return true;
    }

}
