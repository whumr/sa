package com.en123.contribution;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.en123.contribution.base.BaseActivity;
import com.en123.contribution.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class LoginActivity extends BaseActivity {

    private EditText name_txt, password_txt;
    private Button login_btn, reg_btn;

    static String URL_LOGIN = "http://192.168.9.138:2016/api/accounts/login";
    static String URL_REG = "http://192.168.9.138:2016/api/accounts/register";

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
                RequestParams params = new RequestParams(URL_LOGIN);
                params.addHeader("Content-Type", "application/json");
                JSONObject data = new JSONObject();
                try {
                    data.put("mobile", name_txt.getText().toString());
                    data.put("password", password_txt.getText().toString());
                } catch (JSONException e) {
                }
                params.setBodyContent(data.toString());
                params.setConnectTimeout(10 * 1000);
                x.http().post(params, new Callback.CommonCallback<String>() {

                    @Override
                    public void onSuccess(String result) {
                        log(result);
                        try {
                            JSONObject json = new JSONObject(result);
                            Intent intent = new Intent();
                            intent.putExtra("token", json.getString("token"));
                            intent.setClass(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        if (ex instanceof HttpException) {
                            HttpException he = (HttpException) ex;
                            log(he.getCode() + ":" + he.getMessage());
                        } else {
                            ex.printStackTrace(System.out);
                            log(ex.getLocalizedMessage());
                        }
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                    }

                    @Override
                    public void onFinished() {
                    }
                });
            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams(URL_REG);
                params.addHeader("Content-Type", "application/json");
                JSONObject data = new JSONObject();
                try {
                    data.put("mobile", name_txt.getText().toString());
                    data.put("password", password_txt.getText().toString());
                } catch (JSONException e) {
                }
                params.setBodyContent(data.toString());
                params.setConnectTimeout(10 * 1000);
                x.http().post(params, new Callback.CommonCallback<String>() {

                    @Override
                    public void onSuccess(String result) {
                        log(result.toString());
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        if (ex instanceof HttpException) {
                            HttpException he = (HttpException) ex;
                            log(he.getCode() + ":" + he.getMessage());
                        } else {
                            ex.printStackTrace(System.out);
                            log(ex.getLocalizedMessage());
                        }
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                    }

                    @Override
                    public void onFinished() {
                    }
                });
            }
        });
    }

    private void log(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

