package com.savior.android.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.savior.android.R;
import com.savior.android.base.BaseFragment;

/**
 * Created by a352 on 16/1/6.
 */
public class ActionFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_action, container, false);
        return view;
    }
}
