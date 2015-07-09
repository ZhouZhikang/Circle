package com.test.zhikangzhou.activity;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.test.zhikangzhou.action.StaticVariable;
import com.test.zhikangzhou.activity.R;

public class UserDetail extends Activity {
    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;
    private TextView mTextView4;
    private TextView mTextView5;
    private TextView mTextView6;
    private TextView mTextView7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        init();
    }

    void init(){
        mTextView1=(TextView)findViewById(R.id.textView12);
        mTextView2=(TextView)findViewById(R.id.textView13);
        mTextView3=(TextView)findViewById(R.id.textView14);
        mTextView4=(TextView)findViewById(R.id.textView15);
        mTextView5=(TextView)findViewById(R.id.textView16);
        mTextView6=(TextView)findViewById(R.id.textView17);
        mTextView7=(TextView)findViewById(R.id.textView18);
        mTextView1.setText(StaticVariable.sSelectedUser.getUserid());
        mTextView2.setText(StaticVariable.sSelectedUser.getUsername());
        mTextView3.setText(StaticVariable.sSelectedUser.getUserschool());
        mTextView4.setText(StaticVariable.sSelectedUser.getUserspecial());
        mTextView5.setText(StaticVariable.sSelectedUser.getUserclass());
        mTextView6.setText(StaticVariable.sSelectedUser.getDorm());
        mTextView7.setText(StaticVariable.sSelectedUser.getRoom());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_detail, menu);
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
