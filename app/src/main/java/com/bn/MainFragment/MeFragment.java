package com.bn.MainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bn.R;
import com.bn.activity.MyinformationActivity;

public class MeFragment extends Fragment implements View.OnClickListener {
    public View view=null;
    public static LayoutInflater inflater;
    public static ViewGroup container;
    RelativeLayout iup_layout;
    RelativeLayout HeadNickNameRelativeLayout;
    RelativeLayout my_postRelativeLayout;
    RelativeLayout my_likeRelativeLayout;
    RelativeLayout my_collectRelativeLayout;
    public Button meButton;
    TextView post_num;//发布
    TextView like_num;//喜欢
    TextView collect_num;//收藏

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.me_layout,container,false);
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        initView();
    }
    public void  initView()
    {
        post_num = (TextView) view.findViewById(R.id.me_frament_post_num);
        like_num = (TextView) view.findViewById(R.id.me_frament_like_num);
        collect_num = (TextView) view.findViewById(R.id.me_frament_collect_num);
        HeadNickNameRelativeLayout = (RelativeLayout) view.findViewById(R.id.HeadNickNameRelativeLayout);
        my_postRelativeLayout = (RelativeLayout) view.findViewById(R.id.my_postRelativeLayout);
        my_likeRelativeLayout = (RelativeLayout) view.findViewById(R.id.my_likeRelativeLayout);
        my_collectRelativeLayout = (RelativeLayout) view.findViewById(R.id.my_collectRelativeLayout);
        meButton=(Button) view.findViewById(R.id.meButton);
        meButton.setOnClickListener(this);




    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.meButton:
                startActivity(new Intent(getActivity(), MyinformationActivity.class));
                break;
        }
    }
}