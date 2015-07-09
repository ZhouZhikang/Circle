package com.test.zhikangzhou.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.test.zhikangzhou.action.StaticVariable;
import com.test.zhikangzhou.client.model.ViewUser;
import com.test.zhikangzhou.client.util.LoginExecuter;


public class LoginActivity extends Activity {
    private EditText mAccount;
    private EditText mPassword;
    private Button mLoginButton;
    private CheckBox mRemember;
    private String userNameValue,passwordValue;
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        if(sp==null){
            SharedPreferences.Editor ed = sp.edit();
            ed.putBoolean("AUTO_LOGIN", false).commit();
        }else if(StaticVariable.isLog==true&&sp.getBoolean("AUTO_LOGIN", true)){
                LoginTask lg = new LoginTask();
                lg.execute(sp.getString("USER_NAME", ""), sp.getString("PASSWORD", ""));
        }else{
            initView();
//            ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//            int heapSize = manager.getMemoryClass();
//            Log.i("xxxxxxxxxx", String.valueOf(heapSize));
        }
    }

    private class LoginTask extends AsyncTask<String,Void,Object>{
        @Override
        protected Object doInBackground(String...params){
            Object[] list=new LoginExecuter(params[0],params[1]).execute();
            if(list!=null) {
                return list[0];
            }
            else
                return null;
        }

        @Override
        protected void onPostExecute(Object result){
            if(result!=null&&result instanceof ViewUser){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                StaticVariable.mCurrentUser=(ViewUser)result;
            }
            else if(result instanceof String){

                Toast.makeText(LoginActivity.this.getApplicationContext(), (String)result,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initView(){
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        mAccount=(EditText)findViewById(R.id.account);
        mPassword=(EditText)findViewById(R.id.passsword);
        mRemember=(CheckBox)findViewById(R.id.cb_login);
        mAccount.setText(sp.getString("USER_NAME", ""));
        mPassword.setText(sp.getString("PASSWORD", ""));
        mLoginButton=(Button)findViewById(R.id.loginButton);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameValue = mAccount.getText().toString();
                passwordValue = mPassword.getText().toString();
                if(userNameValue!=null&&!userNameValue.equals("")) {
                    if(passwordValue!=null&&!passwordValue.equals("")) {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("USER_NAME", userNameValue);
                        LoginTask lt = new LoginTask();
                        if (mRemember.isChecked() == true) {
                            editor.putString("PASSWORD", passwordValue);
                            editor.putBoolean("AUTO_LOGIN", true).commit();
                            StaticVariable.isLog=true;
                        } else {
                            editor.putString("PASSWORD", "");
                            editor.putBoolean("AUTO_LOGIN", false).commit();
                        }
                        editor.commit();
                        lt.execute(mAccount.getText().toString(), mPassword.getText().toString());
                    }
                    else{
                        Toast.makeText(LoginActivity.this.getApplicationContext(), "密码不能为空",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this.getApplicationContext(), "账号名不能为空",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
