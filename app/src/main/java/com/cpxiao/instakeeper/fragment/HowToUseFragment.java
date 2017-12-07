package com.cpxiao.instakeeper.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cpxiao.instakeeper.R;

/**
 * @author cpxiao on 2017/11/29.
 */

public class HowToUseFragment extends Fragment {
    public static HowToUseFragment newInstance(String info) {
        Bundle args = new Bundle();
        HowToUseFragment fragment = new HowToUseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_how_to_use, null);
        return view;
    }
}