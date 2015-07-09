package com.test.zhikangzhou.activity;

import android.app.Activity;
import android.content.Intent;
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

public class ActivityMembers extends Activity {
    private ListView mMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_members);
        mMembers=(ListView)findViewById(R.id.lv_aty_members);
        String[] listname = new String[StaticVariable.sActivityUsers.length];
        for (int i = 0; i < StaticVariable.sActivityUsers.length; i++) {
            listname[i] = StaticVariable.sActivityUsers[i].getUsername();
        }
        mMembers.setAdapter(new ArrayAdapter<String>(ActivityMembers.this,
                R.layout.activity_text_layout, listname));
        mMembers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StaticVariable.sSelectedUser=StaticVariable.sActivityUsers[position];
                Intent intent = new Intent(ActivityMembers.this, UserDetail.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_members, menu);
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
