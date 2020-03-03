package com.bn.MainFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bn.R;

public class HomeFragment extends Fragment {
    public View view=null;
    public static LayoutInflater inflater;
    public static ViewGroup container;
    public RelativeLayout exampleRelativeLayout;
    public RelativeLayout consultRelativeLayout;
    public RelativeLayout companyRelativeLayout;
    public RelativeLayout evaluateRelativeLayout;
    public RelativeLayout storeRelativeLayout;
    public RelativeLayout inspectRelativeLayout;
    public RelativeLayout addRelativeLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.inflater=inflater;
        this.container=container;
        view =inflater.inflate(R.layout.home_layout,container,false);
        initView();
        return view;
    }

    public void initView(){

        addRelativeLayout = (RelativeLayout) view.findViewById(R.id.addRelativeLayout);
        exampleRelativeLayout = (RelativeLayout) view.findViewById(R.id.exampleRelativeLayout);
        consultRelativeLayout = (RelativeLayout) view.findViewById(R.id.consultRelativeLayout);
        companyRelativeLayout = (RelativeLayout) view.findViewById(R.id.companyRelativeLayout);
        evaluateRelativeLayout =(RelativeLayout) view.findViewById(R.id.evalutelativeLayout);
        storeRelativeLayout = (RelativeLayout) view.findViewById(R.id.storeRelativeLayout);
        inspectRelativeLayout = (RelativeLayout) view.findViewById(R.id.inspectRelativeLayout);

    }
}
