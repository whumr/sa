package com.en123.contribution.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.en123.contribution.R;
import com.en123.contribution.base.BaseFragment;

/**
 * Created by a352 on 16/1/6.
 */
public class GroupFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        return view;
    }
}
