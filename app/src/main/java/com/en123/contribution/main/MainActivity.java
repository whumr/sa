package com.en123.contribution.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.en123.contribution.R;
import com.en123.contribution.base.BaseFragment;
import com.en123.contribution.base.BaseFragmentActivity;

/**
 * Created by a352 on 16/1/6.
 */
public class MainActivity extends BaseFragmentActivity implements View.OnClickListener {

    private TextView action_txt, group_txt, mine_txt;
    private int current_fragment;

    private BaseFragment action_fragment, group_fragment, mine_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setViews();
    }

    private void findViews() {
        action_txt = (TextView) findViewById(R.id.action_txt);
        group_txt = (TextView) findViewById(R.id.group_txt);
        mine_txt = (TextView) findViewById(R.id.mine_txt);
    }

    private void setViews() {
        current_fragment = R.id.action_txt;
        action_txt.setOnClickListener(this);
        group_txt.setOnClickListener(this);
        mine_txt.setOnClickListener(this);

        if (action_fragment == null)
            action_fragment = new ActionFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, action_fragment).commit();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("token"))
            Toast.makeText(this, intent.getStringExtra("token"), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        action_txt.setBackgroundColor(getResources().getColor(R.color.white));
        group_txt.setBackgroundColor(getResources().getColor(R.color.white));
        mine_txt.setBackgroundColor(getResources().getColor(R.color.white));
        switch (id) {
            case R.id.action_txt:
                action_txt.setBackgroundColor(getResources().getColor(R.color.gray_b8));
                break;
            case R.id.group_txt:
                group_txt.setBackgroundColor(getResources().getColor(R.color.gray_b8));
                break;
            case R.id.mine_txt:
                mine_txt.setBackgroundColor(getResources().getColor(R.color.gray_b8));
                break;
        }
        changeFragment(id);
    }

    private void changeFragment(int id) {
        if (id != current_fragment) {
            BaseFragment fragment = null;
            switch (id) {
                case R.id.action_txt:
                    if (action_fragment == null)
                        action_fragment = new ActionFragment();
                    fragment = action_fragment;
                    break;
                case R.id.group_txt:
                    if (group_fragment == null)
                        group_fragment = new GroupFragment();
                    fragment = group_fragment;
                    break;
                case R.id.mine_txt:
                    if (mine_fragment == null)
                        mine_fragment = new MineFragment();
                    fragment = mine_fragment;
                    break;
            }
            current_fragment = id;
            if (fragment != null)
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment).commit();
        }
    }
}
