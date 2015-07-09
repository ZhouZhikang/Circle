package com.test.zhikangzhou.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.zhikangzhou.action.BuileGestureExt;
import com.test.zhikangzhou.action.StaticVariable;
import com.test.zhikangzhou.activity.R;
import com.test.zhikangzhou.client.model.ViewActivity;
import com.test.zhikangzhou.client.model.ViewCircle;
import com.test.zhikangzhou.client.model.ViewMessage;
import com.test.zhikangzhou.client.model.ViewUser;
import com.test.zhikangzhou.client.util.ActMemberExecuter;
import com.test.zhikangzhou.client.util.AddMessageExecuter;
import com.test.zhikangzhou.client.util.GetActsExecuter;
import com.test.zhikangzhou.client.util.GetGroupsExecuter;
import com.test.zhikangzhou.client.util.GetMessageExecuter;
import com.test.zhikangzhou.client.util.JoinActExecuter;
import com.test.zhikangzhou.client.util.LoginExecuter;
import com.test.zhikangzhou.client.util.PickTimeExecuter;
import com.test.zhikangzhou.fragment.CircleFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ActivityDetail extends Activity {
    private TextView mTitle;
    private TextView mOwner;
    private TextView mDescription;
    private TextView mMemberCount;
    private TextView mEnsureTime;
    private ListView mListView;
    private TextView mBelong;
    private EditText mComment;
    private ImageButton mJoinButton;
    private String[] areas ;
    private List<Integer> maplist=new ArrayList<Integer>();
    private boolean[] areaState=new boolean[]{true, false, false, false, false, false,false };
    private ListView areaCheckListView;
    private Map<Date, Integer> mPickTimeMap;
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日");
//    private GestureDetector gestureDetector;
//    private String[] strs = new String[] {
//            "first", "second", "third", "fourth", "fifth","six","seven","eight","nine","ten"
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_detail);
        init();
        dorefresh();

    }

    void init(){
        mBelong=(TextView)findViewById(R.id.tv_belong);
        mTitle= (TextView) findViewById(R.id.activity_title);
        mOwner=(TextView)findViewById(R.id.activity_owner);
        mMemberCount=(TextView)findViewById(R.id.tv_aty_count);
        mDescription=(TextView)findViewById(R.id.activity_description);
        mListView=(ListView)findViewById(R.id.listView_comment);
        mEnsureTime=(TextView)findViewById(R.id.tv_aty_ensuretime);
        mJoinButton=(ImageButton)findViewById(R.id.im_in);
        mBelong.setText("所属圈: "+StaticVariable.mCurrentActivity.getGroupname());
        mJoinButton.setOnClickListener(new CheckBoxClickListener());
        if(StaticVariable.mCurrentActivity.isMyParticipation()==true){
            mJoinButton.setImageDrawable(getDrawable(R.drawable.ic_favorite_black_48dp));
            mJoinButton.setImageTintList(getResources().getColorStateList(R.color.white));
        }
        else if(StaticVariable.mCurrentActivity.isDone()==true){
            mJoinButton.setBackgroundResource(R.drawable.ripple4);
        }
        mComment=(EditText)findViewById(R.id.activity_comment);
        mComment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    if(mComment!=null&&(!mComment.getText().toString().equals(""))) {
                        AddMessageTask amt=new AddMessageTask();
                        amt.execute(mComment.getText().toString(), StaticVariable.mCurrentUser.getUserid(), String.valueOf(StaticVariable.mCurrentActivity.getActivityid()));
                    }
                    else{
                        Toast.makeText(ActivityDetail.this.getApplicationContext(), "内容不能为空",
                                Toast.LENGTH_SHORT).show();
                    }
                    mComment.setText("");
                    dorefresh();

                }
                return false;
            }
        });
        mTitle.setText(StaticVariable.mCurrentActivity.getActivityname());
        mOwner.setText("发起人: "+StaticVariable.mCurrentActivity.getUsername());
        mDescription.setText("描述: "+StaticVariable.mCurrentActivity.getActivitydetail());
        mMemberCount.setText("已参加: "+StaticVariable.mCurrentActivity.getSize());
        if(StaticVariable.mCurrentActivity.getTime()!=null)
            mEnsureTime.setText("确定时间: "+sdf.format(StaticVariable.mCurrentActivity.getTime()));
        else
            mEnsureTime.setText("确定时间: 暂时未定");
        JoinActTask jat=new JoinActTask();
        jat.execute(String.valueOf(StaticVariable.mCurrentActivity.getActivityid()),StaticVariable.mCurrentUser.getUserid());
        mMemberCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityDetail.this, ActivityMembers.class);
                startActivity(intent);
            }
        });

    }

    void dorefresh(){
        if(StaticVariable.mCurrentActivity.isMyParticipation()==true){
            mJoinButton.setImageDrawable(getDrawable(R.drawable.ic_favorite_black_48dp));
            mJoinButton.setImageTintList(getResources().getColorStateList(R.color.white));
        }
        ActMemberTask amt=new ActMemberTask();
        amt.execute(StaticVariable.mCurrentActivity.getActivityid());
        GetMessageTask gmt=new GetMessageTask();
        gmt.execute(StaticVariable.mCurrentActivity.getActivityid());

    }


    class CheckBoxClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            if(StaticVariable.mCurrentCircle.isMyFocus()==true) {
                if (StaticVariable.mCurrentActivity.isMyParticipation() == false) {
                    if (StaticVariable.mCurrentActivity.isDone() == false) {
                        AlertDialog ad = new AlertDialog.Builder(ActivityDetail.this)
                                .setTitle("选择时间")
                                .setMultiChoiceItems(areas, areaState, new DialogInterface.OnMultiChoiceClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton, boolean isChecked) {
                                        //点击某个区域
                                    }
                                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        String s = "您选择了:";
                                        for (int i = 0; i < areas.length; i++) {
                                            if (areaCheckListView.getCheckedItemPositions().get(i)) {
                                                s += areaCheckListView.getAdapter().getItem(i) + "  ";
                                                maplist.add(getDateIcon(i));
                                            } else {
                                                areaCheckListView.getCheckedItemPositions().get(i, false);
                                            }
                                        }
                                        if (areaCheckListView.getCheckedItemPositions().size() > 0) {
//                                    Toast.makeText(ActivityDetail.this, s, Toast.LENGTH_LONG).show();
                                        } else {
                                            //没有选择
                                        }
                                        dialog.dismiss();
                                        PickTimeTask ptt = new PickTimeTask();
                                        ptt.execute(StaticVariable.mCurrentActivity.getActivityid(), StaticVariable.mCurrentUser.getUserid(), maplist);
                                        StaticVariable.mActivity[StaticVariable.mSelectedActivityPosition].setMyParticipation(true);
                                        StaticVariable.mCurrentActivity = StaticVariable.mActivity[StaticVariable.mSelectedActivityPosition];
                                        dorefresh();
                                    }
                                }).setNegativeButton("取消", null).create();
                        areaCheckListView = ad.getListView();
                        ad.show();
                    } else {
                        Toast.makeText(ActivityDetail.this, "活动已结束", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ActivityDetail.this, "你已经参加了这个活动", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(ActivityDetail.this, "请先关注圈子", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class AddMessageTask extends AsyncTask<String,Void,Object> {
        @Override
        protected Object doInBackground(String...params){
            Object[] list=new AddMessageExecuter(params[0],params[1],Integer.parseInt(params[2])).execute();
            if(list!=null && list[0] instanceof String) {
                return (String) list[0];
            }
            else
                return null;
        }

        @Override
        protected void onPostExecute(Object result){
            Toast.makeText(ActivityDetail.this.getApplicationContext(), (String)result,
                    Toast.LENGTH_SHORT).show();

        }
    }

    private class PickTimeTask extends AsyncTask<Object,Void,Object> {
        @Override
        protected Object doInBackground(Object...params){
            Object[] list=new PickTimeExecuter((int)params[0] ,(String)params[1],(List<Integer>)params[2]).execute();
            if(list!=null && list[0] instanceof String) {
                return (String) list[0];
            }
            else
                return null;
        }

        @Override
        protected void onPostExecute(Object result){
            Toast.makeText(ActivityDetail.this.getApplicationContext(), "加入成功",
                    Toast.LENGTH_SHORT).show();

        }
    }

    private class JoinActTask extends AsyncTask<String,Void,Object> {
        @Override
        protected Object doInBackground(String...params){
            Object[] list=new JoinActExecuter(Integer.parseInt(params[0]),params[1]).execute();
            if(list!=null) {
                return list[0];
            }
            else
                return null;
        }

        @Override
        protected void onPostExecute(Object result){
            if (result != null) {
                if(result instanceof Map) {
                    mPickTimeMap = (Map<Date, Integer>) result;
                    int i = 0;
                    areas = new String[mPickTimeMap.size()];
                    for (Date date : mPickTimeMap.keySet()) {
                        areas[i++] = sdf.format(date);
                    }
                }
            }

        }
    }

    private class GetMessageTask extends AsyncTask<Integer,Void,ViewMessage[]> {
        @Override
        protected ViewMessage[] doInBackground(Integer...params){
            Object[] list=new GetMessageExecuter(params[0]).execute();
            if(list!=null) {
                ViewMessage[] result = new ViewMessage[list.length];
                int i = 0;
                for (Object o : list) {
                    result[i++] = (ViewMessage) o;
                }
                return result;
            }
            else
                return null;
        }

        @Override
        protected void onPostExecute(ViewMessage[] result){
            if(result!=null) {
                String[] comment = new String[result.length];
                for (int i = 0; i < result.length; i++) {
                    comment[i] = result[i].getUsername() +" "+ result[i].getSendtime() + "\n" + result[i].getContent();
                }
                mListView.setAdapter(new ArrayAdapter(ActivityDetail.this,
                        R.layout.comment, comment));
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


    private int getDateIcon(int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        for (Date date : mPickTimeMap.keySet()) {
            if (sdf.format(date).equals(areas[i])) {
                return mPickTimeMap.get(date);
            }
        }
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_detail, menu);
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
