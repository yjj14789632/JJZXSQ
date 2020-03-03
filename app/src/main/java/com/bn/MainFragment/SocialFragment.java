package com.bn.MainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.bn.activity.MainActivity;
import com.bn.social.GZFragment;
import com.bn.social.RMFragment;

import com.bn.R;

import java.util.ArrayList;
import java.util.List;

public class SocialFragment extends Fragment implements ViewPager.OnPageChangeListener,View.OnClickListener{
    View view;
    MainActivity mainActivity;
    private ViewPager myviewpager;
    private ImageView search;
    private ImageView myLoc;
    public static LayoutInflater inflater;
    public static ViewGroup container;
    //选项按钮
    public Button rmButton;
    public Button gzButton;
    //作为指示标签的按钮
    private ImageView cursor;
    //标志指示标签的横坐标
    float cursorX = 0;
    //所有标题按钮的数组
    private Button[] btnArgs;
    MyFragmentPagerAdapter adapter;
    //fragment的集合，对应每个子页面
    private ArrayList<Fragment> fragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view =inflater.inflate(R.layout.social_layout,container,false);
        return view;
    }

    public  void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        initView();
    }
    public void initView(){
        search=(ImageView)view.findViewById(R.id.search);//搜索图标
        search.setOnClickListener(this);
        myLoc=(ImageView)view.findViewById(R.id.location);//定位
        myLoc.setOnClickListener(this);
        myviewpager = (ViewPager)view.findViewById(R.id.myviewpager);//viewPager
        rmButton=(Button) view.findViewById(R.id.rmButton);
        gzButton=(Button) view.findViewById(R.id.gzButton);
        btnArgs = new Button[]{rmButton,gzButton};
        cursor = (ImageView)view.findViewById(R.id.biaoqian);//指示器
        cursor.setBackgroundColor(getResources().getColor(R.color.white));//设置指示器颜色

        rmButton.post(new Runnable(){
            @Override
            public void run() {

                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)cursor.getLayoutParams();
                lp.width = rmButton.getWidth();
                cursor.setLayoutParams(lp);
                cursor.setX(rmButton.getLeft());
            }
        });

        rmButton.setOnClickListener(this);
        gzButton.setOnClickListener(this);
        mainActivity=(MainActivity)getActivity();
        fragments = new ArrayList<Fragment>();
        fragments.add(new RMFragment());
        fragments.add(new GZFragment());

        adapter = new MyFragmentPagerAdapter(mainActivity.getSupportFragmentManager(),fragments);
        myviewpager.setAdapter(adapter);//ViewPager设置适配器
        myviewpager.addOnPageChangeListener(this);//注册监听


    }
    private void resetButtonColor() {
        rmButton.setTextColor(getResources().getColor(R.color.nichengcolor));
        gzButton.setTextColor(getResources().getColor(R.color.nichengcolor));
    }
    @Override
    public void onPageSelected(int position) {
        //每次滑动首先重置所有按钮的颜色
        resetButtonColor();
        //将滑动到的当前按钮颜色设置为绿色
        btnArgs[position].setTextColor(getResources().getColor(R.color.black));
        cursorAnim(position);
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rmButton:
                myviewpager.setCurrentItem(0);
                cursorAnim(0);
                break;
            case R.id.gzButton:
                myviewpager.setCurrentItem(1);
                cursorAnim(1);
                break;
           /* case R.id.search:
                Intent intentsearch=new Intent();
                intentsearch.setClass(this.getContext(), SearchActivity.class);
                startActivity(intentsearch);
                break;
            case R.id.myLoc:
                Intent intent=new Intent(MainActivity.activity,MyLocActivity.class);
                startActivity(intent);
                break;*/
        }
    }

    //指示器的跳转，传入当前所处的页面的下标
    public void cursorAnim(int curItem){
        cursorX = rmButton.getLeft();
        //再根据当前的curItem来设置指示器的宽度
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)cursor.getLayoutParams();
        lp.width = btnArgs[curItem].getWidth();
        cursor.setLayoutParams(lp);
        //循环获取当前页之前的所有页面的宽度
        for(int i=0; i<curItem; i++){
            cursorX =cursorX+btnArgs[i].getWidth();
        }
        cursor.setX(cursorX);
    }
    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> list;//存储所有的fragment
        public MyFragmentPagerAdapter(FragmentManager supportFragmentManager, ArrayList<Fragment> fragments) {
            super(supportFragmentManager);
            this.list=fragments;
        }
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }
        @Override
        public int getCount() {
            return list.size();
        }
    }

}
