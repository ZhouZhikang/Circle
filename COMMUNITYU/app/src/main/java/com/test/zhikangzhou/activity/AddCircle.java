package com.test.zhikangzhou.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.test.zhikangzhou.action.StaticVariable;
import com.test.zhikangzhou.activity.R;
import com.test.zhikangzhou.client.model.ViewUser;
import com.test.zhikangzhou.client.util.AddGroupExecuter;
import com.test.zhikangzhou.client.util.LoginExecuter;

public class AddCircle extends Activity {
    private EditText mCircleTitle;
    private EditText mCircleContent;
    private Button mAddCircleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_circle);
        init();
    }

    void init() {
        mCircleTitle = (EditText) findViewById(R.id.circle_setTitle);
        mCircleContent = (EditText) findViewById(R.id.circle_setContent);
        mAddCircleButton = (Button) findViewById(R.id.btn_add_circle);
        mAddCircleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCircleTitle.getText() == null || mCircleTitle.getText().toString().equals("")) {
                    Toast.makeText(AddCircle.this.getApplicationContext(), "圈子名不能为空",
                            Toast.LENGTH_SHORT).show();
                } else if (mCircleContent.getText() == null || mCircleContent.getText().toString().equals("")) {
                    Toast.makeText(AddCircle.this.getApplicationContext(), "简介不能为空",
                            Toast.LENGTH_SHORT).show();
                } else {
                    AddGroupTask agt = new AddGroupTask();
                    agt.execute(mCircleTitle.getText().toString(), mCircleContent.getText().toString(), StaticVariable.mCurrentUser.getUserid());
                    finish();
                }
            }
        });
    }


    private class AddGroupTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            Object[] list = new AddGroupExecuter(params[0], params[1], params[2]).execute();
            if (list != null && list[0] instanceof String) {
                return (String) list[0];
            } else
                return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(AddCircle.this, "创建圈子成功",
                    Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_circle, menu);
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
