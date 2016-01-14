package com.savior.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.savior.android.base.BaseActivity;
import com.savior.android.main.MainActivity;
import com.savior.android.util.HttpUtil;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.concurrent.TimeoutException;

public class LoginActivity extends BaseActivity {

    private EditText name_txt, password_txt;
    private Button login_btn, reg_btn;

    static String URL_LOGIN = "http://192.168.9.99/account/login";
    static String URL_REG = "http://192.168.9.99/account/reg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        setViews();
    }

    private void findViews() {
        name_txt = (EditText) findViewById(R.id.name_txt);
        password_txt = (EditText) findViewById(R.id.password_txt);
        login_btn = (Button) findViewById(R.id.login_btn);
        reg_btn = (Button) findViewById(R.id.reg_btn);
    }

    private void setViews() {
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams();
                params.put("mobile", name_txt.getText().toString());
                params.put("password", password_txt.getText().toString());
                AsyncHttpClient client = HttpUtil.getClient();
                client.post(URL_LOGIN, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        log(response.toString());
                        try {
                            Intent intent = new Intent();
                            intent.putExtra("token", response.getString("token"));
                            intent.setClass(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        log(statusCode + ":连接超时");
                    }
                });
            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams();
                params.put("mobile", name_txt.getText().toString());
                params.put("password", password_txt.getText().toString());
                AsyncHttpClient client = HttpUtil.getClient();
                client.post(URL_REG, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        log(response.toString());
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        log(statusCode + ":" + responseString);
                    }
                });
            }
        });
    }

    private void log(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

