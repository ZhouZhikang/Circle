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
import com.test.zhikangzhou.client.model.ViewCircle;
import com.test.zhikangzhou.client.util.SearchGroupExecuter;
import com.test.zhikangzhou.fragment.CircleFragment;

public class ResultList extends Activity {
    private ListView mResult;
    private ViewCircle[] mCircles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);
        SearchGroupTask sgt=new SearchGroupTask();
        mResult=(ListView)findViewById(R.id.listView_result);
        sgt.execute(StaticVariable.mSearchWord, StaticVariable.mCurrentUser.getUserid());
        mResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StaticVariable.mCurrentCircle = mCircles[position];
                Intent intent = new Intent(ResultList.this, CircleDetail.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result_list, menu);
        return true;
    }

    private class SearchGroupTask extends AsyncTask<String,Void,ViewCircle[]> {
        @Override
        protected ViewCircle[] doInBackground(String... params){
            Object[] list=new SearchGroupExecuter(params[0],params[1]).execute();
            ViewCircle[] mCircleList=new ViewCircle[list.length];
            for (int i=0;i<list.length;i++){
                mCircleList[i]= (ViewCircle)list[i];
            }
            return mCircleList;
        }

        @Override
        protected void onPostExecute(ViewCircle[] result)
        {
            mCircles=result;
            String[] listname=new String[result.length];
            for(int i=0;i<result.length;i++) {
                listname[i]=result[i].getGroupname();
            }
            mResult.setAdapter(new ArrayAdapter<String>(ResultList.this,
                    R.layout.circle_text_layout, listname));

        }

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
