package com.test.zhikangzhou.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.test.zhikangzhou.action.StaticVariable;
import com.test.zhikangzhou.activity.R;
import com.test.zhikangzhou.client.model.ViewActivity;
import com.test.zhikangzhou.client.model.ViewCircle;
import com.test.zhikangzhou.client.util.MyActsExecuter;
import com.test.zhikangzhou.client.util.MyGroupsExecuter;

public class MyCircleActivity extends Activity {
    private ListView mMyCircles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_circle);
        init();
    }

    void init(){
        mMyCircles=(ListView)findViewById(R.id.lv_my_circle);
        MyGroupTask mgt=new MyGroupTask();
        mgt.execute(StaticVariable.mCurrentUser.getUserid());
        mMyCircles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StaticVariable.mCurrentCircle=StaticVariable.mCircles[position];
                Intent intent = new Intent(MyCircleActivity.this, CircleDetail.class);
                startActivity(intent);
            }
        });
    }

    private class MyGroupTask extends AsyncTask<String, Void, ViewCircle[]> {
        @Override
        protected ViewCircle[] doInBackground(String... params) {
            Object[] list = new MyGroupsExecuter(params[0]).execute();
            if (list != null) {
                ViewCircle[] mCircleList = new ViewCircle[list.length];
                for (int i = 0; i < list.length; i++) {
                    mCircleList[i] = (ViewCircle) list[i];
                }
                return mCircleList;
            } else
                return null;
        }

        @Override
        protected void onPostExecute(ViewCircle[] result) {
            if (result != null) {
                StaticVariable.mCircles=result;
                String[] name=new String[result.length];
                for (int i = 0; i < result.length; i++) {
                    name[i]=result[i].getGroupname();
                }
                mMyCircles.setAdapter(new ArrayAdapter<String>(MyCircleActivity.this,R.layout.activity_text_layout,name));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_circle, menu);
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
