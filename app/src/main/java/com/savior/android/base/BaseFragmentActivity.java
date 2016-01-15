package com.savior.android.base;

import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.savior.android.util.SharedPreferencesUtil;

/**
 * Created by a352 on 16/1/6.
 */
public class BaseFragmentActivity extends FragmentActivity {
    private SharedPreferencesUtil sp;

    public SharedPreferencesUtil getSp() {
        if (sp == null)
            sp = new SharedPreferencesUtil(this);
        return sp;
    }

    public void toastShort(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void toastLong(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
