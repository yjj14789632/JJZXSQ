package com.bn.social;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bn.R;
import com.bn.Util.Constant;
import com.bn.activity.MainActivity;
import java.util.ArrayList;
import java.util.List;
import com.bn.Util.MyGetBitmap;
import com.bn.Util.NetInfoUtil;
import com.bn.Util.StrListChange;

public class RMFragment extends Fragment {
    View view;
    private SharedPreferences sp;
    LayoutInflater inflaterAdapter;
    static List postls;//帖子
    static List userls;//用户
    public static String[][] postxx;
    public static String[][] userxx;
    Bitmap[] postpic;
    Bitmap[] userpic;
    byte pic[];//图片字节数组
    ListView lv;
    mythread my=new mythread();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view=inflater.inflate(R.layout.rmpost_layout,container,false);
        return view;
    }
    public  void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        initView();
    }
    public void initView(){
       // sp = MainActivity.activity.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
        //Constant.isHavaNet=true;
        my.start();
        try {
            my.join();

        }catch (Exception e) {
            e.printStackTrace();
        }
        inflaterAdapter=this.getLayoutInflater();
        lv=(ListView) view.findViewById(R.id.allpostListView);
        BaseAdapter ba= new BaseAdapter() {
            @Override
            public int getCount() {
                return postxx.length;
            }

            @Override
            public Object getItem(int arg0) {
                return null;
            }

            @Override
            public long getItemId(int arg0) {
                return 0;
            }
            @Override
            public View getView(int arg0, View arg1, ViewGroup arg2) {
                View view;
                ViewHolder viewHolder;
                if(arg1==null)
                {
                    viewHolder=new ViewHolder();
                    view= inflaterAdapter.inflate(R.layout.rmpostlistdetail_layout,null);
                    viewHolder.username=(TextView)view.findViewById(R.id.postusername);
                    viewHolder.userNick=(ImageView)view.findViewById(R.id.userNick);
                    viewHolder.posttext=(TextView) view.findViewById(R.id.posttext);
                    viewHolder.postpic1=(ImageView)view.findViewById(R.id.postpic1);
                    view.setTag(viewHolder);//将viewHolder存储在View中
                }else{
                    view=arg1;
                    viewHolder=(ViewHolder) view.getTag();
                }
                viewHolder.username.setText(postxx[arg0][3]);
                viewHolder.userNick.setImageBitmap(userpic[arg0]);
                viewHolder.postpic1.setImageBitmap(postpic[arg0]);
                viewHolder.posttext.setText(postxx[arg0][1]);
                return view;
            }
        };
        lv.setAdapter(ba);

    }
    public class mythread extends Thread
    {
        @Override
        public void run()
        {

            postls=new ArrayList<String[]>();
            String ss=NetInfoUtil.GetPostListXx();
            //System.out.println("79878998797987979879797979"+ss);
            postls=StrListChange.StrToList(NetInfoUtil.GetPostListXx());
            if(!postls.isEmpty()&&postls.size()!=0) {
                postxx = StrListChange.ListToTwoStringArray(postls);

            }
            postpic=new Bitmap[postxx.length];
            for(int i=0;i<postxx.length;i++) {
                if (MyGetBitmap.isEmpty(postxx[i][2].trim())) {
                    pic=NetInfoUtil.getPic(postxx[i][2].trim());
                    postpic[i]=MyGetBitmap.getBitmapFromByteArray(pic);
                    postpic[i]=MyGetBitmap.zoomImg(postpic[i],270,270);
                    //MyGetBitmap.setInSDBitmap(pic,postxx[i][2].trim());
                }else {
                    postpic[i]=MyGetBitmap.getSDBitmap(postxx[i][2].trim());
                    //缩小大小
                    postpic[i]=MyGetBitmap.zoomImg(postpic[i],270,270);
                    //不等于null就设置成null
                    if (MyGetBitmap.bitmap != null && !MyGetBitmap.bitmap.isRecycled()) {
                        MyGetBitmap.bitmap = null;
                    }
                }

            }

                userpic=new Bitmap[postxx.length];
                for(int j=0;j<postxx.length;j++) {
                    if (MyGetBitmap.isEmpty(postxx[j][4].trim())) {
                        pic=NetInfoUtil.getPic(postxx[j][4].trim());
                        userpic[j]=MyGetBitmap.getBitmapFromByteArray(pic);
                        userpic[j]=MyGetBitmap.zoomImg(userpic[j],270,270);
                       // MyGetBitmap.setInSDBitmap(pic,postxx[j][4].trim());
                    }else {
                        userpic[j]=MyGetBitmap.getSDBitmap(postxx[j][4].trim());
                        //缩小大小
                        userpic[j]=MyGetBitmap.zoomImg(userpic[j],270,270);
                        //不等于null就设置成null
                        if (MyGetBitmap.bitmap != null && !MyGetBitmap.bitmap.isRecycled()) {
                            MyGetBitmap.bitmap = null;
                        }
                    }

            }


        }
    }
    public class ViewHolder{
        ImageView userNick;
        TextView  username;
        TextView posttext;
        ImageView postpic1;
    }
}
