package com.test.zhikangzhou.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.test.zhikangzhou.action.BaseActivity;
import com.test.zhikangzhou.action.StaticVariable;
import com.test.zhikangzhou.activity.R;
import com.test.zhikangzhou.client.model.ViewActivity;
import com.test.zhikangzhou.client.model.ViewCircle;
import com.test.zhikangzhou.client.model.ViewUser;
import com.test.zhikangzhou.client.util.ActMemberExecuter;
import com.test.zhikangzhou.client.util.GetActsExecuter;
import com.test.zhikangzhou.client.util.GetGroupsExecuter;
import com.test.zhikangzhou.client.util.GroupMemberExecuter;
import com.test.zhikangzhou.client.util.JoinGroupExecuter;
import com.test.zhikangzhou.client.util.OutGroupExecuter;
import com.test.zhikangzhou.fragment.ActivityFragment;
import com.test.zhikangzhou.fragment.CircleFragment;

public class CircleDetail extends BaseActivity implements ObservableScrollViewCallbacks {
    private static final float MAX_TEXT_SCALE_DELTA = 0.3f;
    private LinearLayout mDes;
    private View mOverlayView;
    private TextView mTitle;
    private View mLike;
    private TextView mDescription;
    private TextView mOwner;
    private TextView mCount;
    private int mActionBarSize;
    private ObservableListView mListView;
    private int mFlexibleSpaceShowFabOffset;
    private int mFlexibleSpaceImageHeight;
    private View mListBackgroundView;
    private int mFabMargin;
    private boolean mFabIsShown;
    private static boolean isOnClick=true;
    private String[] strs = new String[]{
            "还未有活动"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_detail);
        init();
    }

    void init() {
        mTitle = (TextView) findViewById(R.id.circle_title);
        mOwner = (TextView) findViewById(R.id.circle_owner);
        mDescription = (TextView) findViewById(R.id.circle_description);
        mLike = (View) findViewById(R.id.button_like);
        mCount = (TextView) findViewById(R.id.tv_circle_count);
//        mListView = (ListView) findViewById(R.id.listView_circle_detail);
        mDes=(LinearLayout)findViewById(R.id.Title_bar);
        mFlexibleSpaceImageHeight = 550;
        mFlexibleSpaceShowFabOffset = getResources().getDimensionPixelSize(R.dimen.flexible_space_show_fab_offset);
        mActionBarSize = getActionBarSize();
        mOverlayView = findViewById(R.id.overlay);
        mListView = (ObservableListView) findViewById(R.id.list);
        mListView.setScrollViewCallbacks(this);
        View paddingView = new View(this);
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                mFlexibleSpaceImageHeight);
        paddingView.setLayoutParams(lp);
        paddingView.setClickable(true);
        mListView.addHeaderView(paddingView);
        mListBackgroundView = findViewById(R.id.list_background);
        mFabMargin = getResources().getDimensionPixelSize(R.dimen.margin_standard);
        ViewHelper.setScaleX(mLike, 0);
        ViewHelper.setScaleY(mLike, 0);



        mTitle.setText(StaticVariable.mCurrentCircle.getGroupname());
        mDescription.setText("简介: " + StaticVariable.mCurrentCircle.getInstraction());
        mOwner.setText("圈主: " + StaticVariable.mCurrentCircle.getOwnername());
        GroupMemberTask gmt = new GroupMemberTask();
        gmt.execute(StaticVariable.mCurrentCircle.getGroupid());
        if (StaticVariable.mCurrentCircle.isMyFocus() == true) {
            mLike.setBackgroundResource(R.drawable.ripple2);
        }
        else{
            mLike.setBackgroundResource(R.drawable.ripple4);
        }
        mLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StaticVariable.mCurrentCircle.isMyFocus() == false) {
                    JoinGroupTask jgt = new JoinGroupTask();
                    jgt.execute(String.valueOf(StaticVariable.mCurrentCircle.getGroupid()), StaticVariable.mCurrentUser.getUserid());

                } else {
                    OutGroupTask ogt = new OutGroupTask();
                    ogt.execute(String.valueOf(StaticVariable.mCurrentCircle.getGroupid()),StaticVariable.mCurrentUser.getUserid());
                }
                GetGroupsTask ggt=new GetGroupsTask();
                ggt.execute(StaticVariable.mCurrentUser.getUserid());
            }
        });
        GetActsTask gat = new GetActsTask();
        gat.execute(String.valueOf(StaticVariable.mCurrentCircle.getGroupid()), StaticVariable.mCurrentUser.getUserid());
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isOnClick==true) {
                    StaticVariable.mCurrentActivity = StaticVariable.mActivity[position - 1];
                    StaticVariable.mSelectedActivityPosition = position - 1;
                    Log.i("XXXXXXXXXXXXX", String.valueOf(StaticVariable.mSelectedActivityPosition));
                    Intent intent = new Intent(CircleDetail.this, ActivityDetail.class);
                    startActivity(intent);
                }
            }
        });
        mCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CircleDetail.this, CircleMembers.class);
                startActivity(intent);
            }
        });

    }

    private class GetActsTask extends AsyncTask<String, Void, ViewActivity[]> {
        @Override
        protected ViewActivity[] doInBackground(String... params) {
            Object[] list = new GetActsExecuter(Integer.parseInt(params[0]), params[1]).execute();
            if (list != null) {
                ViewActivity[] mActivityList = new ViewActivity[list.length];
                for (int i = 0; i < list.length; i++) {
                    mActivityList[i] = (ViewActivity) list[i];
                }
                return mActivityList;
            } else
                return null;
        }

        @Override
        protected void onPostExecute(ViewActivity[] result) {
            if (result != null) {
                String[] listname = new String[result.length];
                StaticVariable.mActivity = result;
                for (int i = 0; i < result.length; i++) {
                    listname[i] = result[i].getActivityname();
                }
                mListView.setAdapter(new ArrayAdapter<String>(CircleDetail.this,
                        R.layout.activity_text_layout, listname));
                isOnClick=true;
            }
            else{
                mListView.setAdapter(new ArrayAdapter<String>(CircleDetail.this,
                        R.layout.activity_text_layout, strs));
                isOnClick=false;
            }
        }
    }

    private class GroupMemberTask extends AsyncTask<Integer, Void, ViewUser[]> {
        @Override
        protected ViewUser[] doInBackground(Integer... params) {
            Object[] list = new GroupMemberExecuter(params[0]).execute();
            if (list != null) {
                ViewUser[] result = new ViewUser[list.length];
                for (int i = 0; i < result.length; i++) {
                    result[i] = (ViewUser) list[i];
                }
                return result;
            } else
                return null;
        }

        @Override
        protected void onPostExecute(ViewUser[] result) {
            StaticVariable.sCircleUsers = result;
            mCount.setText("已关注: " + result.length);
        }
    }

    private class JoinGroupTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            Object[] result = new JoinGroupExecuter(Integer.parseInt(params[0]), params[1]).execute();
            if (result != null) {
                return String.valueOf(result);
            } else
                return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                StaticVariable.mCurrentCircle.setMyFocus(true);
                mLike.setBackgroundResource(R.drawable.ripple2);
                Toast.makeText(CircleDetail.this.getApplicationContext(), "关注成功",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class OutGroupTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            Object[] result = new OutGroupExecuter(Integer.parseInt(params[0]), params[1]).execute();
            if (result != null) {
                return String.valueOf(result);
            } else
                return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                StaticVariable.mCurrentCircle.setMyFocus(false);
                mLike.setBackgroundResource(R.drawable.ripple4);
            }
        }
    }

    private class GetGroupsTask extends AsyncTask<String,Void,ViewCircle[]> {
        @Override
        protected ViewCircle[] doInBackground(String... params){
            Object[] list=new GetGroupsExecuter(params[0]).execute();
            if(list!=null) {
                ViewCircle[] mCircleList = new ViewCircle[list.length];
                for (int i = 0; i < list.length; i++) {
                    mCircleList[i] = (ViewCircle) list[i];
                }
                return mCircleList;
            }
            else
                return null;
        }

        @Override
        protected void onPostExecute(ViewCircle[] result)
        {
            if(result!=null) {
                String[] listname = new String[result.length];
                StaticVariable.mCircles = result;
                for (int i = 0; i < result.length; i++) {
                    listname[i] = result[i].getGroupname();
                }
                CircleFragment.mCircleListView.setAdapter(new ArrayAdapter<String>(CircleFragment.mCircleListView.getContext(),
                        R.layout.circle_text_layout, listname));
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_circle_detail, menu);
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

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        // Translate overlay and image
        float flexibleRange = mFlexibleSpaceImageHeight - mActionBarSize;
        int minOverlayTransitionY = mActionBarSize - mOverlayView.getHeight();
        ViewHelper.setTranslationY(mOverlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        ViewHelper.setTranslationY(mDes, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));

        // Translate list background
        ViewHelper.setTranslationY(mListBackgroundView, Math.max(0, -scrollY + mFlexibleSpaceImageHeight));

        // Change alpha of overlay
        ViewHelper.setAlpha(mOverlayView, ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));

        // Scale title text
        float scale = 1 + ScrollUtils.getFloat((flexibleRange - scrollY) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA);
        setPivotXToTitle();
        ViewHelper.setPivotY(mDes, 0);
//        ViewHelper.setScaleX(mDes, scale);
//        ViewHelper.setScaleY(mDes, scale);

        // Translate title text
        int maxTitleTranslationY = (int) (mFlexibleSpaceImageHeight - mDes.getHeight() );
        int titleTranslationY = maxTitleTranslationY - scrollY;
        ViewHelper.setTranslationY(mDes, titleTranslationY);

        // Translate FAB
        int maxFabTranslationY = mFlexibleSpaceImageHeight - mLike.getHeight() / 2;
        float fabTranslationY = ScrollUtils.getFloat(
                -scrollY + mFlexibleSpaceImageHeight - mLike.getHeight() / 2,
                mActionBarSize - mLike.getHeight() / 2,
                maxFabTranslationY);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            // On pre-honeycomb, ViewHelper.setTranslationX/Y does not set margin,
            // which causes FAB's OnClickListener not working.
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mLike.getLayoutParams();
            lp.leftMargin = mOverlayView.getWidth() - mFabMargin - mLike.getWidth();
            lp.topMargin = (int) fabTranslationY;
            mLike.requestLayout();
        } else {
            ViewHelper.setTranslationX(mLike, mOverlayView.getWidth() - mFabMargin - mLike.getWidth());
            ViewHelper.setTranslationY(mLike, fabTranslationY);
        }

        // Show/hide FAB
        if (fabTranslationY < mFlexibleSpaceShowFabOffset) {
            hideFab();
        } else {
            showFab();
        }
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setPivotXToTitle() {
        Configuration config = getResources().getConfiguration();
        if (Build.VERSION_CODES.JELLY_BEAN_MR1 <= Build.VERSION.SDK_INT
                && config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            ViewHelper.setPivotX(mDes, findViewById(android.R.id.content).getWidth());
        } else {
            ViewHelper.setPivotX(mDes, 0);
        }
    }

    private void showFab() {
        if (!mFabIsShown) {
            ViewPropertyAnimator.animate(mLike).cancel();
            ViewPropertyAnimator.animate(mLike).scaleX(1).scaleY(1).setDuration(200).start();
            mFabIsShown = true;
        }
    }

    private void hideFab() {
        if (mFabIsShown) {
            ViewPropertyAnimator.animate(mLike).cancel();
            ViewPropertyAnimator.animate(mLike).scaleX(0).scaleY(0).setDuration(200).start();
            mFabIsShown = false;
        }
    }

}
