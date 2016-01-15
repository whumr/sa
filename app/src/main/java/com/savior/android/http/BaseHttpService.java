package com.savior.android.http;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.savior.android.base.BaseEntity;
import com.savior.android.http.callback.Callback;
import com.savior.android.http.callback.DefaultCallback;
import com.savior.android.http.callback.EntityCallback;
import com.savior.android.http.callback.EntityListCallback;
import com.savior.android.http.callback.JsonCallback;
import com.savior.android.util.HttpUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/14.
 */
public class BaseHttpService {

    protected static final String BASE_URL = "http://192.168.9.99";
    public static String RESPONSE_STATUS = "success";
    public static String RESPONSE_ERROR = "error";
    public static int RESPONSE_SUCCEED = 1, RESPONSE_FAIL = 0;
    public static final int ERROR_DATA = 0, ERROR_STATUS = 1, SUCCESS = 2;

    protected static void postDefault(String url, RequestParams params, DefaultCallback callback) {
        doPost(url, params, callback, null, null);
    }

    protected static void postEntity(String url, RequestParams params, EntityCallback callback, Class<? extends BaseEntity> clazz, String key) {
        doPost(url, params, callback, clazz, key);
    }

    protected static void postEntityList(String url, RequestParams params, EntityListCallback callback, Class<? extends BaseEntity> clazz, String key) {
        doPost(url, params, callback, clazz, key);
    }

    protected static void postJson(String url, RequestParams params, JsonCallback callback) {
        doPost(url, params, callback, null, null);
    }

    private static void doPost(String url, RequestParams params, final Callback callback, final Class<? extends BaseEntity> clazz, final String key) {
        AsyncHttpClient client = HttpUtil.getClient();
        client.post(url, params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                int success = isSucceed(json);
                switch (success) {
                    case ERROR_DATA:
                        errorData(callback);
                        break;
                    case ERROR_STATUS:
                        errorMsg(json, callback);
                        break;
                    case SUCCESS:
                        try {
                            if (callback instanceof DefaultCallback)
                                ((DefaultCallback) callback).succeed();
                            else if (callback instanceof EntityCallback)
                                entityCallbackSucceed(json.getJSONObject(key), (EntityCallback) callback, clazz);
                            else if (callback instanceof EntityListCallback)
                                entityListCallbackSucceed(json.getJSONArray(key), (EntityListCallback) callback, clazz);
                            else if (callback instanceof JsonCallback)
                                ((JsonCallback) callback).succeed(json);
                        } catch (Exception e) {
                            errorData(callback);
                        }
                        break;
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                fail(statusCode, headers, throwable, errorResponse, callback);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                fail(statusCode, headers, responseString, throwable, callback);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                fail(statusCode, headers, throwable, errorResponse, callback);
            }
        });
    }

    private static <E extends BaseEntity> void entityCallbackSucceed(JSONObject json, EntityCallback callback, Class<E> clazz) {
        try {
            E entity = (E)clazz.getMethod("parseJson", JSONObject.class).invoke(null, json);
            callback.succeed(entity);
        } catch (Exception e) {
            errorData(callback);
        }
    }

    private static <E extends BaseEntity> void entityListCallbackSucceed(JSONArray array, EntityListCallback callback, Class<E> clazz) {
        try {
            List<E> list = new ArrayList<E>();
            for (int i = 0; i < array.length(); i++) {
                list.add((E)clazz.getMethod("parseJson", JSONObject.class).invoke(null, array.getJSONObject(i)));
            }
            callback.succeed(list);
        } catch (Exception e) {
            errorData(callback);
        }
    }

    private static int isSucceed(JSONObject json) {
        try {
            if (json == null || !json.has(RESPONSE_STATUS) ||
                    (json.getInt(RESPONSE_STATUS) != RESPONSE_SUCCEED && json.getInt(RESPONSE_STATUS) != RESPONSE_FAIL))
                return ERROR_DATA;
            int success = json.getInt(RESPONSE_STATUS);
            if (success == RESPONSE_SUCCEED)
                return SUCCESS;
            if (success == RESPONSE_FAIL)
                return ERROR_STATUS;
        } catch (JSONException e) {
        }
        return ERROR_DATA;
    }

    private static void fail(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse, Callback callback) {
        callback.fail("");
    }

    private static void fail(int statusCode, Header[] headers, String responseString, Throwable throwable, Callback callback) {
        callback.fail("");
    }

    private static void fail(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse, Callback callback) {
        callback.fail("");
    }

    private static void errorData(Callback callback) {
        callback.fail("数据错误");
    }

    private static void errorMsg(JSONObject json, Callback callback) {
        String msg = null;
        if (json.has(RESPONSE_ERROR))
            try {
                msg = json.getString(RESPONSE_ERROR);
            } catch (JSONException e) {
            }
        callback.fail(msg);
    }
}