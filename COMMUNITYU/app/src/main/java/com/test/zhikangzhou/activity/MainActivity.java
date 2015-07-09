package com.test.zhikangzhou.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Outline;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.test.zhikangzhou.client.util.GetGroupsExecuter;
import com.test.zhikangzhou.client.util.LoginExecuter;
import com.test.zhikangzhou.fragment.ActivityFragment;
import com.test.zhikangzhou.fragment.CircleFragment;
import com.test.zhikangzhou.fragment.MessageFragment;
import com.test.zhikangzhou.fragment.MyFragment;

import java.util.Objects;


public class MainActivity extends Activity implements View.OnClickListener{
    private LinearLayout mTab;
    private LinearLayout mTabCircle;
    private LinearLayout mTabActivity;
    private LinearLayout mTabMessage;
    private LinearLayout mTabMy;
    private TextView mTxCircle;
    private TextView mTxActivity;
    private TextView mTxMessage;
    private TextView mTxMy;
    private Fragment mTab01;
    private Fragment mTab02;
    private Fragment mTab03;
    private Fragment mTab04;
    private FloatingActionButton mAddActivity;
    private FloatingActionButton mAddCircle;
    private FloatingActionsMenu mAddButton;
    public FragmentManager fm=getFragmentManager();
    public FragmentTransaction trans=fm.beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        setSelect(0);
    }



    private void initView(){
        mTab=(LinearLayout)findViewById(R.id.id_tab);
        mTabCircle=(LinearLayout)findViewById(R.id.id_tab01);
        mTabActivity=(LinearLayout)findViewById(R.id.id_tab02);
        mTabMessage=(LinearLayout)findViewById(R.id.id_tab03);
        mTabMy=(LinearLayout)findViewById(R.id.id_tab04);
        mTxCircle=(TextView)findViewById(R.id.tv_circle);
        mTxActivity=(TextView)findViewById(R.id.tv_activity);
        mTxMessage=(TextView)findViewById(R.id.tv_message);
        mTxMy=(TextView)findViewById(R.id.tv_my);
        mAddButton=(FloatingActionsMenu)findViewById(R.id.multiple_actions);
        mAddActivity=(FloatingActionButton)findViewById(R.id.add_activity);
        mAddCircle=(FloatingActionButton)findViewById(R.id.add_circle);
    }

    private void initEvent(){
        mTabCircle.setOnClickListener(this);
        mTabActivity.setOnClickListener(this);
        mTabMy.setOnClickListener(this);
        mAddActivity.setOnClickListener(this);
        mAddCircle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tab01:
                setSelect(0);
                break;
            case R.id.id_tab02:
                setSelect(1);
                break;
            case R.id.id_tab03:
                setSelect(2);
                break;
            case R.id.id_tab04:
                setSelect(3);
                break;
            case R.id.add_activity:{
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
                break;
            case R.id.add_circle:{
                Intent intent = new Intent(MainActivity.this, AddCircle.class);
                startActivity(intent);
            }
            break;
        }
    }

    public void hidFragment(FragmentTransaction trans) {
        if(mTab01!=null){
            trans.hide(mTab01);
        }
        if(mTab02!=null){
            trans.hide(mTab02);
        }
        if(mTab03!=null){
            trans.hide(mTab03);
        }
        if(mTab04!=null){
            trans.hide(mTab04);
        }
    }

    private void setSelect(int i)
    {
        fm=getFragmentManager();
        trans=fm.beginTransaction();
        switch (i){
            case 0:
                mTab01=new CircleFragment();
                trans.replace(R.id.id_fragment,mTab01);
                mTxCircle.setTextColor(Color.parseColor("#FF4000"));
                mTxActivity.setTextColor(Color.parseColor("#A4A4A4"));
                mTxMy.setTextColor(Color.parseColor("#A4A4A4"));
                break;
            case 1:
                mTab02=new ActivityFragment();
                trans.replace(R.id.id_fragment,mTab02);
                mTxActivity.setTextColor(Color.parseColor("#FF4000"));
                mTxCircle.setTextColor(Color.parseColor("#A4A4A4"));
                mTxMy.setTextColor(Color.parseColor("#A4A4A4"));
                break;
            case 2:
                mTab03=new MessageFragment();
                trans.replace(R.id.id_fragment,mTab03);
                mTxCircle.setTextColor(Color.parseColor("#A4A4A4"));
                mTxActivity.setTextColor(Color.parseColor("#A4A4A4"));
                mTxMy.setTextColor(Color.parseColor("#A4A4A4"));
                break;
            case 3:
                mTab04=new MyFragment();
                trans.replace(R.id.id_fragment,mTab04);
                mTxMy.setTextColor(Color.parseColor("#FF4000"));
                mTxCircle.setTextColor(Color.parseColor("#A4A4A4"));
                mTxActivity.setTextColor(Color.parseColor("#A4A4A4"));
                break;
        }
        trans.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
