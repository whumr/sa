package com.savior.android.base;

import android.app.Activity;
import android.widget.Toast;

import com.savior.android.util.SharedPreferencesUtil;

/**
 * Created by Administrator on 2016/1/5.
 */
public class BaseActivity extends Activity {
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
