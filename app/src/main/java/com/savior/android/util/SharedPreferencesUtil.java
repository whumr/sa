package com.savior.android.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.savior.android.entity.UserEntity;

/**
 * Created by Administrator on 2016/1/15.
 */
public class SharedPreferencesUtil {
    private static final String SP_NAME = "savior",
        LAST_ACCOUNT = "last_account", LAST_TOKEN = "last_token";

    private SharedPreferences sp ;
    private SharedPreferences.Editor editor;

    public SharedPreferencesUtil(Context context) {
        this.sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        this.editor = sp.edit();
    }

    public boolean setLongValue(String key, long value){
        return editor.putLong(key, value).commit();
    }

    public long getLongValue(String key){
        return sp.getLong(key, 0);
    }

    public boolean setStringValue(String key, String value){
        return editor.putString(key, value).commit();
    }
    public String getStringValue(String key){
        return sp.getString(key, "");
    }

    public boolean setBooleanValue(String key, boolean value){
        return editor.putBoolean(key, value).commit();
    }
    public boolean getBooleanValue(String key, boolean flag_default){
        return sp.getBoolean(key, flag_default);
    }

    public boolean setIntValue(String key, int value){
        return editor.putInt(key, value).commit();
    }
    public int getIntValue(String key){
        return sp.getInt(key, -1);
    }

    public boolean setFloatValue(String key, float value){
        return editor.putFloat(key, value).commit();
    }
    public float getFloatValue(String key){
        return sp.getFloat(key, -1);
    }

    public boolean setUserValue(String user_id, String key, String value){
        return editor.putString(user_id + "." + key, value).commit();
    }

    public String getUserValue(String user_id, String key){
        return sp.getString(user_id + "." + key, "");
    }

    public boolean setUserValue(String user_id, String key, boolean value){
        return editor.putBoolean(user_id + "." + key, value).commit();
    }

    public boolean getUserValue(String user_id, String key, boolean flag_default){
        return sp.getBoolean(user_id + "." + key, flag_default);
    }

    public boolean login(UserEntity user) {
        return setStringValue(LAST_ACCOUNT, user.mobile) && setStringValue(LAST_TOKEN, user.last_token);
    }

    public UserEntity getLastUser() {
        String mobile = getStringValue(LAST_ACCOUNT);
        String token = getStringValue(LAST_TOKEN);
        if ("".equals(mobile) || "".equals(token))
            return null;
        UserEntity user = new UserEntity();
        user.mobile = mobile;
        user.last_token = token;
        return user;
    }
}
