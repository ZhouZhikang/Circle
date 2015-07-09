package com.test.zhikangzhou.fragment;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.test.zhikangzhou.action.StaticVariable;
import com.test.zhikangzhou.activity.ActivityDetail;
import com.test.zhikangzhou.activity.CircleDetail;
import com.test.zhikangzhou.activity.MainActivity;
import com.test.zhikangzhou.activity.R;
import com.test.zhikangzhou.client.model.ViewActivity;
import com.test.zhikangzhou.client.model.ViewCircle;
import com.test.zhikangzhou.client.util.GetGroupsExecuter;
import com.test.zhikangzhou.client.util.PartActsExecuter;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityFragment extends Fragment {
    private ListView mActivityListView;
    private String[] strs = new String[] {
            "first", "second", "third", "fourth", "fifth"
    };

    public ActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mview=inflater.inflate(R.layout.fragment_activity, container, false);
        mActivityListView=(ListView)mview.findViewById(R.id.listView_activity);
        PartActsTask pat=new PartActsTask();
        pat.execute(StaticVariable.mCurrentUser.getUserid());
        mActivityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StaticVariable.mCurrentActivity=StaticVariable.mActivity[position];
                Intent intent = new Intent(getActivity(), ActivityDetail.class);
                startActivity(intent);
            }
        });
        return mview;
    }


    private class PartActsTask extends AsyncTask<String,Void,ViewActivity[]> {
        @Override
        protected ViewActivity[] doInBackground(String... params){
            Object[] list=new PartActsExecuter(params[0]).execute();
            if(list!=null) {
                ViewActivity[] mActivityList = new ViewActivity[list.length];
                for (int i = 0; i < list.length; i++) {
                    mActivityList[i] = (ViewActivity) list[i];
                }
                return mActivityList;
            }
            else
                return null;
        }

        @Override
        protected void onPostExecute(ViewActivity[] result)
        {
            if(result!=null) {
                String[] listname = new String[result.length];
                StaticVariable.mActivity = result;
                for (int i = 0; i < result.length; i++) {
                    if(result[i].getTime()!=null) {
                        listname[i] = result[i].getActivityname()+"(已确定！)";
                    }
                    else{
                        listname[i] = result[i].getActivityname();
                    }
                }
                mActivityListView.setAdapter(new ArrayAdapter(ActivityFragment.this.getActivity(),
                        R.layout.activity_text_layout, listname));
            }

        }

    }

}
