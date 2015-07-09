package com.test.zhikangzhou.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.test.zhikangzhou.activity.CircleDetail;
import com.test.zhikangzhou.activity.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {
    private ListView mListViewMessage;
    private String[] strs = new String[] {
            "first", "second", "third", "fourth", "fifth"
    };

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mview=inflater.inflate(R.layout.fragment_message, container, false);
        mListViewMessage=(ListView)mview.findViewById(R.id.listView_message);
        mListViewMessage.setAdapter(new ArrayAdapter(this.getActivity(),
                R.layout.circle_text_layout, strs));

        return mview;
    }


}
