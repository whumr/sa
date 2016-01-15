package com.savior.android.util;

import android.content.Context;

import com.savior.android.entity.UserEntity;

/**
 * Created by Administrator on 2016/1/15.
 */
public class UserSession {

    private UserEntity user;
    private SharedPreferencesUtil sp;

    private static UserSession session;

    private UserSession() {
    }

    public static UserSession getSession() {
        if (session == null)
            session = new UserSession();
        return session;
    }

    public boolean login(Context context, UserEntity user) {
        this.user = user;
        sp = new SharedPreferencesUtil(context);
        return sp.login(user);
    }

    public UserEntity getUser() {
        return this.user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public boolean isLogin() {
        return this.user ==null;
    }
}
