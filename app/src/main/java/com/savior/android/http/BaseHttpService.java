package com.savior.android.http;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.savior.android.http.callback.Callback;
import com.savior.android.http.callback.DefaultCallback;
import com.savior.android.http.callback.EntityCallback;
import com.savior.android.util.HttpUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/1/14.
 */
public class BaseHttpService {

    public static String RESPONSE_STATUS = "success";
    public static String RESPONSE_ERROR = "error";
    public static int RESPONSE_SUCCEED = 1, RESPONSE_FAIL = 0;
    public static final int ERROR_DATA = 0, ERROR_STATUS = 1, SUCCESS = 2;

    protected static void doPostDefault(String url, RequestParams params, final DefaultCallback callback) {

    }

    protected static void doPost(String url, RequestParams params, final Callback callback) {
        AsyncHttpClient client = HttpUtil.getClient();
        client.patch(url, params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                int success = isSucceed(response);
                switch (success) {
                    case ERROR_DATA:
                        errorData(callback);
                        break;
                    case ERROR_STATUS:
                        errorMsg(response, callback);
                        break;
                    case SUCCESS:
                        if (callback instanceof DefaultCallback)
                            ((DefaultCallback)callback).succeed();
                        else if (callback instanceof EntityCallback)
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

    private static int isSucceed(JSONObject json) {
        try {
            if (json != null || !json.has(RESPONSE_STATUS) ||
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
