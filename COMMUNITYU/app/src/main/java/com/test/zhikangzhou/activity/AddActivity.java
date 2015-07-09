package com.test.zhikangzhou.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.test.zhikangzhou.action.StaticVariable;
import com.test.zhikangzhou.activity.R;
import com.test.zhikangzhou.client.util.AddActExecuter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddActivity extends Activity {
    private TextView myTextView;
    private EditText mTitle;
    private EditText mContent;
    private Spinner mySpinner;
    private Date date1;
    private Date date2;
    private Date date3;
    private int mSelectedCircle;
    private ArrayAdapter<String> adapter;
    private TextView getTime1;
    private TextView getTime2;
    private TextView getTime3;
    private Calendar calendar1;// 用来装日期的
    private Calendar calendar2;// 用来装日期的
    private Calendar calendar3;// 用来装日期的
    private FloatingActionButton addButton;
    private DatePickerDialog dialog;
    private DatePickerDialog dialog2;
    private DatePickerDialog dialog3;
    private List<Date> datelist=new ArrayList<Date>();
    String[] listname = new String[StaticVariable.mCircles.length];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        init();
    }

    void init(){
        mTitle=(EditText)findViewById(R.id.activity_setTitle);
        mContent=(EditText)findViewById(R.id.add_content);
        addButton=(FloatingActionButton)findViewById(R.id.confirm_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTitle.getText()==null||mTitle.getText().toString().equals("")){
                    Toast.makeText(AddActivity.this.getApplicationContext(), "标题不能为空",
                            Toast.LENGTH_SHORT).show();
                }
                else if(mContent.getText()==null||mContent.getText().toString().equals("")){
                    Toast.makeText(AddActivity.this.getApplicationContext(), "内容不能为空",
                            Toast.LENGTH_SHORT).show();
                }
                else if(date1==null&&date2==null&&date3==null){
                    Toast.makeText(AddActivity.this.getApplicationContext(), "请至少选择一个时间",
                            Toast.LENGTH_SHORT).show();
                }else {
                    AddActTask aat = new AddActTask();
                    aat.execute(mTitle.getText().toString(), mContent.getText().toString(), mSelectedCircle, StaticVariable.mCurrentUser.getUserid(), datelist);
                    finish();
                }
            }
        });
        myTextView = (TextView)findViewById(R.id.TextView_circle);
        mySpinner = (Spinner)findViewById(R.id.Spinner_circle);
        getTime1 = (TextView) findViewById(R.id.time1);
        getTime1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calendar1 = Calendar.getInstance();
                dialog = new DatePickerDialog(AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                System.out.println("年-->" + year + "月-->"
                                        + monthOfYear + "日-->" + dayOfMonth);
                                getTime1.setText(year + "/" + monthOfYear + "/"
                                        + dayOfMonth);
                                calendar1.set(year, monthOfYear, dayOfMonth);
                                date1 = calendar1.getTime();
                                datelist.add(date1);
                                Log.i("111111111111111111", date1.toString());
                            }
                        }, calendar1.get(Calendar.YEAR), calendar1
                        .get(Calendar.MONTH), calendar1
                        .get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        getTime2 = (TextView) findViewById(R.id.time2);
        getTime2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calendar2 = Calendar.getInstance();
                dialog2 = new DatePickerDialog(AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                System.out.println("年-->" + year + "月-->"
                                        + monthOfYear + "日-->" + dayOfMonth);
                                getTime2.setText(year + "/" + monthOfYear + "/"
                                        + dayOfMonth);
                                calendar2.set(year,monthOfYear,dayOfMonth);
                                date2=calendar2.getTime();
                                datelist.add(date2);
                                Log.i("111111111111111111",date2.toString());
                            }
                        }, calendar2.get(Calendar.YEAR), calendar2
                        .get(Calendar.MONTH), calendar2
                        .get(Calendar.DAY_OF_MONTH));
                dialog2.show();
            }
        });
        getTime3 = (TextView) findViewById(R.id.time3);
        getTime3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calendar3 = Calendar.getInstance();
                dialog3 = new DatePickerDialog(AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                System.out.println("年-->" + year + "月-->"
                                        + monthOfYear + "日-->" + dayOfMonth);
                                getTime3.setText(year + "/" + monthOfYear + "/"
                                        + dayOfMonth);
                                calendar3.set(year, monthOfYear, dayOfMonth);
                                date3=calendar3.getTime();
                                datelist.add(date3);
                                Log.i("111111111111111111",date3.toString());
                            }
                        }, calendar3.get(Calendar.YEAR), calendar3
                        .get(Calendar.MONTH), calendar3
                        .get(Calendar.DAY_OF_MONTH));
                dialog3.show();
            }
        });

        for (int i = 0; i < StaticVariable.mCircles.length; i++) {
            listname[i] = StaticVariable.mCircles[i].getGroupname();
        }
        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listname);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        mySpinner.setAdapter(adapter);
        //第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
        mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                /* 将所选mySpinner 的值带入myTextView 中*/
                myTextView.setText("圈子: "+ adapter.getItem(arg2));
                                for(int i=0;i<StaticVariable.mCircles.length;i++){
                    if(adapter.getItem(arg2).equals(StaticVariable.mCircles[i].getGroupname())){
                        mSelectedCircle=StaticVariable.mCircles[i].getGroupid();
                        break;
                    }
                }
                /* 将mySpinner 显示*/
                arg0.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                myTextView.setText("NONE");
                arg0.setVisibility(View.VISIBLE);
            }
        });
        /*下拉菜单弹出的内容选项触屏事件处理*/
        mySpinner.setOnTouchListener(new Spinner.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                /**
                 *
                 */
                return false;
            }
        });
        /*下拉菜单弹出的内容选项焦点改变事件处理*/
        mySpinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener(){
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

            }
        });
    }

    private class AddActTask extends AsyncTask<Object,Void,String> {
        @Override
        protected String doInBackground(Object...params){
            String name = (String) params[0];
            String detail = (String) params[1];
            int gid = (int) params[2];
            String uid = (String) params[3];
            List<Date> dl = (List<Date>) params[4];
            Object[] list=new AddActExecuter(name, detail, gid, uid, dl).execute();
            if(list!=null && list[0] instanceof String) {
                return (String) list[0];
            }
            else
                return null;
        }

        @Override
        protected void onPostExecute(String result){
            Toast.makeText(AddActivity.this, "创建活动成功",
                    Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
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
