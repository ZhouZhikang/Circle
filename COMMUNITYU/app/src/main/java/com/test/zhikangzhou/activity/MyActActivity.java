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
import com.test.zhikangzhou.client.util.GetGroupsExecuter;
import com.test.zhikangzhou.client.util.MyActsExecuter;

public class MyActActivity extends Activity {
    public static ListView mMyActListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_act);
        init();
    }

    void init() {
        mMyActListView = (ListView) findViewById(R.id.lv_my_aty);
        MyActsTask mat=new MyActsTask();
        mat.execute(StaticVariable.mCurrentUser.getUserid());
        mMyActListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StaticVariable.mCurrentActivity=StaticVariable.mActivity[position];
                Intent intent = new Intent(MyActActivity.this, MyActManager.class);
                startActivity(intent);
            }
        });
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
                mMyActListView.setAdapter(new ArrayAdapter<String>(MyActActivity.this,R.layout.activity_text_layout,name));
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_act, menu);
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
