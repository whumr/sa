package com.savior.android.http;

import android.content.Context;

import com.loopj.android.http.RequestParams;
import com.savior.android.entity.UserEntity;
import com.savior.android.http.callback.DefaultCallback;
import com.savior.android.http.callback.EntityCallback;
import com.savior.android.util.SharedPreferencesUtil;

/**
 * Created by Administrator on 2016/1/15.
 */
public class UserService extends BaseHttpService {

    private static final String URL_USER = BASE_URL + "/account",
            URL_LOGIN = URL_USER + "/login",
            URL_REG = URL_USER + "/reg",
            URL_CHECKTOKEN = URL_USER + "/checkToken";

    private static final String KEY_USER = "account";

    public static void login(String mobile, String password, EntityCallback<UserEntity> callback) {
        RequestParams params = new RequestParams();
        params.put("mobile", mobile);
        params.put("password", password);
        postEntity(URL_LOGIN, params, callback, UserEntity.class, KEY_USER);
    }

    public static void reg(String mobile, String password, EntityCallback<UserEntity> callback) {
        RequestParams params = new RequestParams();
        params.put("mobile", mobile);
        params.put("password", password);
        postEntity(URL_REG, params, callback, UserEntity.class, KEY_USER);
    }

    public static void checkLogin(Context context, EntityCallback<UserEntity> callback) {
        SharedPreferencesUtil sp = new SharedPreferencesUtil(context);
        UserEntity user = sp.getLastUser();
        if (user == null)
            callback.fail("no last user");
        else {
            RequestParams params = new RequestParams();
            params.put("mobile", user.mobile);
            params.put("token", user.last_token);
            postEntity(URL_CHECKTOKEN, params, callback, UserEntity.class, KEY_USER);
        }
    }
}
