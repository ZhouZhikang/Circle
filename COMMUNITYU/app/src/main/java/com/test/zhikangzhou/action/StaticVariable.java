package com.test.zhikangzhou.action;


import android.content.SharedPreferences;

import com.test.zhikangzhou.client.model.ViewActivity;
import com.test.zhikangzhou.client.model.ViewCircle;
import com.test.zhikangzhou.client.model.ViewUser;

/**
 * Created by zhikangzhou on 15/6/22.
 */
public class StaticVariable {
    public static ViewUser mCurrentUser=null;
    public static ViewUser sSelectedUser=null;
    public static ViewActivity[] mActivity;
    public static ViewActivity mCurrentActivity;
    public static String mSearchWord;
    public static ViewCircle[] mCircles;
    public static ViewCircle mCurrentCircle;
    public static int mSelectedActivityPosition;
    public static ViewUser[] sCircleUsers;
    public static ViewUser[] sActivityUsers;
    public static boolean isLog=true;
}
