package com.savior.android.util;

import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by Administrator on 2016/1/14.
 */
public class HttpUtil {

    private static AsyncHttpClient client;
    private static final int TIMEOUT = 10 * 1000;

    public static AsyncHttpClient getClient() {
        if (client == null) {
            client = new AsyncHttpClient();
            client.setConnectTimeout(TIMEOUT);
        }
        return client;
    }
}
