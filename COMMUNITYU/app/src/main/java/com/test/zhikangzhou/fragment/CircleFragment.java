package com.test.zhikangzhou.fragment;


import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.test.zhikangzhou.action.StaticVariable;
import com.test.zhikangzhou.activity.CircleDetail;
import com.test.zhikangzhou.activity.LoginActivity;
import com.test.zhikangzhou.activity.R;
import com.test.zhikangzhou.activity.ResultList;
import com.test.zhikangzhou.client.model.*;
import com.test.zhikangzhou.client.util.GetGroupsExecuter;
import com.test.zhikangzhou.client.util.SearchGroupExecuter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CircleFragment extends Fragment {
    private EditText mSearchText;
    private ImageButton mSearchButton;
    public static ListView mCircleListView;
    private String[] strs = new String[] {
            "first", "second", "third", "fourth", "fifth"
    };

    public CircleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mview=inflater.inflate(R.layout.fragment_circle, container, false);
        GetGroupsTask ggt=new GetGroupsTask();
        mSearchText=(EditText)mview.findViewById(R.id.et_search);
        mSearchButton=(ImageButton)mview.findViewById(R.id.search_button);
        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                    StaticVariable.mSearchWord=mSearchText.getText().toString();
                    Intent intent = new Intent(getActivity(), ResultList.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticVariable.mSearchWord=mSearchText.getText().toString();
                Intent intent = new Intent(getActivity(), ResultList.class);
                startActivity(intent);
            }
        });
        mCircleListView=(ListView)mview.findViewById(R.id.listView_circle);
        ggt.execute(StaticVariable.mCurrentUser.getUserid());
        mCircleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StaticVariable.mCurrentCircle=StaticVariable.mCircles[position];
                Intent intent = new Intent(getActivity(), CircleDetail.class);
                startActivity(intent);
            }
        });
        return mview;
    }

    private class GetGroupsTask extends AsyncTask<String,Void,ViewCircle[]> {
        @Override
        protected ViewCircle[] doInBackground(String... params){
            Object[] list=new GetGroupsExecuter(params[0]).execute();
            if(list!=null) {
                ViewCircle[] mCircleList = new ViewCircle[list.length];
                for (int i = 0; i < list.length; i++) {
                    mCircleList[i] = (ViewCircle) list[i];
                }
                return mCircleList;
            }
            else
                return null;
        }

        @Override
        protected void onPostExecute(ViewCircle[] result)
        {
            if(result!=null) {
                String[] listname = new String[result.length];
                StaticVariable.mCircles = result;
                for (int i = 0; i < result.length; i++) {
                    listname[i] = result[i].getGroupname();
                }
                mCircleListView.setAdapter(new ArrayAdapter<String>(CircleFragment.this.getActivity(),
                        R.layout.circle_text_layout, listname));
            }

        }

    }

}
