package com.test.zhikangzhou.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.test.zhikangzhou.action.StaticVariable;
import com.test.zhikangzhou.activity.R;
import com.test.zhikangzhou.client.model.ViewActivity;
import com.test.zhikangzhou.client.model.ViewTimetable;
import com.test.zhikangzhou.client.model.ViewUser;
import com.test.zhikangzhou.client.util.ActMemberExecuter;
import com.test.zhikangzhou.client.util.ModifyActExecuter;
import com.test.zhikangzhou.client.util.MyActsExecuter;
import com.test.zhikangzhou.client.util.ViewTimeExecuter;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyActManager extends Activity {
    private TextView mMyTitle;
    private TextView mMyOwner;
    private EditText mMyDescription;
    private RadioGroup mGroup;
    private Button mButton;
    private TextView mBelong;
    private TextView mCount;
    private TextView mTime;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private Map<Integer,Date> mMap=new HashMap<>();
    private static int i=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_act_manager);
        init();
    }

    void init(){
        mMyTitle=(TextView)findViewById(R.id.my_aty_title);
        mMyOwner=(TextView)findViewById(R.id.my_aty_owner);
        mGroup=(RadioGroup)findViewById(R.id.radioGroup_time);
        mButton=(Button)findViewById(R.id.btn_sure);
        mBelong=(TextView)findViewById(R.id.tv_my_belong);
        mCount=(TextView)findViewById(R.id.tv_my_count);
        mCount.setText("人数:"+StaticVariable.mCurrentActivity.getSize());
        ActMemberTask amt=new ActMemberTask();
        amt.execute(StaticVariable.mCurrentActivity.getActivityid());
        mCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActManager.this, ActivityMembers.class);
                startActivity(intent);
            }
        });
        mTime=(TextView)findViewById(R.id.tv_my_ensuretime);
        if(StaticVariable.mCurrentActivity.getTime()!=null)
            mTime.setText("确定时间: "+sdf.format(StaticVariable.mCurrentActivity.getTime()));
        else
            mTime.setText("确定时间: 暂时未定");
        mBelong.setText("所属圈子: "+StaticVariable.mCurrentActivity.getGroupname());
        ViewTimeTask vmt = new ViewTimeTask();
        vmt.execute(StaticVariable.mCurrentActivity.getActivityid());
        mMyDescription=(EditText)findViewById(R.id.tv_my_detail);
        mMyDescription.setText(StaticVariable.mCurrentActivity.getActivitydetail());
        mMyTitle.setText(StaticVariable.mCurrentActivity.getActivityname());
        mMyOwner.setText("发起人: "+StaticVariable.mCurrentActivity.getUsername());
        mMyDescription.setText(StaticVariable.mCurrentActivity.getActivitydetail());
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mGroup.getCheckedRadioButtonId()!=-1) {
                    ModifyActTsk mat = new ModifyActTsk();
//                    Log.i("xxxxxxxxxx",String.valueOf(mGroup.getCheckedRadioButtonId()));
//                    Log.i("xxxxxxxxxx",String.valueOf(mMap.get(1)));
//                    Toast.makeText(MyActManager.this.getApplicationContext(), sdf.format(mMap.get(mGroup.getCheckedRadioButtonId())), Toast.LENGTH_SHORT).show();
                    mat.execute(mMyDescription.getText().toString(),mMap.get(mGroup.getCheckedRadioButtonId()),StaticVariable.mCurrentActivity.getActivityid());
                    MyActsTask mat2=new MyActsTask();
                    mat2.execute(StaticVariable.mCurrentUser.getUserid());
                }
                else{
                    Toast.makeText(MyActManager.this.getApplicationContext(), "请确定时间！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class ViewTimeTask extends AsyncTask<Integer,Void,ViewTimetable> {
        @Override
        protected ViewTimetable doInBackground(Integer...params){
            Object[] list=new ViewTimeExecuter(params[0]).execute();
            if(list!=null) {
                return (ViewTimetable)list[0];
            }
            else
                return null;
        }

        @Override
        protected void onPostExecute(ViewTimetable result){
            Map<Date, Integer> map = result.getAvailableList();
            Set<Date> set = map.keySet();
            mGroup.removeAllViewsInLayout();
            for (Date date : set)
            {
                mMap.put(i,date);
                i++;
                RadioButton tempButton = new RadioButton(MyActManager.this);
//            tempButton.setBackgroundResource(R.drawable.xxx);	// 设置RadioButton的背景图片
//            tempButton.setButtonDrawable(R.drawable.);		// 设置按钮的样式
                tempButton.setPadding(80, 0, 0, 0);           		// 设置文字距离按钮四周的距离
                tempButton.setText(sdf.format(date) + " : " + map.get(date) + "人");
                mGroup.addView(tempButton);
            }
        }
    }

    private class ModifyActTsk extends AsyncTask<Object,Void,String> {
        @Override
        protected String doInBackground(Object...params){
            Object[] list=new ModifyActExecuter((String)params[0],(Date)params[1],(int)params[2]).execute();
            if(list!=null&&list[0] instanceof String) {
                return (String)list[0];
            }
            else
                return null;
        }

        @Override
        protected void onPostExecute(String result){
            Toast.makeText(getApplication(),"发布成功！",Toast.LENGTH_SHORT);
           finish();
        }
    }

    private class MyActsTask extends AsyncTask<String, Void, ViewActivity[]> {
        @Override
        protected ViewActivity[] doInBackground(String... params) {
            Object[] list = new MyActsExecuter(params[0]).execute();
            if (list != null) {
                ViewActivity[] mActsList = new ViewActivity[list.length];
                for (int i = 0; i < list.length; i++) {
                    mActsList[i] = (ViewActivity) list[i];
                }
                return mActsList;
            } else
                return null;
        }

        @Override
        protected void onPostExecute(ViewActivity[] result) {
            if (result != null) {
                StaticVariable.mActivity=result;
                String[] name=new String[result.length];
                for (int i = 0; i < result.length; i++) {
                    name[i]=result[i].getActivityname();
                }
                MyActActivity.mMyActListView.setAdapter(new ArrayAdapter<String>(MyActActivity.mMyActListView.getContext(), R.layout.activity_text_layout, name));
            }
        }
    }

    private class ActMemberTask extends AsyncTask<Integer,Void,ViewUser[]> {
        @Override
        protected ViewUser[] doInBackground(Integer...params){
            Object[] list=new ActMemberExecuter(params[0]).execute();
            if(list!=null) {
                ViewUser[] result = new ViewUser[list.length];
                for (int i=0;i<result.length;i++) {
                    result[i] = (ViewUser)list[i];
                }
                return result;
            }
            else
                return null;
        }

        @Override
        protected void onPostExecute(ViewUser[] result){
            StaticVariable.sActivityUsers=result;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_act_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
