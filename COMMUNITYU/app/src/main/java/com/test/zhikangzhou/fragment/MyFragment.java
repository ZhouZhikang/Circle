package com.test.zhikangzhou.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.zhikangzhou.action.StaticVariable;
import com.test.zhikangzhou.activity.LoginActivity;
import com.test.zhikangzhou.activity.MyActActivity;
import com.test.zhikangzhou.activity.MyCircleActivity;
import com.test.zhikangzhou.activity.R;
import com.test.zhikangzhou.activity.ResultList;
import com.test.zhikangzhou.activity.UserDetail;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {
    private LinearLayout mMyAct;
    private LinearLayout mMyCircle;
    private LinearLayout mMyInfo;
    private Button mLogout;


    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mview= inflater.inflate(R.layout.fragment_my, container, false);
        mMyInfo=(LinearLayout)mview.findViewById(R.id.ll_my_info);
        mMyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticVariable.sSelectedUser=StaticVariable.mCurrentUser;
                Intent intent = new Intent(getActivity(), UserDetail.class);
                startActivity(intent);
            }
        });
        mMyAct=(LinearLayout)mview.findViewById(R.id.ll_my_aty);
        mMyAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyActActivity.class);
                startActivity(intent);
            }
        });
        mMyCircle=(LinearLayout)mview.findViewById(R.id.ll_my_circle);
        mMyCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyCircleActivity.class);
                startActivity(intent);
            }
        });
        mLogout=(Button)mview.findViewById(R.id.btn_logout);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
//                LoginActivity.ed.putBoolean("AUTO_LOGIN", false).commit();
                StaticVariable.isLog=false;
                getActivity().finish();
            }
        });
        return mview;
    }


}
