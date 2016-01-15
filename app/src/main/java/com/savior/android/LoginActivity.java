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
import com.savior.android.entity.UserEntity;
import com.savior.android.http.UserService;
import com.savior.android.http.callback.DefaultCallback;
import com.savior.android.http.callback.EntityCallback;
import com.savior.android.main.MainActivity;
import com.savior.android.util.HttpUtil;
import com.savior.android.util.UserSession;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.concurrent.TimeoutException;

public class LoginActivity extends BaseActivity {

    private EditText name_txt, password_txt;
    private Button login_btn, reg_btn;

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
                UserService.login(name_txt.getText().toString(), password_txt.getText().toString(), new EntityCallback<UserEntity>() {
                    @Override
                    public void succeed(UserEntity user) {
                        UserSession.getSession().login(LoginActivity.this, user);
                        jumpToMain();
                    }

                    @Override
                    public void fail(String error) {
                        toastShort(error);
                    }
                });
            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserService.reg(name_txt.getText().toString(), password_txt.getText().toString(), new EntityCallback<UserEntity>() {
                    @Override
                    public void succeed(UserEntity user) {
                        toastShort(user.id + "");
                    }

                    @Override
                    public void fail(String error) {
                        toastShort(error);
                    }
                });
            }
        });
        UserService.checkLogin(this, new EntityCallback<UserEntity>() {
            @Override
            public void succeed(UserEntity user) {
                UserSession.getSession().setUser(user);
                jumpToMain();
            }

            @Override
            public void fail(String error) {
                toastShort(error);
            }
        });
    }

    private void jumpToMain() {
        Intent intent = new Intent();
        intent.putExtra("token", UserSession.getSession().getUser().last_token);
        intent.setClass(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}

